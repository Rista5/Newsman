package com.newsman.newsman.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.SimpleNews;
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
//                adapter.setNewsList(news);
                createLoader(news);
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
