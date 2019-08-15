package com.newsman.newsman.REST.Put.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.JsonDeserializer;
import com.newsman.newsman.ServerEntities.Picture;

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
}
