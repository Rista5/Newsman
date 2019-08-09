package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class DBUpdateObject {

    protected Context mContext;

    public DBUpdateObject(JSONObject json, Context context) {
        mContext = context;
    }

    public abstract void update() throws JSONException;
}
