package com.example.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.demo.R;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    SettingActivity
 *  描述：      TODO
 */
public class SettingActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toast.makeText(this, "这一页是假的，正在开发中...", Toast.LENGTH_LONG).show();
    }
}
