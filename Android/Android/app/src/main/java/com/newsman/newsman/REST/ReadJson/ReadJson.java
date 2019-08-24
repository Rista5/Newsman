package com.newsman.newsman.REST.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;

public abstract class ReadJson {
    public abstract void readJson(JsonReader jsonReader) throws IOException;
    //TODO smisli bolji nacin za updateDB, mozda da vraca objekat
    //TODO problem, da li moze samo da se insertuje ili moze jos nesto??
    public abstract void updateDB(Context context);
    public abstract ReadJson clone();
}
