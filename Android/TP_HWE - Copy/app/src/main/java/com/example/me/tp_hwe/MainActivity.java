package com.example.me.tp_hwe;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button setIp, connect;
    String ipAddress;
    EditText testiramo;
    Handler updateHandler=null;
    Handler newsHandler = null;
    ArrayList<User> displayedUsers = null;

    ArrayList<News> newsList =  null;
    NewsListAdapter adapter = null;
    Context context = null;
    final Handler newsUpdateHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            News updatedNews = (News)bundle.getSerializable("news");
            boolean found = false;
            for(int i=0;i<newsList.size();i++){
                if(newsList.get(i).getId() == updatedNews.getId()){
                    newsList.set(i,updatedNews);
                    found = true;
                }
            }
            adapter.notifyDataSetChanged();
        }
    };


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        testiramo = findViewById(R.id.editName);
        setIp = (Button) findViewById(R.id.setIPButton);
        setIp.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.editIP);
                ipAddress = editText.getText().toString();
                //za sad ovde
                MQSubscriber sub = new MQSubscriber(ipAddress);
                sub.subscribe(updateHandler,newsUpdateHandler);
            }
        });


        final Handler userHandler = new Handler() {

            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Object obj = bundle.getSerializable("users");
                if(obj instanceof ArrayList)
                    displayedUsers = (ArrayList<User>)obj;
                displayUsers();

            }
        };
        connect = (Button) findViewById(R.id.connectButton);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //probavljanje vesti
                GetNewsFromRest getNewsFromRest = new GetNewsFromRest(ipAddress,"news");
                getNewsFromRest.Get(newsHandler);
            }
        });

        updateHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                Bundle bundle = msg.getData();
                User user = (User) bundle.getSerializable("user");
                boolean found = false;
                for(int i=0; i<displayedUsers.size();i++){
                    if(displayedUsers.get(i).getID() == user.getID()){
                        found = true;
                        displayedUsers.set(i,user);
                    }
                }
                if(!found){
                    displayedUsers.add(user);
                }
                displayUsers();
            }
        };

        newsHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                Bundle bundle = msg.getData();
                newsList = (ArrayList<News>)bundle.getSerializable("news");
                ListView listView = findViewById(R.id.news_list);
                adapter = new NewsListAdapter(context,R.layout.news_list_element,newsList);
                listView.setAdapter(adapter);
            }
        };



    }

    private void displayUsers(){
        TextView userView = findViewById(R.id.users_list);
        userView.setText("");
        for(User u: displayedUsers){
            userView.append(u.toString());
        }

    }

}
