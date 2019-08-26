package com.newsman.newsman.message_queue;

import android.content.Context;
import android.graphics.Bitmap;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.DateGetter;
import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.server_entities.SimpleNews;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateSimpleNews extends DBUpdate {

    private SimpleNews news;

    UpdateSimpleNews(MessageInfo info, Context context) throws JSONException {
        super(info, context);
        if(info.getJsonObject() != null) {
            news = parseSimpleNews(info.getJsonObject());
        }
    }

    @Override
    public void update() {
        News news = new News();
        SimpleNews.populateNews(news, this.news);
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).newsDao().insertNews(news);
                PictureLoader.savePictureData(mContext, this.news.getBackgroundId(), this.news.getBackgroundPicture());
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).newsDao().updateNews(news);
                PictureLoader.savePictureData(mContext, this.news.getBackgroundId(), this.news.getBackgroundPicture());
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).newsDao()
                        .deleteNews(news);
                AppDatabase.getInstance(mContext).commentDao()
                        .deleteCommentsForNews(messageInfo.getObjectId());
                AppDatabase.getInstance(mContext).pictureDao()
                        .deletePictureByIdWithData(mContext, messageInfo.getObjectId());
                break;
        }
    }

    private SimpleNews parseSimpleNews(JSONObject json) throws JSONException {
        int id = json.getInt("Id");
        String title = json.getString("Title");
        String content = json.getString("Content");
        String lastModified = json.getString("LasModified");
        Bitmap background = null;
        int backId = Constant.INVALID_PICTURE_ID;
        JSONObject backPic = json.getJSONObject("BackgroundPicture");
        if(backPic != null){
            Picture picture= JSONParser.parsePicture(json.getJSONObject("BackgroundPicture"));
            backId = picture.getId();
            background = picture.getPictureData();
        }
        return new SimpleNews(id, title, content, DateGetter.parseDate(lastModified),
                background, backId);
    }
}
