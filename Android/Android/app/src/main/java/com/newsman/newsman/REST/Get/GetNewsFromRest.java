package com.newsman.newsman.REST.Get;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.CommentDao;
import com.newsman.newsman.Database.NewsDao;
import com.newsman.newsman.Database.PictureDao;
import com.newsman.newsman.Database.UserDao;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.JsonDeserializer;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetNewsFromRest extends GetFromRest {

    private List<News> newsList;

    @Override
    public News readObject(JsonReader jsonReader)throws IOException {
        return JsonDeserializer.readNews(jsonReader);
    }

    @Override
    public String getRoute() {
        return Constant.NEWS_ROUTE;
    }

    @Override
    public void readJsonArray(JsonReader jsonReader) throws IOException {
        newsList = new ArrayList<>();
        jsonReader.beginArray();
        while(jsonReader.hasNext()){
            newsList.add(readObject(jsonReader));
        }
    }

    @Override
    public void updateDB(Context context) {
        NewsDao newsDao = AppDatabase.getInstance(context).newsDao();
        CommentDao commentDao = AppDatabase.getInstance(context).commentDao();
        UserDao userDao = AppDatabase.getInstance(context).userDao();
        PictureDao pictureDao = AppDatabase.getInstance(context).pictureDao();
        //TODO try to perform update at once, without for loop
        //TODO try to find better aproach for update of comments and users
        for(News n: newsList) {
            newsDao.insertNews(n); // using insert because on conflict strategy is repalce
            for(Comment c: n.getComments()) {
                userDao.insertUser(c.getCreatedBy());
                commentDao.insertComment(c);
            }
            for(Picture p: n.getPictures()) {
                pictureDao.insertPicture(p);
            }
        }
    }
}
