package com.example.me.tp_hwe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Me on 1/9/2019.
 */

public class GetUserClass {

    private String ipAddress;

    public GetUserClass(String ip)
    {
        ipAddress = ip + "/api/User/";
    }

    public void Get(final int id, final Handler hanlder) {
        Thread getThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(ipAddress + id);
                            HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                            myConnection.setRequestProperty("Accept", "Application/json");

                            String text = "";
                            if (myConnection.getResponseCode() == 200) {
                                InputStream responseBody = myConnection.getInputStream();
                                InputStreamReader isr = new InputStreamReader(responseBody, "UTF-8");
                                JsonReader jsonReader = new JsonReader(isr);


                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    text += jsonReader.nextName() + ":" + jsonReader.nextString();
                                    text += "\n\n\n";
                                }
                                jsonReader.close();
                            }
                            myConnection.disconnect();
                            Message msg = hanlder.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", text);
                            msg.setData(bundle);
                            hanlder.sendMessage(msg);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        getThread.start();
    }
}
