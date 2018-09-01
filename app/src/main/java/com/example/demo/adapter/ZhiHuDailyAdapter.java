package com.example.demo.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.entity.ZhiHuNews;
import com.example.demo.ui.WebViewActivity;

import java.util.List;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.adapter
 *  文件名：    ZhiHuDailyAdapter
 *  描述：      TODO
 */
public class ZhiHuDailyAdapter extends RecyclerView.Adapter<ZhiHuDailyAdapter.ViewHolder>{
    private List<ZhiHuNews> zhiHuNewsList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View newsView;
        ImageView newsImage;
        TextView titleText;
        public ViewHolder(View view){
            super(view);
            newsView = view;
            newsImage = (ImageView)view.findViewById(R.id.news_image);
            titleText = (TextView)view.findViewById(R.id.news_title);
        }
    }

    public ZhiHuDailyAdapter(List<ZhiHuNews> zhiHuNewsList){
        this.zhiHuNewsList = zhiHuNewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                int positon = holder.getAdapterPosition();
                ZhiHuNews news = zhiHuNewsList.get(positon);
                intent.putExtra("title",news.getNewsTitle());
                intent.putExtra("url",news.getNewsUrl());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ZhiHuNews zhiHuNews = zhiHuNewsList.get(position);
        holder.titleText.setText(zhiHuNews.getNewsTitle());
        Glide.with(holder.newsView.getContext()).load(zhiHuNews.getNewsImage()).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return zhiHuNewsList.size();
    }
}
