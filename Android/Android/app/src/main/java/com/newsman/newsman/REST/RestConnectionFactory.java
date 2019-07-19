package com.newsman.newsman.REST;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestConnectionFactory {
    private static String IP_ADDRESSE = "192.168.1.7";

    public static HttpURLConnection createConnection(String route) throws IOException {
        URL url = new URL("http://" +  IP_ADDRESSE + ":52752/api" + route);
        return (HttpURLConnection) url.openConnection();
    }
}
