package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONObject;

public abstract class UpdateObject extends DBUpdateObject{

    public UpdateObject(JSONObject json, Context context) {
        super(json, context);
    }

    @Override
    public void update() {
        updateRecord();
    }
    public abstract void updateRecord();
}
