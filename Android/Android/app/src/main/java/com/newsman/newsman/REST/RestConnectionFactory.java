package com.newsman.newsman.REST;

import com.newsman.newsman.Auxiliary.Constant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestConnectionFactory {

    public static HttpURLConnection createConnection(String route, String type) throws IOException {
        URL url = new URL("http://" + Constant.getIpAddress() + ":52752/api" + route);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(type);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept","application/json");
        if(type.equals("PUT") || type.equals("POST"))
            connection.setDoOutput(true);
        if(type.equals("GET"))
            connection.setDoInput(true);
        return connection;
    }
}
