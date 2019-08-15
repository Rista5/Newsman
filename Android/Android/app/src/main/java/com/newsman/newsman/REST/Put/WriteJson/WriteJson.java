package com.newsman.newsman.REST.Put.WriteJson;

import android.util.JsonWriter;

import java.io.IOException;

public abstract class WriteJson {
    public abstract void writeJson(JsonWriter jsonWriter) throws IOException;
}
