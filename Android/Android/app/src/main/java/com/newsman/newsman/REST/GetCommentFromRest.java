package com.newsman.newsman.REST;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.CommentDao;
import com.newsman.newsman.Database.UserDao;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.JsonSerializer;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetCommentFromRest extends GetFromRest {

    private static String ROUTE = "/comments/";
    private List<Comment> commentList;

    public GetCommentFromRest(String ipAddress) {
        super(ipAddress);
    }

    @Override
    public Comment readObject(JsonReader jsonReader) throws IOException {
        return JsonSerializer.readComment(jsonReader);
    }

    @Override
    public String getRoute() {
        return ROUTE;
    }

    @Override
    public void readJsonArray(JsonReader jsonReader) throws IOException {
        commentList = new ArrayList<>();
        jsonReader.beginArray();
        while(jsonReader.hasNext()){
            commentList.add(readObject(jsonReader));
        }
    }

    @Override
    public void updateDB(Context context) {
        CommentDao dao = AppDatabase.getInstance(context).commentDao();
        UserDao userDao = AppDatabase.getInstance(context).userDao();
        for(Comment c: commentList) {
            dao.insertComment(c);
            userDao.updateUser(c.getCreatedBy());
        }
    }

}
