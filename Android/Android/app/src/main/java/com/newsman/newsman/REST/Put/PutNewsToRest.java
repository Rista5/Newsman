package com.newsman.newsman.REST.Put;

import android.util.JsonWriter;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.ServerEntities.Picture;

import java.io.IOException;

public class PutNewsToRest extends PutToRest {

    private News news;

    public PutNewsToRest(News news) {
        this.news = news;
    }

    @Override
    public String getRoute() {
        return Constant.NEWS_ROUTE;
    }

    @Override
    public void writeJsonObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(news.getId());
        jsonWriter.name("Title").value(news.getTitle());
        jsonWriter.name("Content").value(news.getContent());
        //TODO probaj bez nekih vrednosti post, tipa bez lastModified jer ga serveer postavlja
        //jsonWriter.name("LastModified").value("no date");
        jsonWriter.name("Comments");
        //verovatno ce ovako da ostane jer nema komentara na pocetku
        jsonWriter.beginArray().endArray();
        jsonWriter.name("Pictures");
        jsonWriter.beginArray();
        for(Picture p: news.getPictures()) {
            new PutPictureToRest(p).writeJsonObject(jsonWriter);
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }
}
