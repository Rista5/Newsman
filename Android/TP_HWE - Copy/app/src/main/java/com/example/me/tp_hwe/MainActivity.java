package com.example.me.tp_hwe;

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
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button setIp, connect;
    String ipAddress, OcekivaniTekst;
    EditText testiramo;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testiramo = findViewById(R.id.editName);
        setIp = (Button) findViewById(R.id.setIPButton);
        setIp.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.editIP);
                ipAddress = editText.getText().toString();
            }
        });

        final Handler userHandler = new Handler() {

            public void handleMessage(Message msg) {
                String message = msg.getData().getString("msg");
                EditText tv = (EditText) findViewById(R.id.editName);
                tv.setText(message);
            }
        };
        connect = (Button) findViewById(R.id.connectButton);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetUserClass get = new GetUserClass(ipAddress);
                get.Get(1,userHandler);
            }
        });
    }


}
