package com.newsman.newsman.rest_connection;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.ConnectionParam;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestConnectionFactory {

    public static HttpURLConnection createConnection(String route, String type,
                                                     ConnectionParam connectionParam) throws IOException {
        URL url = new URL("http://" + Constant.getIpAddress() + ":52752/api" + route);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(type);
        connection.setRequestProperty("Content-Type", connectionParam.requestType());
        connection.setRequestProperty("Accept", connectionParam.responseType());
        if(type.equals("PUT") || type.equals("POST"))
            connection.setDoOutput(true);
        if(type.equals("GET"))
            connection.setDoInput(true);
        return connection;
    }
}
