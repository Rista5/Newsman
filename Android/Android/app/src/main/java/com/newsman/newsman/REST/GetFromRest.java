package com.newsman.newsman.REST;

import android.content.Context;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class GetFromRest {
    private String ipAddress;
    private String route;

    public GetFromRest(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public abstract Serializable readObject(JsonReader jsonReader) throws IOException;

    public abstract String getRoute();

    public abstract void readJsonArray(JsonReader jsonReader) throws IOException;

    public abstract void updateDB(Context context);

    public void Get(final Context context) {
        AppExecutors.getInstance().getNetworkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpURLConnection httpConnection =
                                    (HttpURLConnection) RestConnectionFactory
                                            .createConnection(getRoute());
                            httpConnection.setRequestProperty("Accept", "Application/json");

                            ArrayList<Serializable> list = null;
                            if (httpConnection.getResponseCode() == 200) {
                                InputStream responseBody = httpConnection.getInputStream();
                                InputStreamReader isr = new InputStreamReader(responseBody, "UTF-8");
                                JsonReader jsonReader = new JsonReader(isr);

                                readJsonArray(jsonReader);
                                jsonReader.close();
                            }
                            httpConnection.disconnect();
                            updateDB(context);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
