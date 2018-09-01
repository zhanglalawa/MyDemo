package com.example.demo.entity;

/*
 *  项目名：    Demo
 *  包名：      com.example.demo.entity
 *  文件名：    ZhiHuNews
 *  描述：      TODO
 */
public class ZhiHuNews {
    private String newsTitle;
    private String newsImage;
    private String newsUrl;

    public ZhiHuNews(String newsTitle,String newsImage,String newsUrl){
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
        this.newsUrl = newsUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public String getNewsUrl() {
        return newsUrl;
    }
}
