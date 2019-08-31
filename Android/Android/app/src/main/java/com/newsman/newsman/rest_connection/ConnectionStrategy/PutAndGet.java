package com.newsman.newsman.rest_connection.ConnectionStrategy;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonWriter;

import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.JsonParam;
import com.newsman.newsman.rest_connection.ReadJson.ReadJson;
import com.newsman.newsman.rest_connection.ReadJson.ReadPrototype;
import com.newsman.newsman.rest_connection.WriteJson.WriteJson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PutAndGet extends ConnectionConsumer {

    private WriteJson jsonStrategyWriter;
    private Context context;

    public PutAndGet(Context context, WriteJson jsonStrategyW) {
        super(new JsonParam());
        this.jsonStrategyWriter = jsonStrategyW;
        this.context = context;
    }

    public void useConnection(HttpURLConnection connection) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);

        jsonStrategyWriter.writeJson(jsonWriter);
        jsonWriter.close();
        outputStream.flush();
        outputStream.close();
        if (connection.getResponseCode() == 200) {
            InputStream responseBody = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(isr);

            ReadJson readJson = ReadPrototype.getInstance().GetReader(jsonStrategyWriter.getClass());
            readJson.readJson(jsonReader);
            readJson.updateDB(context);
            jsonReader.close();
        }
    }

    @Override
    public String getType() {
        return Put.PUT;
    }
}