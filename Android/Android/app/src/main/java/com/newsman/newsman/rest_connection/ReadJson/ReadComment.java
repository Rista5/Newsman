package com.newsman.newsman.rest_connection.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.server_entities.Comment;

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
