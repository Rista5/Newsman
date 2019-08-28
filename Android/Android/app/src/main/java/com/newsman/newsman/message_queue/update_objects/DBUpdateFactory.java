package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.newsman.newsman.message_queue.MessageInfo;

import org.json.JSONException;

public class DBUpdateFactory {
    public static DBUpdate createInstance(String type, MessageInfo info,
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
            case "RawPictureUpdate":
                return new UpdatePictureRaw(info,context);
        }
        return new NullUpdate(info, context);
    }
}
