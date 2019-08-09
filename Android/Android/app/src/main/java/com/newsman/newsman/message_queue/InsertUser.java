package com.newsman.newsman.message_queue;

import android.content.Context;

import com.newsman.newsman.ServerEntities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class InsertUser extends InsertObject {

    private User user;

    public InsertUser(JSONObject json, Context context) {
        super(json, context);
    }

    @Override
    public void insertRecord() {

    }

    private User parseUser(JSONObject json) throws JSONException {
        return new User(
                Integer.parseInt(json.getString("Id")),
                json.getString("Username")
        );
    }

    public User getUser() {return user;}
}
