package com.newsman.newsman.REST.Put.ConnectionStrategy;

import android.util.JsonWriter;

import com.newsman.newsman.REST.Put.WriteJson.WriteJson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class Put extends ConnectionConsumer {

    public static final String PUT = "PUT";

    private WriteJson jsonStrategy;
    public Put(WriteJson jsonStrategy) {
        this.jsonStrategy = jsonStrategy;
    }


    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);

        jsonStrategy.writeJson(jsonWriter);
        jsonWriter.close();
        outputStream.flush();
        outputStream.close();
        connection.getResponseCode();
    }

    @Override
    public String getType() {
        return PUT;
    }
}
