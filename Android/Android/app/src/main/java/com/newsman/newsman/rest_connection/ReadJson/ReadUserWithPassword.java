package com.newsman.newsman.rest_connection.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.server_entities.UserWithPassword;

import java.io.IOException;

public class ReadUserWithPassword extends ReadJson {

    private UserWithPassword user;

    @Override
    public void readJson(JsonReader jsonReader) throws IOException {
        user = JsonDeserializer.readUserWithPassword(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        LoginState.getInstance().setUser(user);
    }

    @Override
    public ReadJson clone() {
        return new ReadUserWithPassword();
    }
}
