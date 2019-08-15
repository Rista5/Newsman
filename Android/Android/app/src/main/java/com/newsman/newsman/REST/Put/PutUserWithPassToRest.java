package com.newsman.newsman.REST.Put;

import android.util.JsonWriter;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.UserWithPassword;

import java.io.IOException;

public class PutUserWithPassToRest extends PutToRest {

    //private static String ROUTE = "/user/";
    private final UserWithPassword user;

    public PutUserWithPassToRest(UserWithPassword user) {
        this.user = user;
    }

    @Override
    public String getRoute() {
        return Constant.USER_ROUTE;
    }

    @Override
    public void writeJsonObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Username").value(user.getUsername());
        jsonWriter.name("Password").value(user.getPassword());
        jsonWriter.endObject();
    }
}
