package com.newsman.newsman.REST;

import android.util.JsonWriter;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.User;

import java.io.IOException;

public class PutUserToRest extends PutToRest {

    private User user;

    public PutUserToRest(User user) {
        this.user = user;
    }

    @Override
    public String getRoute() {
        return Constant.USER_ROUTE;
    }

    @Override
    public void writeJsonObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(user.getId());
        jsonWriter.name("Username").value(user.getUsername());
        jsonWriter.endObject();
    }
}
