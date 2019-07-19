package com.newsman.newsman.message_queue;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

public class NullUpdateObject extends UpdateObject {

    public NullUpdateObject(JSONObject json, Context context) {
        super(json, context);
    }

    @Override
    public void updateRecord() {
        Log.d("NULL_OBJECT_UPDATE", "Update called");
    }
}
