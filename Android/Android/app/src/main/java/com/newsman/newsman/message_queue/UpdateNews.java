package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.DateGetter;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateNews extends DBUpdate {

    private News news;
    private List<DBUpdate> updateComments;
    private List<DBUpdate> updatePictures;

    UpdateNews(MessageInfo info, Context context) throws JSONException {
        super(info, context);
        if(info.getJsonObject() != null){
            news = parseNews(info.getJsonObject());
        }
    }

    @Override
    public void update() {
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).newsDao().insertNews(news);
                updateSubobjects();
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).newsDao().updateNews(news);
                updateSubobjects();
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).newsDao()
                        .deleteNewsById(messageInfo.getObjectId());
                AppDatabase.getInstance(mContext).commentDao()
                        .deleteCommentsForNews(messageInfo.getObjectId());
                AppDatabase.getInstance(mContext).pictureDao()
                        .deletePictureByIdWithData(mContext, messageInfo.getObjectId());
                break;
        }
    }

    private void updateSubobjects(){
        for(DBUpdate updateComment : updateComments){
            updateComment.update();
        }
        for(DBUpdate updatePicture : updatePictures) {
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
            JSONObject commJson = commentsJson.getJSONObject(i);
            MessageInfo info = new MessageInfo(messageInfo.getNewsId(), commJson.getInt("Id"),
                    messageInfo.getOperation(), commJson);
            updateComments.add(new UpdateComment(info, mContext));
        }
        JSONArray picturesArray = json.getJSONArray("Pictures");
        updatePictures = new ArrayList<>(picturesArray.length());
        for(int i=0; i<picturesArray.length(); i++) {
            JSONObject picJson = picturesArray.getJSONObject(i);
            MessageInfo info = new MessageInfo(messageInfo.getNewsId(), picJson.getInt("Id"),
                    messageInfo.getOperation(), picJson);
            updatePictures.add(new UpdatePicture(info, mContext));
        }
        List<Comment> comments = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        String lastModified = json.getString("LasModified");
        // pozadina se ponasa kao klasicna slika prilikom update-a
        int backId = Constant.INVALID_PICTURE_ID;
        JSONObject pictureJson = json.getJSONObject("BackgroundPicture");
        if(pictureJson!=null && pictureJson.getInt("Id") != Constant.INVALID_PICTURE_ID) {
            backId = pictureJson.getInt("Id");
            MessageInfo info = new MessageInfo(messageInfo.getNewsId(), backId,
                    messageInfo.getOperation(), pictureJson);
            updatePictures.add(new UpdatePicture(info, mContext));
        }
        return new News(id,title,content,comments, DateGetter.parseDate(lastModified), pictures, backId);
    }
}
