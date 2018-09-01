package com.example.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.utils.HttpUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.ui
 *  文件名：    WebViewActivity
 *  描述：      TODO
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();

    }

    private void initView(){
        webView = (WebView)findViewById(R.id.web_view);
        Intent intent = getIntent();
        String newsTitle = intent.getStringExtra("title");
        String newsUrl = intent.getStringExtra("url");
        parseUrl(newsUrl);


    }

    private void parseUrl(String newsUrl){
        HttpUtils.sendOkHttpRequest(newsUrl,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                WebViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WebViewActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                WebViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            webView.setWebViewClient(new WebViewClient());
                            webView.getSettings().setJavaScriptEnabled(true);
                            JSONObject jsonObject = new JSONObject(responseText);
                            webView.loadDataWithBaseURL(null,jsonObject.getString("body"),"text/html","utf-8",null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
