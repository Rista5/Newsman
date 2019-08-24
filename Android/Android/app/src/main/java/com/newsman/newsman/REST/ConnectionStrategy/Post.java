package com.newsman.newsman.REST.ConnectionStrategy;

import android.util.JsonWriter;

import com.newsman.newsman.REST.WriteJson.WriteJson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class Post extends ConnectionConsumer {

    public static final String POST = "POST";
    private WriteJson writeJson;

    public Post(WriteJson writeJson) {
        this.writeJson = writeJson;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);

        writeJson.writeJson(jsonWriter);
        jsonWriter.close();
        outputStream.flush();
        outputStream.close();
        connection.getResponseCode();
    }

    @Override
    public String getType() {
        return POST;
    }
}