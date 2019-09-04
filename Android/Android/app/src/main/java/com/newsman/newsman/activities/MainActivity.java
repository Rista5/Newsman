package com.newsman.newsman.activities;

import android.annotation.SuppressLint;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.new_rest.BitmapConnector;
import com.newsman.newsman.new_rest.NewsConnector;
import com.newsman.newsman.new_rest.PictureConnector;
import com.newsman.newsman.new_rest.dtos.PictureDTO;
import com.newsman.newsman.new_rest.retrofit_services.PictureService;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.thread_management.AppExecutors;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.R;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Get;
import com.newsman.newsman.rest_connection.ReadJson.ReadComposite;
import com.newsman.newsman.rest_connection.ReadJson.ReadNews;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
    }

    private void setUpViews() {
        findViewById(R.id.lounch_news_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().getNetworkIO()
                        .execute(NewsConnector.loadAllNews(getApplicationContext()));

                Intent startMQClient = new Intent(getApplicationContext(), SubscriptionService.class);
                startMQClient.setAction(SubscriptionService.START);
                startService(startMQClient);

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
    }

    private void startService(){
        Intent intent = new Intent(this, SubscriptionService.class);
        intent.setAction("Action");
        startService(intent);
    }

    private void sendMessage() {
        Intent intent = new Intent(this, SubscriptionService.class);
        intent.setAction("Message");
        startService(intent);
    }

    private void logNews() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    private Picture generateTestPicture(Bitmap bmp) {
        Picture p = new Picture();
        p.setId(Constant.INVALID_PICTURE_ID);
        p.setName(Integer.toString((int)new Date().getTime())+".PNG");
        p.setDescription("Nova fotka");
        p.setBelongsToNewsId(6);
        p.setPictureData(bmp);
        return p;
    }

    private void testMq() {
        int[] subs = new int[100];
        for(int i=0; i< 100; i++)
            subs[i] = i+1;
        AppExecutors.getInstance().getNetworkIO().execute(
                new MQClient(Constant.getIpAddress(), this, subs));
    }

    private void testPictureSave(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.getBaseUrl())
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .build();
        PictureService service = retrofit.create(PictureService.class);
        Call<PictureDTO> call = service.getPicture(66);

        call.enqueue(new Callback<PictureDTO>() {

            @Override
            public void onResponse(Call<PictureDTO> call, Response<PictureDTO> response) {
                PictureDTO pic = response.body();
            }

            @Override
            public void onFailure(Call<PictureDTO> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

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
