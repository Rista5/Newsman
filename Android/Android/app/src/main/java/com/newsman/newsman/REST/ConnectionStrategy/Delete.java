package com.newsman.newsman.REST.ConnectionStrategy;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;

public class Delete extends ConnectionConsumer {

    public static final String DELETE = "DELETE";
    private int id;

    public Delete(int id) {
        this.id = id;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        if(connection.getResponseCode() == 200) {
            Log.d("news deleted", "newsId = "+id );
        }
    }

    @Override
    public String getType() {
        return DELETE;
    }

    @Override
    public String routeExtension() {
        return Integer.toString(id);
    }
}
