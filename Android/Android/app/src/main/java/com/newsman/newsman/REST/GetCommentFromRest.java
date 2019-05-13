package com.newsman.newsman.REST;

import android.util.JsonReader;

import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.JsonSerializer;

import java.io.IOException;
import java.io.Serializable;

public class GetCommentFromRest extends GetFromRest {

    private static String ROUTE = "/comments/";

    public GetCommentFromRest(String ipAddress, String bundleKey) {
        super(ipAddress, bundleKey);
    }

    @Override
    public Comment readObject(JsonReader jsonReader) throws IOException {
        return JsonSerializer.readComment(jsonReader);
    }

    @Override
    public String getRoute() {
        return ROUTE;
    }
}
