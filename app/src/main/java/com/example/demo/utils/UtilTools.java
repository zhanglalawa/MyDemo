package com.example.demo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.utils
 *  文件名：    UtilTools
 *  描述：      工具类
 */
public class UtilTools {
    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
