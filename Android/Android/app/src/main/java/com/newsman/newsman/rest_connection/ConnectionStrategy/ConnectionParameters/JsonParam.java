package com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters;

public class JsonParam implements ConnectionParam {
    @Override
    public String requestType() {
        return "application/json";
    }

    @Override
    public String responseType() {
        return "application/json";
    }
}
