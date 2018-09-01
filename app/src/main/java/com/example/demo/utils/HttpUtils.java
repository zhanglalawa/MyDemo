package com.example.demo.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.utils
 *  文件名：    HttpUtils
 *  描述：      TODO
 */
public class HttpUtils {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
