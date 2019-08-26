package com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters;

public class OctetParam implements ConnectionParam {
    @Override
    public String requestType() {
        return "application/octet-stream";
    }

    @Override
    public String responseType() {
        return "application/octet-stream";
    }
}
