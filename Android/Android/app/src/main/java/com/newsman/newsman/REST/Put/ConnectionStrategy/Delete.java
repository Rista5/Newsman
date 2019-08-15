package com.newsman.newsman.REST.Put.ConnectionStrategy;

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
        connection.connect();
    }

    @Override
    public String getType() {
        return DELETE;
    }
}
