package com.example.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    ForgetPasswordActivity
 *  描述：      忘记密码输入邮箱修改
 */
public class ForgetPasswordActivity extends BaseActivity {
    private EditText registerEmail;
    private Button verify;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    private void initView(){
        registerEmail = (EditText)findViewById(R.id.register_email);
        verify = (Button)findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = registerEmail.getText().toString();
                if (!TextUtils.isEmpty(email)){
                    Toast.makeText(ForgetPasswordActivity.this,"邮箱验证功能未开通，敬请期待...",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgetPasswordActivity.this,"输入框不饿能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
