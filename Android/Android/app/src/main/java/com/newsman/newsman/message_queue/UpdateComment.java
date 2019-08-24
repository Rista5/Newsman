package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;

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
        int commentId = json.getInt("Id");
        JSONObject userJson = json.getJSONObject("CreatedBy");
        MessageInfo info = new MessageInfo(
                messageInfo.getNewsId(),
                userJson.getInt("Id"),
                messageInfo.getOperation(),
                userJson
        );
        updateUser = new UpdateUser(info, mContext);
        String commentContent = json.getString("Content");
        int belongsToNews = json.getInt("BelongsToNewsId");
        String postDate = json.getString("PostDate");
        return new Comment(commentId, commentContent, updateUser.getUser().getId(), belongsToNews, postDate);
    }
}
