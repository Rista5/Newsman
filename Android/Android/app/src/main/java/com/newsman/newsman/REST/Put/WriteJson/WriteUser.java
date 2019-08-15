package com.newsman.newsman.REST.Put.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.User;

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
