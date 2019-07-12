package com.newsman.newsman;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.REST.GetNewsFromRest;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.activities.NewsListActivity;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button mLoadNewsButton;
    private Button mTestDbButton;
    private String IP_ADDRESSE = "192.168.1.7";
    private String KEY = "key";
    private AppDatabase mDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mDB = AppDatabase.getInstance(getApplicationContext());

        TextView tv = findViewById(R.id.test);
        Button button = findViewById(R.id.lounch_news_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsListActivity.class);
                startActivity(intent);
            }
        });
        mLoadNewsButton = findViewById(R.id.load_news_button);


        //TODO fix this handler warrning
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                final List<News> news = (List<News>)bundle.getSerializable(KEY);
                if (news != null) {
                    for(News n: news) {
                        Log.w("TAG", n.getTitle());

                    }
                    AppExecutors.getInstance().getDatabaseIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            for(News n: news) {
                                mDB.newsDao().updateNews(n);
                            }
                            Log.d("DB", "news successfully saved");
                        }
                    });
                }
            }
        };

        mLoadNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetNewsFromRest(IP_ADDRESSE, KEY).Get(handler);
            }
        });

        mTestDbButton = findViewById(R.id.load_news_from_db);
        mTestDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logNews();
            }
        });
    }

    private void logNews() {
        LiveData<List<News>> liveNews = mDB.newsDao().loadAllNews();
        liveNews.observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {
                Log.d("THIS", this.toString());
                for(News n:news) {
                    Log.d("DB", "news id: "+n.getId());
                }
            }
        });
    }
}
