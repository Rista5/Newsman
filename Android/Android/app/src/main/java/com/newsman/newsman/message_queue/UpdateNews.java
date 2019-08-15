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

public class UpdateNews extends DBUpdate {

    private News news;
    private List<UpdateComment> updateComments;
    private List<UpdatePicture> updatePictures;

    UpdateNews(String operation, JSONObject json, Context context) throws JSONException {
        super(operation, json, context);
        news = parseNews(json);
    }

    @Override
    public void update() {
        switch (mOperation) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).newsDao().insertNews(news);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).newsDao().updateNews(news);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).newsDao().deleteNews(news);
                break;
        }
        for(UpdateComment updateComment : updateComments){
            updateComment.update();
        }
        for(UpdatePicture updatePicture : updatePictures) {
            updatePicture.update();
        }
    }

    private News parseNews(JSONObject json) throws JSONException {
        int id = json.getInt("Id");
        String title = json.getString("Title");
        String content = json.getString("Content");
        JSONArray commentsJson = json.getJSONArray("Comments");
        updateComments = new ArrayList<>(commentsJson.length());
        for(int i=0; i<commentsJson.length(); i++) {
            updateComments.add(new UpdateComment(mOperation, commentsJson.getJSONObject(i), mContext));
        }
        JSONArray picturesArray = json.getJSONArray("Pictures");
        updatePictures = new ArrayList<>(picturesArray.length());
        for(int i=0; i<picturesArray.length(); i++) {
            updatePictures.add(new UpdatePicture(mOperation, picturesArray.getJSONObject(i), mContext));
        }
        List<Comment> comments = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String lastModified = json.getString("LasModified");
        return new News(id,title,content,comments,lastModified, pictures);
    }
}
