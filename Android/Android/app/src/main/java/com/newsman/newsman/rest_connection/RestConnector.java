package com.newsman.newsman.rest_connection;

import com.newsman.newsman.thread_management.AppExecutors;
import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionConsumer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class RestConnector implements Runnable{

    private ConnectionConsumer connConsumer;
    private String route;

    public RestConnector(ConnectionConsumer connConsumer, String route) {
        this.connConsumer = connConsumer;
        this.route = route;
    }

    public void execute() {
        AppExecutors.getInstance().getNetworkIO().execute(this);
    }

    @Override
    public void run() {
        try {
            HttpURLConnection httpConnection = RestConnectionFactory.createConnection(
                    route + connConsumer.routeExtension(), connConsumer.getType(), connConsumer.getConnectionParam());

            connConsumer.useConnection(httpConnection);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
