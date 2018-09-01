package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.entity.MyUser;
import com.example.demo.ui.LoginActivity;
import com.example.demo.ui.ModifyPasswordActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.fragment
 *  文件名：    UserFragment
 *  描述：      个人中心界面
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private MyUser userInfo;
    private Button logout;
    private Button editUser;
    private EditText editUserName;
    private EditText editNickname;
    private RadioGroup mRadioGroup;
    private EditText editIntro;
    private EditText editAge;
    private Button finishEdit;
    private Button modify;
    private boolean gender;
    private FloatingActionButton changeBackground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        editUser = (Button) view.findViewById(R.id.edit_user);
        editUser.setOnClickListener(this);

        editUserName = (EditText) view.findViewById(R.id.user_name);
        editAge = (EditText) view.findViewById(R.id.edit_age);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.userRadioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.modify_boy) {
                    gender = true;
                } else {
                    gender = false;
                }
            }
        });
        editIntro = (EditText) view.findViewById(R.id.edit_intro);
        editNickname = (EditText) view.findViewById(R.id.edit_nickname);

        finishEdit = (Button) view.findViewById(R.id.finish_edit);
        finishEdit.setOnClickListener(this);

        modify = (Button)view.findViewById(R.id.modify);
        modify.setOnClickListener(this);

        changeBackground = (FloatingActionButton) view.findViewById(R.id.change_background);
        changeBackground.setOnClickListener(this);

        //默认不可编辑吗，其中用户名永远不可更改
        editUserName.setEnabled(false);
        setEnable(false);

        //根据注册时候的填写先编辑这些框
        userInfo = BmobUser.getCurrentUser(MyUser.class);
        editUserName.setText(userInfo.getUsername());
        editNickname.setText(userInfo.getNickname());
        editAge.setText(String.valueOf(userInfo.getAge()));
        if (userInfo.getGender()) {
            mRadioGroup.check(R.id.modify_boy);
        } else {
            mRadioGroup.check(R.id.modify_girl);
        }
        editIntro.setText(userInfo.getIntro());
        if (TextUtils.isEmpty(userInfo.getIntro())) {
            editIntro.setHint("~用一两句话描述你自己吧~");
        }
    }

    private void setEnable(boolean enable) {
        editAge.setEnabled(enable);
        mRadioGroup.getChildAt(0).setEnabled(enable);
        mRadioGroup.getChildAt(1).setEnabled(enable);
        editIntro.setEnabled(enable);
        editNickname.setEnabled(enable);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.logout:
                //退出登录
                MyUser.logOut();   //清除缓存用户对象
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_user:
                //编辑资料按钮
                editUser.setBackgroundResource(R.drawable.button_cannot_use_bg);
                setEnable(true);
                editNickname.requestFocus();
                editNickname.setSelection(editNickname.getText().toString().length());
                finishEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.finish_edit:
                //完成修改按钮
                //先获取值
                String nickname = editNickname.getText().toString().trim();
                String age = editAge.getText().toString().trim();
                String intro = editIntro.getText().toString().trim();

                if (!TextUtils.isEmpty(nickname)
                        && !TextUtils.isEmpty(age)) {
                    final MyUser newUser = new MyUser();
                    newUser.setNickname(nickname);
                    newUser.setGender(gender);
                    if (TextUtils.isDigitsOnly(age)) {
                        newUser.setAge(Integer.parseInt(age));
                        newUser.setIntro(intro);
                        if (TextUtils.isEmpty(intro)){
                            editIntro.setHint("~用一两句话描述你自己吧~");
                        }

                        final MyUser currentUser = BmobUser.getCurrentUser(MyUser.class);
                        newUser.update(currentUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(),"修改成功！",Toast.LENGTH_SHORT).show();
                                    editUser.setBackgroundResource(R.drawable.button_bg);
                                    setEnable(false);
                                    finishEdit.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(getActivity(),"修改失败！"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(),"修改失败！请输入合法的年龄",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "请填写完整必要的信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.modify:
                Intent intent = new Intent(getActivity(), ModifyPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.change_background:
                Toast.makeText(getActivity(), "这个按钮的换背景和头像功能正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
