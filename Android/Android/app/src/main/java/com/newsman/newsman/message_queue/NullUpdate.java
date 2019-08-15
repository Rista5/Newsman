package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONObject;

public class NullUpdate extends DBUpdate {

    NullUpdate(String operation, JSONObject json, Context context) {
        super(operation, json, context);
    }

    @Override
    public void update() {

    }
}
