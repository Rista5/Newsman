package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class InsertObject extends DBUpdateObject {

    public InsertObject(JSONObject json, Context context) {
        super(json, context);
    }

    @Override
    public void update() throws JSONException {
        insertRecord();
    }

    public abstract void insertRecord() throws JSONException;
}
