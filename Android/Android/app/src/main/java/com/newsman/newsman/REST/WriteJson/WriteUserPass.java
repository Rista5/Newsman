package com.newsman.newsman.REST.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.UserWithPassword;

import java.io.IOException;

public class WriteUserPass extends WriteJson {

    private UserWithPassword user;

    public WriteUserPass(UserWithPassword user) {
        this.user = user;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {
        JsonSerializer.writeUserPass(jsonWriter, user);
    }
}
