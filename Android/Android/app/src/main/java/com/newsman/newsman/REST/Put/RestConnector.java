package com.newsman.newsman.REST.Put;

import android.util.JsonWriter;

import com.newsman.newsman.AppExecutors;
import com.newsman.newsman.REST.Put.ConnectionStrategy.ConnectionConsumer;
import com.newsman.newsman.REST.RestConnectionFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class RestConnector implements SendComponent{

    private ConnectionConsumer connConsumer;
    private String route;

    public RestConnector(ConnectionConsumer connConsumer, String route) {
        this.connConsumer = connConsumer;
        this.route = route;
    }

    public void run() {
        AppExecutors.getInstance().getNetworkIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        OutputStream outputStream;
                        try {
                            HttpURLConnection httpConnection = RestConnectionFactory
                                    .createConnection(route, connConsumer.getType());

                            connConsumer.useConnection(httpConnection);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public void sendRequest() {
        run();
    }
}
