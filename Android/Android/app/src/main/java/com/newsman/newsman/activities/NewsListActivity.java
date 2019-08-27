package com.newsman.newsman.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.R;
import com.newsman.newsman.server_entities.SimpleNews;
import com.newsman.newsman.adapters.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<SimpleNews>>{

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private List<SimpleNews> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        recyclerView = (RecyclerView) findViewById(R.id.rv_news_list);
        newsList = new ArrayList<>();

        adapter = new NewsListAdapter(this, newsList);

        prepareNews();

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
//                createLoader(news);
            }
        });
    }

    private void createLoader(List<News> news) {
        Bundle data = new Bundle();
        data.putParcelableArrayList(Constant.NEWS_BUNDLE_KEY, new ArrayList<News>(news));
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        Loader<List<SimpleNews>> loader = loaderManager.getLoader(Constant.PICTURE_LOADER_ID);

        if(loader == null) {
            loader = loaderManager.initLoader(Constant.PICTURE_LOADER_ID, data, this);
        } else {
            loader = loaderManager.restartLoader(Constant.PICTURE_LOADER_ID, data, this);
        }
        loader.startLoading();
    }

    @NonNull
    @Override
    public Loader<List<SimpleNews>> onCreateLoader(int i, @Nullable Bundle bundle) {
        List<News> news = new ArrayList<>();
        if(bundle != null) {
            news = bundle.getParcelableArrayList(Constant.NEWS_BUNDLE_KEY);
        }
        return new PictureLoader(this, news);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<SimpleNews>> loader, List<SimpleNews> simpleNews) {
        adapter.setNewsList(simpleNews);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<SimpleNews>> loader) {

    }
}
