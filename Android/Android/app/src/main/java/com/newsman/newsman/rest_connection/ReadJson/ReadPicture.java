package com.newsman.newsman.rest_connection.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.server_entities.Picture;

import java.io.IOException;

public class ReadPicture extends ReadJson {

    private Picture picture;

    @Override
    public void readJson(JsonReader jsonReader) throws IOException {
        picture = JsonDeserializer.readPicture(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        AppDatabase.getInstance(context).pictureDao().insertPicture(picture);
    }

    @Override
    public ReadJson clone() {
        return new ReadPicture();
    }
}
