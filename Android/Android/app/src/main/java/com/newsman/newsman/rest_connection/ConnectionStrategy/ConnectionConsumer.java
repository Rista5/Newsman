package com.newsman.newsman.rest_connection.ConnectionStrategy;

import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.ConnectionParam;

import java.io.IOException;
import java.net.HttpURLConnection;

public abstract class ConnectionConsumer {
    public abstract void useConnection(HttpURLConnection connection) throws IOException;
    public abstract String getType();

    private ConnectionParam connectionParam;

    public ConnectionConsumer(ConnectionParam connectionParam) {
        this.connectionParam = connectionParam;
    }

    public String routeExtension(){
        return "";
    }

    public ConnectionParam getConnectionParam() {
        return connectionParam;
    }
}