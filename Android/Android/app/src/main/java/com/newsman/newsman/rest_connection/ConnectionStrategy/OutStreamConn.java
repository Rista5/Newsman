package com.newsman.newsman.rest_connection.ConnectionStrategy;

import android.graphics.Bitmap;

import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.OctetParam;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class OutStreamConn extends ConnectionConsumer {

    private Bitmap bitmap;

    public OutStreamConn(Bitmap bitmap) {
        super(new OctetParam());
        this.bitmap = bitmap;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        connection.getResponseCode();
    }

    @Override
    public String getType() {
        return "PUT";
    }
}
