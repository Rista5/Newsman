package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class DBUpdate {

    Context mContext;
    String mOperation;

    DBUpdate(String operation, JSONObject json, Context context) {
        mContext = context;
        mOperation = operation;
    }

    public abstract void update() throws JSONException;
}
