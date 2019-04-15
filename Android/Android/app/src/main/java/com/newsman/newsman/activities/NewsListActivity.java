package com.newsman.newsman.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.newsman.newsman.Entities.NewsItem;
import com.newsman.newsman.R;
import com.newsman.newsman.adapters.NewsListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private List<NewsItem> newsItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        recyclerView = (RecyclerView) findViewById(R.id.rv_news_list);
        newsItemList = new ArrayList<>();

        prepareNews();

        adapter = new NewsListAdapter(this, newsItemList);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_create_new_news:
                createNews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNews() {
        Intent intent = new Intent(this, CreateNewsActivity.class);
        startActivity(intent);
    }

    private void prepareNews() {
        NewsItem newsItem = new NewsItem("Title1", new Date(), "User1", "this is content asdasdas");
        newsItemList.add(newsItem);

        newsItem = new NewsItem("Title2", new Date(), "User2", "this is content2 asdasdas");
        newsItemList.add(newsItem);

        newsItem = new NewsItem("Title3", new Date(), "User3", "this is content3 asdasdas");
        newsItemList.add(newsItem);

        newsItem = new NewsItem("Title4", new Date(), "User4", "this is content4 asdasdas");
        newsItemList.add(newsItem);

        newsItem = new NewsItem("Title5", new Date(), "User5", "this is content5 asdasdas");
        newsItemList.add(newsItem);

        newsItem = new NewsItem("Title6", new Date(), "User6", "this is content6 asdasdas");
        newsItemList.add(newsItem);

        newsItem = new NewsItem("Title7", new Date(), "User7", "this is content7 asdasdas");
        newsItemList.add(newsItem);

    }
}
