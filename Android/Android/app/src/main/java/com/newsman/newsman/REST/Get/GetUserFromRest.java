package com.newsman.newsman.REST.Get;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.JsonDeserializer;
import com.newsman.newsman.ServerEntities.User;

import java.io.IOException;

public class GetUserFromRest extends GetFromRest {

    private User user;


    @Override
    public User readObject(JsonReader jsonReader) throws IOException {
        return JsonDeserializer.readUser(jsonReader);
    }

    @Override
    public String getRoute() {
        return Constant.USER_ROUTE;
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
