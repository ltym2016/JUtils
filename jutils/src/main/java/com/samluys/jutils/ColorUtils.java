package com.samluys.jutils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * @author luys
 * @describe
 * @date 2020-01-16
 * @email samluys@foxmail.com
 */
public class ColorUtils {

    /**
     * 获取带透明度的颜色
     *
     * @param color 不带透明度的颜色
     * @param alpha 透明度 0-1.0f
     * @return
     */
    public static int getAlphaColor(int color, float alpha) {
        int mAlpha = (int) (255 * alpha);
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        return Color.argb(mAlpha, red, green, blue);
    }

    public static Drawable getTintDrawable(Drawable drawable, @ColorInt int color) {
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable1, color);
        return drawable1;
    }

    public static Drawable getTintDrawable(Context context, int id, int color) {
        Drawable originDrawable = ContextCompat.getDrawable(context, id);
        if (originDrawable != null) {
            return getTintDrawable(originDrawable, color);
        }
        return null;
    }
}
