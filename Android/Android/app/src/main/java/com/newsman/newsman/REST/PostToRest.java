package com.newsman.newsman.REST;

import android.content.Context;
import android.util.JsonWriter;

import com.newsman.newsman.AppExecutors;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public abstract class PostToRest {
    private String route;

    public abstract String getRoute();
    public abstract JSONObject getJsonObject();
    public abstract void writeJsonObject();

    public void Post(final Context context) {
        AppExecutors.getInstance().getNetworkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpURLConnection httpConnection = RestConnectionFactory
                                    .createConnection(getRoute());
                            httpConnection.setRequestMethod("POST");
                            httpConnection.setRequestProperty("Content-Type", "application/json");
                            httpConnection.setRequestProperty("Accept","application/json");
                            httpConnection.setDoOutput(true);

                            if(httpConnection.getResponseCode()==200) {
                                OutputStream outputStream = httpConnection.getOutputStream();
                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                                JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);

                                writeJsonObject();
                                jsonWriter.close();

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}
