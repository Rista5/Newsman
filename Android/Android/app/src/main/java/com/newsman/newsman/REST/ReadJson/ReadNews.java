package com.newsman.newsman.REST.ReadJson;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

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

public class ReadNews extends ReadJson {

    private News news;

    @Override
    public void readJson(JsonReader jsonReader) throws IOException {
        news = JsonDeserializer.readNews(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        NewsDao newsDao = AppDatabase.getInstance(context).newsDao();
        CommentDao commentDao = AppDatabase.getInstance(context).commentDao();
        UserDao userDao = AppDatabase.getInstance(context).userDao();
        PictureDao pictureDao = AppDatabase.getInstance(context).pictureDao();
        //TODO try to perform update at once, without for loop
        //TODO try to find better aproach for update of comments and users
        newsDao.insertNews(news); // using insert because on conflict strategy is repalce
        for(Comment c: news.getComments()) {
            userDao.insertUser(c.getCreatedBy());
            commentDao.insertComment(c);
        }
        for(Picture p: news.getPictures()) {
            if(p.getPictureData()!=null)
                pictureDao.insertPictureWithLoader(context, p);
            else
                Log.d("delete picture", p.getId() +"    " +p.getName());
        }
    }

    @Override
    public ReadJson clone() {
        return new ReadNews();
    }
}
