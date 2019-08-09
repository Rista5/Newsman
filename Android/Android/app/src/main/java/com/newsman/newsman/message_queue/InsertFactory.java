package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class InsertFactory {
    public static InsertObject createInsertObject(String type, JSONObject jsonObject, Context context) throws JSONException {
        if (type.equals("UserDTO")) {
            return new InsertUser(jsonObject, context);
        } else if (type.equals("NewsDTO")) {
            return new InsertNews(jsonObject, context);
        } else if(type.equals("CommentDTO")) {
            return new InsertComment(jsonObject, context);
        }
        return new NullInsertObject(jsonObject, context);
    }
}
