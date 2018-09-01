package com.example.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.adapter.ZhiHuDailyAdapter;
import com.example.demo.entity.ZhiHuNews;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.fragment
 *  文件名：    GirlFragment
 *  描述：      TODO
 */
public class ZhiHuDailyFragment extends Fragment {
    private List<ZhiHuNews> zhiHuNewsList = new ArrayList<>();
    private RecyclerView zhiHuDailyRecyclerView;
    private ZhiHuDailyAdapter adapter;
    private TextView newsDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu, null);
        initView(view);
        getNews();
        return view;
    }

    private void initView(View view) {
        zhiHuDailyRecyclerView = (RecyclerView) view.findViewById(R.id.zhihu_daily_recycler_view);
        newsDate = (TextView) view.findViewById(R.id.news_date);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        zhiHuDailyRecyclerView.setLayoutManager(layoutManager);
        adapter = new ZhiHuDailyAdapter(zhiHuNewsList);
        zhiHuDailyRecyclerView.setAdapter(adapter);
    }

    private void getNews() {
        String url = "https://news-at.zhihu.com/api/4/news/latest";

        HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseJson(responseText);
                    }
                });
            }
        });
    }

    private void parseJson(String responseText) {
        try {
            JSONObject jsonObject = new JSONObject(responseText);
            String date = jsonObject.getString("date");
            String year = date.substring(0, 4);
            String month = date.substring(4, 6);
            String day = date.substring(6, 8);
            newsDate.setText("每日更新 今天：" + year + "年" + month + "月" + day + "日");
            JSONArray jsonArray = jsonObject.getJSONArray("top_stories");
            JSONObject object;
            for (int i = 0; i <jsonArray.length(); i ++){
                object = (JSONObject)jsonArray.get(i);
                ZhiHuNews news = new ZhiHuNews(object.getString("title"),object.getString("image"),
                        "https://news-at.zhihu.com/api/4/news/"+object.getString("id"));
                zhiHuNewsList.add(news);
            }
            jsonArray = jsonObject.getJSONArray("stories");
            for (int i = 0; i <jsonArray.length(); i ++){
                object = (JSONObject)jsonArray.get(i);
                ZhiHuNews news = new ZhiHuNews(object.getString("title"),(String)(object.getJSONArray("images").get(0)),
                        "https://news-at.zhihu.com/api/4/news/"+object.getString("id"));
                zhiHuNewsList.add(news);
            }
            adapter.notifyItemInserted(zhiHuNewsList.size()-1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
