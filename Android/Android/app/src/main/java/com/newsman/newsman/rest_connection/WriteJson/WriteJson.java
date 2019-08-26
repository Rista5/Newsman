package com.newsman.newsman.rest_connection.WriteJson;

import android.util.JsonWriter;

import java.io.IOException;

public abstract class WriteJson {
    public abstract void writeJson(JsonWriter jsonWriter) throws IOException;
}
