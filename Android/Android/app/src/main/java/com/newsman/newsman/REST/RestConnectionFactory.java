package com.newsman.newsman.REST;

import com.newsman.newsman.Auxiliary.Constant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestConnectionFactory {

    public static HttpURLConnection createConnection(String route) throws IOException {
        URL url = new URL("http://" + Constant.getIpAddress() + ":52752/api" + route);
        return (HttpURLConnection) url.openConnection();
    }
}
