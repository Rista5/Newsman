package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class DBUpdateFactory {
    public static DBUpdateObject createInstance(JSONObject json, Context context,
                                                String operation, String type) throws JSONException {
        if(operation.equals("Update")) {
            return UpdateFactory.CreateUpdateObject(type, json, context);
        } else {
            return InsertFactory.createInsertObject(type, json, context);
        }
    }
}
