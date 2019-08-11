package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUser extends UpdateObject {

    private User user;

    public UpdateUser(JSONObject json, Context context) throws JSONException {
        super(json, context);
        user = parseUser(json);
    }

    @Override
    public void updateRecord() {
        AppDatabase.getInstance(mContext).userDao().insertUser(user);
    }

    private User parseUser(JSONObject json) throws JSONException {
        return new User(
                Integer.parseInt(json.getString("Id")),
                json.getString("Username")
        );
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
