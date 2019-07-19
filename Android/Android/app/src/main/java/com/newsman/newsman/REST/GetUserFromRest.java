package com.newsman.newsman.REST;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.UserDao;
import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.User;

import java.io.IOException;
import java.io.Serializable;

public class GetUserFromRest extends GetFromRest {

    private static String ROUTE = "/User/";
    private User user;

    public GetUserFromRest(String ipAddress) {
        super(ipAddress);
    }

    @Override
    public User readObject(JsonReader jsonReader) throws IOException {
        return JsonSerializer.readUser(jsonReader);
    }

    @Override
    public String getRoute() {
        return ROUTE;
    }

    @Override
    public void readJsonArray(JsonReader jsonReader) throws IOException {
        user = readObject(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        AppDatabase.getInstance(context).userDao().insertUser(user);
    }
}
