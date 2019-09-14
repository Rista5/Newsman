package com.newsman.newsman.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.newsman.newsman.auxiliary.manu_helpers.LoginMenuInflater;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.picture_management.BitmapObservable;
import com.newsman.newsman.picture_management.BitmapObserver;
import com.newsman.newsman.rest_connection.rest_connectors.BitmapConnector;
import com.newsman.newsman.rest_connection.rest_connectors.NewsConnector;
import com.newsman.newsman.model.dtos.PictureDTO;
import com.newsman.newsman.rest_connection.retrofit_services.PictureService;
import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.thread_management.AppExecutors;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.thread_management.SubscriptionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView background;
    private BitmapObserver backgroundObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
        setIpAddress();
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        LoginMenuInflater.inflateLogin(inflater,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(LoginMenuInflater.handleOnMenuItemClick(item, this))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void setUpViews() {
        findViewById(R.id.lounch_news_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpServices();
                Intent intent = new Intent(getApplicationContext(), NewsListActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.lounch_login_activity).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.lounch_create_account).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
            startActivity(intent);
        });
        background = findViewById(R.id.main_activity_logo);
    }

    @Override
    protected void onDestroy() {
        if(backgroundObserver != null)
            backgroundObserver.removeObserver();
        super.onDestroy();
    }

    private void startUpServices() {
        AppExecutors.getInstance().getNetworkIO()
                .execute(NewsConnector.loadAllSimpleNews(getApplicationContext()));
        Intent intent = new Intent(this, SubscriptionService.class);
        intent.setAction(SubscriptionService.START);
        startService(intent);
    }

    private void setIpAddress(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String ip = preferences.getString(getString(R.string.pref_ip_key), getString(R.string.pref_ip));
        Constant.setIpAddress(ip);
    }

    private News genNews() {
        News n= new News();
        n.setId(0);
        n.setTitle("Title");
        List<Comment> coms = new ArrayList<>();
        coms.add(genComment());
        coms.add(genComment());
        n.setComments(coms);
        n.setContent("COTNENT");
        return n;
    }

    private Comment genComment() {
        Comment c= new Comment();
        c.setId(0);
        c.setCreatedById(3);
        c.setContent("Contentntntntnt");
        c.setPostDate(new Date());
        c.setUsername("Neki user");
        return c;
    }


    public static Handler UIHandler;
    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable run){
        UIHandler.post(run);
    }
}
