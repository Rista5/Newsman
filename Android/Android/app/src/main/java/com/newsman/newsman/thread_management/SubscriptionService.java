package com.newsman.newsman.thread_management;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.newsman.newsman.activities.MainActivity;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.R;
import com.newsman.newsman.message_queue.MQClient;

import java.io.IOException;

public class SubscriptionService extends IntentService {


    public static final String SUBSCRIBE = "subscribe";
    public static final String UNSUBSCRIBE = "unsubscribe";
    public static final String TEMP_SUBSCRIBE = "temp_subscribe";
    public static final String TEMP_UNSUBSCRIBE = "temp_upsubscribe";
    public static final String START = "start";
    public static final String STOP = "stop";

    public static void startClient(Context context) {
        Intent startMQClient = new Intent(context, SubscriptionService.class);
        startMQClient.setAction(SubscriptionService.START);
        context.startService(startMQClient);
    }

    public SubscriptionService() {
        super("SubscriptionService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent == null) return;
        String action = intent.getAction();
        if(action == null) return;
        switch (action){
            case SUBSCRIBE:
                subscribeToNews(getNewsId(intent));
                break;
            case UNSUBSCRIBE:
                unsubscribeFromNews(getNewsId(intent));
                break;
            case TEMP_SUBSCRIBE:
                tempSubscribe(getNewsId(intent));
                break;
            case TEMP_UNSUBSCRIBE:
                tempUnsubscribe(getNewsId(intent));
                break;
            case START:
                startClient();
                break;
            case STOP:
                stopClient();
                break;
            default:
                break;
        }
    }

    public void startClient() {
        int[] ids = AppDatabase.getInstance(getApplicationContext()).newsDao().getSubscribedNewsIds();
        ClientSingleton.getClient(getApplicationContext()).startService(ids);
    }

    public void stopClient() {
        ClientSingleton.removeClient();
    }

    private void subscribeToNews(int newsId){
        if(newsId <= 0){
            displayToast(R.string.subscribe_error);
            return;
        }
        try {
            ClientSingleton.getClient(getApplicationContext()).subscribeToNews(newsId);
            AppDatabase.getInstance(getApplicationContext()).newsDao().subscribeToNews(newsId);
            displayToast(R.string.action_subscribe_to_news);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unsubscribeFromNews(int newsId){
        if(newsId <= 0){
            displayToast(R.string.subscribe_error);
            return;
        }
        try {
            ClientSingleton.getClient(getApplicationContext()).unsubscribeFromNews(newsId);
            AppDatabase.getInstance(getApplicationContext()).newsDao().unsubscribeFromNews(newsId);
            displayToast(R.string.action_unsubscribe_from_news);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tempSubscribe(int newsId) {
        if(newsId <= 0){
            displayToast(R.string.subscribe_error);
            return;
        }
        try {
            ClientSingleton.getClient(getApplicationContext()).subscribeTempNews(newsId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tempUnsubscribe(int newsId) {
        if(newsId <= 0){
            displayToast(R.string.subscribe_error);
            return;
        }
        try {
            ClientSingleton.getClient(getApplicationContext()).unsubscribeFromTempNews(newsId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNewsId(Intent intent) {
        int id = -1;
        Bundle b = intent.getExtras();
        if(b != null ){
            id = b.getInt(Constant.NEWS_BUNDLE_KEY);
        }
        return id;
    }

    private void displayToast(String message) {
        MainActivity.runOnUI(() -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        });
    }

    private void displayToast(int resourceStringId) {
        MainActivity.runOnUI(() -> {
            Toast.makeText(getApplicationContext(), resourceStringId, Toast.LENGTH_LONG).show();
        });
    }
}
