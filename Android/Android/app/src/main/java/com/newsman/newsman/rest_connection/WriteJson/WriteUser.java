package com.newsman.newsman.rest_connection.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.server_entities.User;

import java.io.IOException;

public class WriteUser extends WriteJson {

    private User user;

    public WriteUser(User user) {
        this.user = user;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {
        JsonSerializer.writeUser(jsonWriter, user);
    }
}
