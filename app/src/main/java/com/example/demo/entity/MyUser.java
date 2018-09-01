package com.example.demo.entity;

import cn.bmob.v3.BmobUser;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.entity
 *  文件名：    MyUser
 *  描述：      用户属性
 */
public class MyUser extends BmobUser {
    private int age;
    //true男、false女
    private boolean gender;
    private String intro;
    private String nickname;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
