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

import org.w3c.dom.Text;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    ModifyPasswordActivity
 *  描述：      修改密码界面
 */
public class ModifyPasswordActivity extends BaseActivity {
    private EditText newPassword;
    private EditText oldPassword;
    private EditText newPasswordAgain;
    private Button modify;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        initView();
    }

    private void initView(){
        oldPassword = (EditText)findViewById(R.id.old_password);
        newPassword = (EditText)findViewById(R.id.new_password);
        newPasswordAgain = (EditText)findViewById(R.id.new_password_again);
        modify = (Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = oldPassword.getText().toString();
                String newPass = newPassword.getText().toString();
                String newPassAgain = newPasswordAgain.getText().toString();

                if (!TextUtils.isEmpty(newPassAgain) && !TextUtils.isEmpty(oldPass) && !TextUtils.isEmpty(newPass)) {
                   if(newPass.equals(newPassAgain)){
                       MyUser.updateCurrentUserPassword(oldPass, newPass, new UpdateListener() {
                           @Override
                           public void done(BmobException e) {
                               if(e==null){
                                   Toast.makeText(ModifyPasswordActivity.this,"密码修改成功，可以用新密码进行登录啦",Toast.LENGTH_SHORT).show();
                                   finish();
                               }else{
                                   switch (e.getErrorCode()){
                                       case 210:
                                           Toast.makeText(ModifyPasswordActivity.this,"旧密码输入错误，密码修改失败" + e.toString(),Toast.LENGTH_SHORT).show();
                                           break;
                                       default:
                                           Toast.makeText(ModifyPasswordActivity.this,"密码修改失败" + e.toString(),Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }

                       });
                   }else {
                       Toast.makeText(ModifyPasswordActivity.this,"两次新密码输入不一致",Toast.LENGTH_SHORT).show();
                   }

                }else{
                    Toast.makeText(ModifyPasswordActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
