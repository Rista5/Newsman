package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateComment extends DBUpdate {

    private Comment comment;
    private UpdateUser updateUser;

    UpdateComment(String operation, JSONObject json, Context context) throws JSONException {
        super(operation, json, context);
        comment = parseComment(json);
    }

    @Override
    public void update() {
        updateUser.update();
        switch (mOperation) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).commentDao().insertComment(comment);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).commentDao().updateComment(comment);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).commentDao().deleteComment(comment);
                break;
        }
    }

    private Comment parseComment(JSONObject json) throws JSONException {
        int commentId = json.getInt("Id");
        updateUser = new UpdateUser(mOperation, json.getJSONObject("CreatedBy"), mContext);
        String commentContent = json.getString("Content");
        int belongsToNews = json.getInt("BelongsToNewsId");
        String postDate = json.getString("PostDate");
        return new Comment(commentId, commentContent, updateUser.getUser().getId(), belongsToNews, postDate);
    }
}
