package com.newsman.newsman.REST.ConnectionStrategy;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.REST.ReadJson.ReadJson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Get extends ConnectionConsumer {

    public static final String GET = "GET";

    private ReadJson readJson;
    private Context context;

    public Get(Context context, ReadJson readJson) {
        super(new JsonParam());
        this.readJson = readJson;
        this.context = context;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == 200) {
            InputStream responseBody = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(isr);

            readJson.readJson(jsonReader);
            jsonReader.close();
        }
        connection.disconnect();
        readJson.updateDB(context);
    }

    @Override
    public String getType() {
        return GET;
    }
}
