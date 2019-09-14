package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.google.gson.Gson;
import com.newsman.newsman.auxiliary.picture_helpers.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.model.db_entities.SimpleNews;
import com.newsman.newsman.model.dtos.SimpleNewsDTO;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateSimpleNews extends DBUpdate {

    private SimpleNews news;

    UpdateSimpleNews(MessageInfo info, Context context) {
        super(info, context);
        if(info.getJsonString() != null) {
            news = parseSimpleNews(info.getJsonString());
        }
    }

    @Override
    public void update() {
        News news = new News();
        SimpleNews.populateNews(news, this.news);
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).newsDao().insertNews(news);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).newsDao().updateNews(news);
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

    private SimpleNews parseSimpleNews(String jsonString) {
        return SimpleNewsDTO.getSimpleNews(GsonFactory.newIstance().fromJson(jsonString, SimpleNewsDTO.class));
    }
}
