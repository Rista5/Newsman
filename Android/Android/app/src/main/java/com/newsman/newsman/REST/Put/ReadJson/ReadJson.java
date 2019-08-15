package com.newsman.newsman.REST.Put.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;

public abstract class ReadJson {
    public abstract void readJson(JsonReader jsonReader) throws IOException;
    //TODO smisli bolji nacin za update, mozda da vraca objekat
    public abstract void updateDB(Context context);
}
