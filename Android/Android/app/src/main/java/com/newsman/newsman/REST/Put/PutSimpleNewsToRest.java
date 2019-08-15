package com.newsman.newsman.REST.Put;

import android.util.JsonWriter;

import com.newsman.newsman.ServerEntities.SimpleNews;

import java.io.IOException;

public class PutSimpleNewsToRest extends PutToRest {

    private SimpleNews news;

    public PutSimpleNewsToRest(SimpleNews news){
        this.news = news;
    }

    @Override
    public String getRoute() {
        return null;
    }

    @Override
    public void writeJsonObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(news.getId());
        jsonWriter.name("Title").value(news.getTitle());
        jsonWriter.name("Content").value(news.getContent());
        jsonWriter.endArray();
        jsonWriter.endObject();
    }
}
