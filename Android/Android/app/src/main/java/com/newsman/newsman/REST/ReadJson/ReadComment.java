package com.newsman.newsman.REST.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.JsonDeserializer;

import java.io.IOException;

public class ReadComment extends ReadJson {

    private Comment comment;

    @Override
    public void readJson(JsonReader jsonReader) throws IOException {
        comment = JsonDeserializer.readComment(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        AppDatabase.getInstance(context).commentDao().insertComment(comment);
    }

    @Override
    public ReadJson clone() {
        return new ReadComment();
    }
}
