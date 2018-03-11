package com.example.gyh.newsapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderCallbacks<List<News>> {
    private static final String REQUIRE_URL = "http://content.guardianapis.com/search" +
            "?q=debates&api-key=test";
    private static final int NEWS_LOADER_ID = 0;
    private NewsAdapter mNewsAdapter;
    private TextView mEmptyTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());
        ListView newsListView = (ListView) findViewById(R.id.news_list);
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);

        newsListView.setEmptyView(mEmptyTextView);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        newsListView.setAdapter(mNewsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                News currentNews = mNewsAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });
        getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, REQUIRE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mProgressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(R.string.no_news);
        mNewsAdapter.clear();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            //如果存在 {@link Earthquake} 的有效列表，则将其添加到适配器的
            // 数据集。这将触发 ListView 执行更新。
            if (news != null && !news.isEmpty()) {
                mNewsAdapter.addAll(news);
            }
        } else {
            // display error
            mEmptyTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsAdapter.clear();
    }
}
