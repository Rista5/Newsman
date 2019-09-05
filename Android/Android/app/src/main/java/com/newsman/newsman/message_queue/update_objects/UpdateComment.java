package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.google.gson.Gson;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.model.dtos.CommentDTO;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateComment extends DBUpdate {

    private Comment comment;

    UpdateComment(MessageInfo info, Context context) {
        super(info, context);
        if(info.getJsonString() != null) {
            comment = parseComment(info.getJsonString());
        }
    }

    @Override
    public void update() {
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).commentDao().insertComment(comment);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).commentDao().updateComment(comment);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).commentDao().deleteCommentById(messageInfo.getObjectId());
                break;
        }
    }

    private Comment parseComment(String jsonString) {
        return CommentDTO.getComment(GsonFactory.newIstance().fromJson(jsonString, CommentDTO.class));
    }
}
