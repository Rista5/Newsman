package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONObject;

public abstract class UpdateObject {
    private Context appContext;

    public UpdateObject(JSONObject json, Context context) {
        appContext = context;
    }
    public abstract void updateRecord();
}
