package com.newsman.newsman.REST;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;

import com.newsman.newsman.AppExecutors;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public abstract class GetFromRest {
    private String ipAddress;
    private String route;
    private String bundleKey;
    public GetFromRest(String ipAddress, String bundleKey){
        this.ipAddress = ipAddress;
        this.bundleKey = bundleKey;
    }

    public abstract Serializable readObject(JsonReader jsonReader) throws IOException;
    public abstract String getRoute();

    public void Get(final Handler hanlder) {
        AppExecutors.getInstance().getNetworkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" +  ipAddress + ":52752/api" + getRoute());
                            HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                            myConnection.setRequestProperty("Accept", "Application/json");

                            String text = "";
                            ArrayList<Serializable> list = null;
                            if (myConnection.getResponseCode() == 200) {
                                InputStream responseBody = myConnection.getInputStream();
                                InputStreamReader isr = new InputStreamReader(responseBody, "UTF-8");
                                JsonReader jsonReader = new JsonReader(isr);

                                list = new ArrayList<>();
                                jsonReader.beginArray();
                                while(jsonReader.hasNext()){
                                    list.add(readObject(jsonReader));
                                }
                                jsonReader.close();
                            }
                            myConnection.disconnect();
                            Message msg = hanlder.obtainMessage();

                            Bundle bundle = new Bundle();
                            bundle.putSerializable(bundleKey, list);
                            msg.setData(bundle);
                            hanlder.sendMessage(msg);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

    }
}
