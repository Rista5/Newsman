package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateComment extends UpdateObject {

    private Comment comment;
    private UpdateUser updateUser;
    private Context context;

    public UpdateComment(JSONObject json, Context context) throws JSONException {
        super(json, context);
        comment = parseComment(json);
        this.context = context;
    }

    @Override
    public void updateRecord() {
        updateUser.updateRecord();
        AppDatabase.getInstance(context).commentDao().updateComment(comment);
    }

    private Comment parseComment(JSONObject json) throws JSONException {
        int commentId = json.getInt("Id");
        updateUser = new UpdateUser(json.getJSONObject("CreatedBy"), context);
        String commentContent = json.getString("Content");
        int belongsToNews = json.getInt("BelongsToNewsId");
        String postDate = json.getString("PostDate");
        return new Comment(commentId, commentContent, updateUser.getUser().getId(), belongsToNews, postDate);
    }
}
