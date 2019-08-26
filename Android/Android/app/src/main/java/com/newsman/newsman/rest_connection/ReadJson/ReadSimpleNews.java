package com.newsman.newsman.rest_connection.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.SimpleNews;

import java.io.IOException;

public class ReadSimpleNews extends ReadJson {

    private SimpleNews news;

    @Override
    public void readJson(JsonReader jsonReader) throws IOException {
        news = JsonDeserializer.readSimpleNews(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        News updateNews = new News();
        SimpleNews.populateNews(updateNews, news);
        AppDatabase.getInstance(context).newsDao().insertNews(updateNews);
    }
    @Override
    public ReadJson clone() {
        return new ReadSimpleNews();
    }
}
