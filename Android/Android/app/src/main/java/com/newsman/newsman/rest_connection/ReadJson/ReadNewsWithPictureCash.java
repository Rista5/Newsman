package com.newsman.newsman.rest_connection.ReadJson;

import android.app.Application;
import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.activities.MainActivity;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadNewsWithPictureCash extends ReadJson{

    private ReadNews readNews;
    public void setReadNews(ReadNews readNews){
        this.readNews = readNews;
    }
    @Override
    public void readJson(JsonReader jsonReader) throws IOException {
        readNews.readJson(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        readNews.updateDB(context);
        News news = readNews.getNews();
        List<Picture> pictures = news.getPictures();
        List<Integer> oldId = new ArrayList<>();
        List<Integer> newId = new ArrayList<>();
        for (Picture pic: pictures) {
            oldId.add(pic.getTempID());
            newId.add(pic.getId());
        }
        BitmapCache.getInstance().putBitmapsCreateNews(oldId,newId,news.getId());
    }

    @Override
    public ReadJson clone() {
        ReadNewsWithPictureCash clone = new ReadNewsWithPictureCash();
        clone.setReadNews((ReadNews)this.readNews.clone());
        return clone;
    }
}
