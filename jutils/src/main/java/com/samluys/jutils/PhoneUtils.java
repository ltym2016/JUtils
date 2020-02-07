package com.samluys.jutils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * @author luys
 * @describe
 * @date 2020-02-07
 * @email samluys@foxmail.com
 */
public class PhoneUtils {

    /**
     * 跳转到原生拨号页面
     *
     * @param phoneNumber The phone number.
     * @return {@code true}: operate successfully<br>{@code false}: otherwise
     */
    public static boolean dial(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        if (isIntentAvailable(intent)) {
            Utils.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }
        return false;
    }

    /**
     * 跳转到原生发送消息页面
     *
     * @param phoneNumber The phone number.
     * @param content     The content.
     * @return {@code true}: operate successfully<br>{@code false}: otherwise
     */
    public static boolean sendSms(final String phoneNumber, final String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        if (isIntentAvailable(intent)) {
            intent.putExtra("sms_body", content);
            Utils.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }
        return false;
    }


    private static boolean isIntentAvailable(final Intent intent) {
        return Utils.getContext()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }
}
