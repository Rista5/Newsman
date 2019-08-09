package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InsertNews extends InsertObject {

    private News news;
    private List<InsertComment> insertComments;
    private List<InsertPicture> insertPictures;

    public InsertNews(JSONObject json, Context context) {
        super(json, context);
    }

    @Override
    public void insertRecord() throws JSONException {
        AppDatabase.getInstance(mContext).newsDao().insertNews(news);
        for(InsertComment insertComment: insertComments){
            insertComment.insertRecord();
        }
        for(InsertPicture insertPicture: insertPictures) {
            insertPicture.insertRecord();
        }
    }

    private News parseNews(JSONObject json) throws JSONException {
        int id = json.getInt("Id");
        String title = json.getString("Title");
        String content = json.getString("Content");
        JSONArray commentsJson = json.getJSONArray("Comments");
        insertComments = new ArrayList<>(commentsJson.length());
        for(int i=0; i<commentsJson.length(); i++) {
            insertComments.add(new InsertComment(commentsJson.getJSONObject(i), mContext));
        }
        JSONArray picturesArray = json.getJSONArray("Pictures");
        insertPictures = new ArrayList<>(picturesArray.length());
        for(int i=0; i<picturesArray.length(); i++) {
            insertPictures.add(new InsertPicture(picturesArray.getJSONObject(i), mContext));
        }
        List<Comment> comments = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String lastModified = json.getString("LasModified");
        return new News(id,title,content,comments,lastModified, pictures);
    }
}
