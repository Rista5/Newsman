package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdateNews extends UpdateObject {

    private News news;
    private ArrayList<UpdateComment> updateComments;
    private Context context;

    public UpdateNews(JSONObject json, Context context) throws JSONException {
        super(json,context);
        news = parseNews(json);
        this.context = context;
    }

    @Override
    public void updateRecord() {
        AppDatabase.getInstance(context).newsDao().updateNews(news);
        for(UpdateComment updateComment: updateComments) {
            updateComment.updateRecord();
        }
    }

    private News parseNews(JSONObject json) throws JSONException {
        int id = json.getInt("Id");
        String title = json.getString("Title");
        String content = json.getString("Content");
        JSONArray commentsJson = json.getJSONArray("Comments");
        updateComments = new ArrayList<>(commentsJson.length());
        for(int i=0; i<commentsJson.length(); i++) {
            updateComments.add(new UpdateComment(commentsJson.getJSONObject(i), context));
        }
        ArrayList<Comment> comments = new ArrayList<>();
        String lastModified = json.getString("LasModified");
        return new News(id,title,content,comments,lastModified);
    }
}
