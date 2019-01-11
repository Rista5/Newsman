package com.example.me.tp_hwe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<News> {
    int resource;
    Context context;
    List<News> newsList;

    public NewsListAdapter(Context context, int resource, List<News> newsList){
        super(context,resource, newsList);
        this.resource = resource;
//        this.context = context;
//        this.newsList = newsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        LinearLayout newsView;
        News news = getItem(position);
        //News news = newsList.get(position);

        if(convertView==null){
            newsView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, newsView, true);
        } else {
            newsView = (LinearLayout) convertView;
        }
        TextView title = (TextView) newsView.findViewById(R.id.news_title);
        TextView content = (TextView) newsView.findViewById(R.id.news_text);
        ListView comments = (ListView) newsView.findViewById(R.id.comments_list);

        title.setText(news.getTitle());
        content.setText(news.getContent());
//        TODO: dodaj activity za komentare i klasu
//        ArrayAdapter<String> commentAdapter = new ArrayAdapter<String>(this,R.layout.)

        return newsView;
    }

}
