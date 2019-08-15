package com.newsman.newsman.REST.Put.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.News;

import java.io.IOException;

public class WriteNews extends WriteJson {

    private News news;

    public WriteNews(News news) {
        this.news = news;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {
        JsonSerializer.writeNews(jsonWriter, news);
    }
}
