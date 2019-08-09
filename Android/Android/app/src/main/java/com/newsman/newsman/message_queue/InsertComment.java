package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;

import org.json.JSONException;
import org.json.JSONObject;

public class InsertComment extends InsertObject {

    private Comment comment;
    private InsertUser insertUser;

    public InsertComment(JSONObject json, Context context) throws JSONException {
        super(json, context);
        comment = parseComment(json);
    }

    @Override
    public void insertRecord() {
        insertUser.insertRecord();
        AppDatabase.getInstance(mContext).commentDao().insertComment(comment);
    }

    Comment parseComment(JSONObject json) throws JSONException {
        int commentId = json.getInt("Id");
        insertUser = new InsertUser(json.getJSONObject("CreatedBy"), mContext);
        String commentContent = json.getString("Content");
        int belongsToNews = json.getInt("BelongsToNewsId");
        String postDate = json.getString("PostDate");
        return new Comment(commentId, commentContent, insertUser.getUser().getId(), belongsToNews, postDate);
    }
}
