package com.newsman.newsman.REST.ConnectionStrategy;

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
