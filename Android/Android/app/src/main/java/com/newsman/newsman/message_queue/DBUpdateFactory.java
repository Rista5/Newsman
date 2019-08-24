package com.newsman.newsman.message_queue;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

class DBUpdateFactory {
    static DBUpdate createInstance(String type, MessageInfo info,
                                   Context context) throws JSONException {
        switch (type) {
            case "UserDTO":
                return new UpdateUser(info, context);
            case "NewsDTO":
                return new UpdateNews(info, context);
            case "CommentDTO":
                return new UpdateComment(info, context);
            case "PictureDTO":
                return new UpdatePicture(info, context);
            case "SimpleNewsDTO":
                return new UpdateSimpleNews(info, context);
        }
        return new NullUpdate(info, context);
    }
}
