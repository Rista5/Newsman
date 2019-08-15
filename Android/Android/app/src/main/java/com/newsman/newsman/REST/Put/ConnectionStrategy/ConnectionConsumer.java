package com.newsman.newsman.REST.Put.ConnectionStrategy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

public abstract class ConnectionConsumer {
    public abstract void useConnection(HttpURLConnection connection) throws IOException;
    public abstract String getType();
}
