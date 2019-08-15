package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

class DBUpdateFactory {
    static DBUpdate createInstance(String type, String operation, JSONObject jsonObject,
                                   Context context) throws JSONException {
        switch (type) {
            case "UserDTO":
                return new UpdateUser(operation, jsonObject, context);
            case "NewsDTO":
                return new UpdateNews(operation, jsonObject, context);
            case "CommentDTO":
                return new UpdateComment(operation, jsonObject, context);
            case "PictureDTO":
                return new UpdatePicture(operation, jsonObject, context);
        }
        return new NullUpdate(operation, jsonObject, context);
    }
}
