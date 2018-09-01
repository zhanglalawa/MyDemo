package com.example.demo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.entity.MyUser;
import com.example.demo.utils.LogUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    RegisterActivity
 *  描述：      注册页面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText registerUser;
    private EditText registerNikename;
    private EditText registerAge;
    private EditText registerIntro;
    private RadioGroup mRadioGroup;
    private EditText registerPassword;
    private EditText passwordAgain;
    private EditText registerEmail;
    private Button register;
    //性别，默认为true,男
    private boolean gender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        registerUser = (EditText) findViewById(R.id.register_user);
        registerNikename = (EditText) findViewById(R.id.register_nickname);
        registerAge = (EditText) findViewById(R.id.register_age);
        registerIntro = (EditText) findViewById(R.id.register_intro);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boy) {
                    gender = true;
                } else {
                    gender = false;
                }
            }
        });
        registerPassword = (EditText) findViewById(R.id.register_password);
        passwordAgain = (EditText) findViewById(R.id.password_again);
        registerEmail = (EditText) findViewById(R.id.register_email);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                //获取输入框的值
                String name = registerUser.getText().toString().trim();
                String nickname = registerNikename.getText().toString().trim();
                String age = registerAge.getText().toString().trim();
                String intro = registerIntro.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                String passwordIdentified = passwordAgain.getText().toString().trim();
                String email = registerEmail.getText().toString().trim();

                if (!TextUtils.isEmpty(name) &&
                        !TextUtils.isEmpty(nickname)
                        && !TextUtils.isEmpty(age)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(passwordIdentified)
                        && !TextUtils.isEmpty(email)) {
                    //判断两次输入是否一样
                    if (password.equals(passwordIdentified)) {
                        //性别框

                        //如果简介为空
                        /*if (TextUtils.isEmpty(intro)) {
                            intro = "该用户未填写简介";
                        }*/

                        //注册
                        registerTheUser(name, nickname, age, intro, gender, password, email);
                    } else {
                        Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "带*的框为必填项！", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void registerTheUser(final String name,final String nickname, final String age, final String intro, final boolean gender, final String password, final String email) {
        MyUser user = new MyUser();
        user.setUsername(name);
        user.setNickname(nickname);
        user.setIntro(intro);
        if (TextUtils.isDigitsOnly(age)){
            user.setAge(Integer.parseInt(age));
        }else{
            Toast.makeText(RegisterActivity.this, "注册失败,请填写合法的年龄" , Toast.LENGTH_SHORT).show();
            return;
        }

        user.setGender(gender);
        user.setPassword(password);
        user.setEmail(email);

        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, name + "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    switch (e.getErrorCode()){
                        case 301:
                            Toast.makeText(RegisterActivity.this, "注册失败,请填写合法的邮箱名" , Toast.LENGTH_SHORT).show();
                            break;
                        case 202:
                            Toast.makeText(RegisterActivity.this, "注册失败，用户名已被注册" , Toast.LENGTH_SHORT).show();
                            break;
                        case 203:
                            Toast.makeText(RegisterActivity.this, "注册失败，邮箱已被注册" , Toast.LENGTH_SHORT).show();
                        default:
                            Toast.makeText(RegisterActivity.this, "注册失败"+e.toString() , Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }
}
