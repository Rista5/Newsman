package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUser extends DBUpdate {

    private User user;

    UpdateUser(String operation, JSONObject json, Context context) throws JSONException {
        super(operation, json, context);
        user = parseUser(json);
    }

    @Override
    public void update() {
        switch (mOperation) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).userDao().insertUser(user);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).userDao().updateUser(user);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).userDao().deleteUser(user);
                break;
        }
    }

    private User parseUser(JSONObject json) throws JSONException {
        return new User(
                Integer.parseInt(json.getString("Id")),
                json.getString("Username")
        );
    }

    public User getUser() {return user;}
}
