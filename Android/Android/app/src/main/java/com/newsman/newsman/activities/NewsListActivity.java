package com.newsman.newsman.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.auxiliary.manu_helpers.LoginMenuInflater;
import com.newsman.newsman.auxiliary.sorting.news.ByDateModified;
import com.newsman.newsman.auxiliary.sorting.news.ByTitle;
import com.newsman.newsman.auxiliary.sorting.news.ByUsers;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.R;
import com.newsman.newsman.model.db_entities.SimpleNews;
import com.newsman.newsman.adapters.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private List<SimpleNews> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        recyclerView = (RecyclerView) findViewById(R.id.rv_news_list);
        newsList = new ArrayList<>();

        adapter = new NewsListAdapter(this, newsList, new ByDateModified());

        prepareNews();

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.sortList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        LoginMenuInflater.inflateLogin(inflater,menu);
        inflater.inflate(R.menu.news_list_menu, menu);
        if(LoginState.getInstance().getUserId() == Constant.INVALID_USER_ID) {
            menu.findItem(R.id.action_create_new_news).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_create_new_news:
                createNews();
                return true;
            case R.id.action_sort_by_date:
                adapter.setSortStrategy(new ByDateModified());
                sortList();
                return true;
            case R.id.action_sort_by_title:
                adapter.setSortStrategy(new ByTitle());
                sortList();
                return true;
            case R.id.action_sort_by_user:
                adapter.setSortStrategy(new ByUsers());
                sortList();
                return true;
            default:
                if(LoginMenuInflater.handleOnMenuItemClick(item, getApplicationContext())) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    private void createNews() {
        Intent intent = new Intent(this, CreateNewsActivity.class);
        startActivity(intent);
    }

    private void sortList() {
        adapter.sortList();
    }

    private void prepareNews() {
        LiveData<List<News>> liveNews = AppDatabase.getInstance(this).newsDao().getAllNews();
        liveNews.observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {
                if(news == null) return;
                List<SimpleNews> simpleNews = new ArrayList<>(news.size());
                for(News n: news) {
                    simpleNews.add(SimpleNews.getSimpleNews(n, getApplicationContext()));
                }
                adapter.setNewsList(simpleNews);
            }
        });
    }

}
