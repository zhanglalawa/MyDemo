package com.example.demo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.entity.MyUser;
import com.example.demo.utils.ShareUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    LoginActivity
 *  描述：      登陆页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register;
    private Button login;
    private EditText userName;
    private EditText password;
    private CheckBox isRemember;
    private TextView forget;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        userName = (EditText) findViewById(R.id.login_name);
        password = (EditText) findViewById(R.id.login_password);
        isRemember = (CheckBox) findViewById(R.id.is_remember);
        forget= (TextView) findViewById(R.id.forget);
        forget.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //用SharedReference实现记住密码功能
        boolean isChecked = ShareUtils.getBoolean(this, "isRemember", false);
        if (isChecked) {
            String name = ShareUtils.getString(this, "userName", "");
            String pass = ShareUtils.getString(this, "password", "");
            userName.setText(name);
            password.setText(pass);

            //把光标聚焦在Password这一个框里面，并且光标移动到后面
            password.requestFocus();
            password.setSelection(pass.length());
            isRemember.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                String name = userName.getText().toString();
                String pass = password.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)) {
                    progressBar.setVisibility(View.VISIBLE);
                    //为了体现转了转

                    //获取输入的用户名和密码
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(pass);
                    //判断输入的用户名和密码信息
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            progressBar.setVisibility(View.GONE);
                            if (e == null) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                //是否选框
                                ShareUtils.putBoolean(LoginActivity.this, "isRemember", isRemember.isChecked());

                                if (isRemember.isChecked()) {
                                    ShareUtils.putString(LoginActivity.this, "userName", userName.getText().toString());
                                    ShareUtils.putString(LoginActivity.this, "password", password.getText().toString());
                                } else {
                                    ShareUtils.deleteShare(LoginActivity.this, "userName");
                                    ShareUtils.deleteShare(LoginActivity.this, "password");
                                }
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forget:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;

        }
    }
}
