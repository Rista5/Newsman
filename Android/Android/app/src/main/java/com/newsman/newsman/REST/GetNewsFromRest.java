package com.newsman.newsman.REST;

import android.util.JsonReader;

import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Handler;

public class GetNewsFromRest extends GetFromRest {

    private static String ROUTE = "/News/";
    public GetNewsFromRest(String ipAddress, String bundleKey){
        super(ipAddress,bundleKey);
    }

    @Override
    public News readObject(JsonReader jsonReader)throws IOException {
        return JsonSerializer.readNews(jsonReader);
    }

    @Override
    public String getRoute() {
        return ROUTE;
    }
}
