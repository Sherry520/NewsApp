package com.example.gyh.newsapp;

/**
 * Created by gyh on 2016/10/29.
 */

public class News {
    private String mNewsTitle;
    private String mDate;
    private String mUrl;

    public News(String newsTitle, String date, String url) {
        mNewsTitle = newsTitle;
        mDate = date;
        mUrl = url;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
