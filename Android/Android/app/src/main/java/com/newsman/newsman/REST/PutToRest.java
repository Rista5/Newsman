package com.newsman.newsman.REST;

import android.content.Context;
import android.util.JsonWriter;

import com.newsman.newsman.AppExecutors;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public abstract class PutToRest {
    private String route;

    public PutToRest() {}

    public abstract String getRoute();
    public abstract void writeJsonObject(JsonWriter jsonWriter) throws IOException;

    public void Post(final Context context) {
        AppExecutors.getInstance().getNetworkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        OutputStream outputStream;
                        try {
                            HttpURLConnection httpConnection = RestConnectionFactory
                                    .createConnection(getRoute());
                            httpConnection.setRequestMethod("PUT");
                            httpConnection.setRequestProperty("Content-Type", "application/json");
                            httpConnection.setRequestProperty("Accept","application/json");
                            httpConnection.setDoOutput(true);


                            outputStream = httpConnection.getOutputStream();
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                            JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);

                            writeJsonObject(jsonWriter);
                            jsonWriter.close();
                            httpConnection.getResponseCode();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}
