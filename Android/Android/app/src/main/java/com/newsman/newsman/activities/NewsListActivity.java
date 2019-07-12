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

import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.R;
import com.newsman.newsman.adapters.NewsListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        recyclerView = (RecyclerView) findViewById(R.id.rv_news_list);
        newsList = new ArrayList<>();

        prepareNews();

        adapter = new NewsListAdapter(this, newsList);

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
        News newsItem = new News( 1,"Title1", "this is content asdasdas", null, new Date().toString());
        newsList.add(newsItem);

        newsItem = new News( 2,"Title2", "this is content asdasdas", null, new Date().toString());
        newsList.add(newsItem);

        newsItem = new News( 3,"Title3", "this is content asdasdas", null, new Date().toString());
        newsList.add(newsItem);

        newsItem = new News( 4,"Title4", "this is content asdasdas", null, new Date().toString());
        newsList.add(newsItem);

        newsItem = new News( 5,"Title5", "this is content asdasdas", null, new Date().toString());
        newsList.add(newsItem);

        newsItem = new News( 6,"Title6", "this is content asdasdas", null, new Date().toString());
        newsList.add(newsItem);

        newsItem = new News( 7, "Title7", "this is content asdasdas", null, new Date().toString());
        newsList.add(newsItem);

    }
}
