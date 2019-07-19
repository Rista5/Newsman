package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateFactory {
    public static UpdateObject CreateUpdateObject(String type, JSONObject jsonObject, Context context) throws JSONException {
        if(type.equals("UserDTO")) {
            return new UpdateUser(jsonObject, context);
        } else if (type.equals("NewsDTO")) {
            return new UpdateNews(jsonObject, context);
        } else if(type.equals("CommentDTO")) {
            return new UpdateComment(jsonObject, context);
        }
        return new NullUpdateObject(jsonObject, context);
    }
}
