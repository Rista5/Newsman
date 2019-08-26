package com.newsman.newsman.rest_connection.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.server_entities.SimpleNews;

import java.io.IOException;

public class WriteSimpleNews extends WriteJson {

    private SimpleNews news;

    public WriteSimpleNews(SimpleNews news) {
        this.news = news;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {
        JsonSerializer.writeSimpleNews(jsonWriter, news);
    }
}
