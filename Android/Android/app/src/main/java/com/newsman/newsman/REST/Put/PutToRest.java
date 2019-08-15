package com.newsman.newsman.REST.Put;

import android.util.JsonWriter;

import com.newsman.newsman.AppExecutors;
import com.newsman.newsman.REST.RestConnectionFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public abstract class PutToRest implements SendComponent {

    public abstract String getRoute();
    public abstract void writeJsonObject(JsonWriter jsonWriter) throws IOException;


    public static final String GET = "GET";

    public void put() {
        AppExecutors.getInstance().getNetworkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        OutputStream outputStream;
                        try {
                            HttpURLConnection httpConnection = RestConnectionFactory
                                    .createConnection(getRoute(), "PUT");
//                            httpConnection.setRequestMethod("PUT");
//                            httpConnection.setRequestProperty("Content-Type", "application/json");
//                            httpConnection.setRequestProperty("Accept","application/json");
//                            httpConnection.setDoOutput(true);

                            outputStream = httpConnection.getOutputStream();
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                            JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);

                            writeJsonObject(jsonWriter);
                            jsonWriter.close();
                            outputStream.flush();
                            outputStream.close();
                            httpConnection.getResponseCode();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public void sendRequest() {
        put();
    }
}
