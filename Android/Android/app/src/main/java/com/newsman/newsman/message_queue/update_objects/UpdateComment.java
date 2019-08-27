package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.server_entities.Comment;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateComment extends DBUpdate {

    private Comment comment;
    private UpdateUser updateUser;

    UpdateComment(MessageInfo info, Context context) throws JSONException {
        super(info, context);
        if(info.getJsonObject() != null) {
            comment = parseComment(info.getJsonObject());
        }
    }

    @Override
    public void update() {
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                updateUser.update();
                AppDatabase.getInstance(mContext).commentDao().insertComment(comment);
                break;
            case MQClient.opUpdate:
                updateUser.update();
                AppDatabase.getInstance(mContext).commentDao().updateComment(comment);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).commentDao().deleteCommentById(messageInfo.getObjectId());
                break;
        }
    }

    private Comment parseComment(JSONObject json) throws JSONException {
        return JSONParser.parseComment(json);
    }
}
