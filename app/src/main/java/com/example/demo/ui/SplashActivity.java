package com.example.demo.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.entity.MyUser;
import com.example.demo.utils.ShareUtils;
import com.example.demo.utils.StaticClass;
import com.example.demo.utils.UtilTools;

import cn.bmob.v3.BmobUser;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    Splashctivity
 *  描述：      延时2s，判断程序是否第一次运行，自定义字体，Activity全屏主题
 */
public class SplashActivity extends AppCompatActivity {
    private TextView tv_splash;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLE_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
                        if (userInfo!=null){
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    //初始化View
    private void initView() {
        //延时2000ms发送message
        handler.sendEmptyMessageDelayed(StaticClass.HANDLE_SPLASH, 2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setFont(this,tv_splash);
    }

    //判断是否为第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            //是第一次运行，需要更改标记位
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    //闪屏页面要全屏，禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
