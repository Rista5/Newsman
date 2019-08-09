package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONObject;

public class NullInsertObject extends InsertObject {

    public NullInsertObject(JSONObject json, Context context) {
        super(json, context);
    }

    @Override
    public void insertRecord() {

    }
}
