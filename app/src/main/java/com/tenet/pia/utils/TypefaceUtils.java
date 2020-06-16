package com.tenet.pia.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TypefaceUtils {

    // 上下文
    private Context context;
    private Typeface typeface;

    /**
     *构造函数
     * @param context
     * @param ttfPath
     */
    public TypefaceUtils(Context context, String ttfPath) {
        this.context = context;
        this.typeface = getTypefaceFromTTF(ttfPath);
    }

    /**
     *获取字体文件
     * @param ttfPath
     * @return
     */
    public Typeface getTypefaceFromTTF(String ttfPath) {
        if (ttfPath == null) {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Old Newspapers Sung");
        } else {
            return Typeface.createFromAsset(context.getAssets(), ttfPath);
        }
    }

    /**
     * 设置 textView 字体
     * @param textView
     */
    public void setTxtViewTypeface(TextView textView) {
        textView.setTypeface(typeface);
    }

    /**
     * 设置字体
     * @param ttfPath
     */
    public void setTypeface(String ttfPath) {
        typeface = getTypefaceFromTTF(ttfPath);
    }

}
