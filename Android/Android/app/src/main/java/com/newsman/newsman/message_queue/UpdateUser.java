package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUser extends DBUpdate {

    private User user;

    UpdateUser(MessageInfo info, Context context) throws JSONException {
        super(info, context);
        if(info.getJsonObject() != null) {
            user = parseUser(info.getJsonObject());
        }
    }

    @Override
    public void update() {
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).userDao().insertUser(user);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).userDao().updateUser(user);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).userDao().deleteUserById(messageInfo.getObjectId());
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
