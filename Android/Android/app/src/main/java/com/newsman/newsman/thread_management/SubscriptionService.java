package com.newsman.newsman.thread_management;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.R;

public class SubscriptionService extends IntentService {


    public static final String SUBSCRIBE = "subscribe";
    public static final String UNSUBSCRIBE = "unsubscribe";
    public static final String START = "start";
    public static final String STOP = "stop";

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
                subscribeToNews(intent);
                break;
            case UNSUBSCRIBE:
                unsubscribeFromNews(intent);
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
        int[] newsIds = AppDatabase.getInstance(getApplicationContext())
                .newsDao().getSubscribedNewsIds();
        MQClientThread.startNewInstance(Constant.getIpAddress(), getApplicationContext(), newsIds);
    }

    public void stopClient() {
        MQClientThread.deleteThread();
    }

    private void subscribeToNews(Intent intent){
        int id = getNewsId(intent);
        if(id <= 0){
            Toast.makeText(getApplicationContext(), R.string.subscribe_error, Toast.LENGTH_LONG).show();
            return;
        }
        AppDatabase.getInstance(getApplicationContext()).newsDao().subscribeToNews(id);
        startClient();
        Toast.makeText(getApplicationContext(), R.string.action_subscribe_to_news, Toast.LENGTH_LONG).show();
    }

    private void unsubscribeFromNews(Intent intent){
        int id = getNewsId(intent);
        if(id <= 0){
            Toast.makeText(getApplicationContext(), R.string.subscribe_error, Toast.LENGTH_LONG).show();
            return;
        }
        AppDatabase.getInstance(getApplicationContext()).newsDao().unsubscribeFromNews(id);
        startClient();
        Toast.makeText(getApplicationContext(), R.string.action_unsubscribe_from_news, Toast.LENGTH_LONG).show();
    }

    private int getNewsId(Intent intent) {
        int id = -1;
        Bundle b = intent.getExtras();
        if(b != null ){
            id = b.getInt(Constant.NEWS_BUNDLE_KEY);
        }
        return id;
    }
}
