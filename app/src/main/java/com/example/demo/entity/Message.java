package com.example.demo.entity;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.entity
 *  文件名：    Message
 *  描述：      TODO
 */
public class Message {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int type;

    public Message(String content,int type){
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
