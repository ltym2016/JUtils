package com.samluys.jutils;


import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luys
 * @describe 字符处理工具类
 * @date 2020-01-16
 * @email samluys@foxmail.com
 */
public class StringUtils {


    /**
     * int补0
     *
     * @param intnum
     * @return
     */
    public static String addZero(int intnum) {
        if (intnum < 10 && intnum > 0) {
            return "0" + intnum;
        } else {
            return intnum + "";
        }
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr
     *            身份证号
     * @return true 有效：false 无效
     * @throws ParseException
     */
    public static boolean IDCardValidate(String IDStr) throws ParseException {
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ 号码的长度18位 ================
        if (IDStr.length() != 18) {
            return false;
        }
        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        }
        if (isNumeric(Ai) == false) {
            //errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return false;
        }
        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 日
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
//          errorInfo = "身份证生日无效。";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                //errorInfo = "身份证生日不在有效范围。";
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            //errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            //errorInfo = "身份证日期无效";
            return false;
        }
        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            //errorInfo = "身份证地区编码错误。";
            return false;
        }
        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                //errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        } else {
            return true;
        }
        return true;
    }
    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
//      hashtable.put("71", "台湾");
//      hashtable.put("81", "香港");
//      hashtable.put("82", "澳门");
//      hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 数字转成W（万）
     * @param num
     * @return
     */
    public static String numToW(int num) {
        String text;
        if (num > 9999) {
            BigDecimal d = new BigDecimal(num);
            BigDecimal d1 = new BigDecimal(10000);
            BigDecimal result = d.divide(d1);
            double s = result.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
            text = s+"W";
        } else {
            text = String.valueOf(num);
        }

        return text;
    }

    /**
     * 去除首尾换行符和空格
     * @param str
     * @return
     */
    public static String trim(String str) {
        if(TextUtils.isEmpty(str)) {
            return str;
        }
        String trimStr = str.trim();
        return trimStr;
    }

    /**
     * 除法 四舍五入
     * @param arg1 除数
     * @param arg2 被除数
     * @param num 保留几位
     * @return
     */
    public static Double divide(int arg1, int arg2, int num) {
        BigDecimal bigDecimal = new BigDecimal(arg1);
        // 除数，实现2/12
        BigDecimal divisor = new BigDecimal(arg2);

        // 使用四舍五入模式，保留两位小数，注意模式HALF_UP
//        MathContext mc = new MathContext(num, RoundingMode.HALF_UP);
        Double calcResult = bigDecimal.divide(divisor, num,BigDecimal.ROUND_HALF_UP).doubleValue();
        return calcResult;
    }

    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 过滤表情
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        int len = source.length();// 返回unicode代码单元(码点)的数量
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);//取到每个码点
            if (isNotEmojiCharacter(codePoint)) {//判断是否是常用字符
                buf.append(codePoint);//如果是,就正常添加
            } else {
                buf.append("");//不是说明可能是emoji或者其他不支持的特殊字符,使用空替换
            } } return buf.toString();
    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        //判断是否为常用字符码点段
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD));
    }

    /**
     * 禁止输入表情
     * @return
     */
    public static InputFilter filterInputEmoji () {
        try {
            /** 表情过滤器 */
            InputFilter emojiFilter = new InputFilter() {
                //[\\uD83C\\uDF00-\\uD83D\\uDDFF]
                //[\\uD83E\\uDD00-\\uD83E\\uDDFF]
                // [\\uD83D\\uDE00-\\uD83D\\uDE4F]
                //[\\uD83D\\uDE80-\\uD83D\\uDEFF]

                Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    Matcher emojiMatcher = emoji.matcher(source);
                    if (emojiMatcher.find()) {
                        ToastUtils.showLong("不支持输入表情");
                        return "";
                    }
                    return null;
                }
            };

            return emojiFilter;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取URL指定的name的value值
     * @param url
     * @param name
     * @return
     */
    public static int getIdByName(String url, String name) {

        try {
            if (TextUtils.isEmpty(url)) {
                return -1;
            }

            if (!url.contains(name)) {
                return -1;
            }

            String result = "";
            int index = url.indexOf("?");
            String temp = url.substring(index + 1);

            String[] keyValue = temp.split("&");
            for (String str : keyValue) {
                if (str.contains(name)) {
                    result = str.replace(name + "=", "");
                    break;
                }
            }
            return Integer.valueOf(result);
        } catch (Exception e) {
            return -1;
        }
    }
}
