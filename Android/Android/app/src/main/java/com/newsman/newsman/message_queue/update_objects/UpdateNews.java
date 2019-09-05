package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.google.gson.Gson;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.date_helpers.DateAux;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.model.db_entities.User;
import com.newsman.newsman.model.dtos.CommentDTO;
import com.newsman.newsman.model.dtos.NewsDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateNews extends DBUpdate {

    private News news;

    UpdateNews(MessageInfo info, Context context) {
        super(info, context);
        if(info.getJsonString() != null){
            news = parseNews(info.getJsonString());
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
        for(Comment updateComment : news.getComments()){
            AppDatabase.getInstance(mContext).commentDao().insertComment(updateComment);
        }
        for(Picture updatePicture : news.getPictures()) {
            AppDatabase.getInstance(mContext).pictureDao().insertPicture(updatePicture);
        }
    }

    private News parseNews(String jsonString) {
        return NewsDTO.getNews(GsonFactory.newIstance().fromJson(jsonString, NewsDTO.class));
    }
}
