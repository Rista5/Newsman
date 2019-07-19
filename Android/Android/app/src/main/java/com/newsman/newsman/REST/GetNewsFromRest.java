package com.newsman.newsman.REST;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.CommentDao;
import com.newsman.newsman.Database.NewsDao;
import com.newsman.newsman.Database.UserDao;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class GetNewsFromRest extends GetFromRest {

    private static String ROUTE = "/News/";
    private List<News> newsList;

    public GetNewsFromRest(String ipAddress){
        super(ipAddress);
    }

    @Override
    public News readObject(JsonReader jsonReader)throws IOException {
        return JsonSerializer.readNews(jsonReader);
    }

    @Override
    public String getRoute() {
        return ROUTE;
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
        //TODO try to perform update at once, without for loop
        //TODO try to find better aproach for update of comments and users
        for(News n: newsList) {
            newsDao.insertNews(n); // using insert because on conflict strategy is repalce
            for(Comment c: n.getComments()) {
                userDao.insertUser(c.getCreatedBy());
                commentDao.insertComment(c);
            }
        }
    }

}
