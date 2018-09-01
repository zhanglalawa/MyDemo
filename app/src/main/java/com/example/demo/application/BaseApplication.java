package com.example.demo.application;

import android.app.Application;

import com.example.demo.utils.StaticClass;

import cn.bmob.v3.Bmob;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.application
 *  文件名：    BaseApplication
 *  描述：      TODO
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
