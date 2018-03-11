package com.example.gyh.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gyh on 2016/10/30.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private static final char LOCATION_DELITE = 'T';
    private static final String LOCATION_SEPARATOR = "Z";

    public NewsAdapter(Context context, List<News> newses) {
        super(context, 0, newses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }
        News currentNews = getItem(position);
        TextView newsTitleText = (TextView) listItemView.findViewById(R.id.newsTitle);
        String newsTitle = currentNews.getNewsTitle();
        newsTitleText.setText(newsTitle);
        TextView publicationDateText = (TextView) listItemView.findViewById(R.id.publicationDate);
        String publicationDate = currentNews.getDate();

        // replace T with backspace
        String datetime = publicationDate.replace(LOCATION_DELITE, ' ');
        //delete Z
        String[] dateTime1 = datetime.split(LOCATION_SEPARATOR);
        String dateTime = dateTime1[0];

        publicationDateText.setText(dateTime);
        return listItemView;
    }
}
