package com.newsman.newsman.REST.WriteJson;

import android.graphics.Bitmap;
import android.util.JsonWriter;

import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.News;

import java.io.IOException;

public class WriteNews extends WriteJson {

    private News news;
    private Bitmap background;

    public WriteNews(News news, Bitmap background) {
        this.news = news;
        this.background = background;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {
        JsonSerializer.writeNews(jsonWriter, news, background);
    }
}
