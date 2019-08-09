package com.newsman.newsman.REST;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.PictureDao;
import com.newsman.newsman.ServerEntities.JsonSerializer;
import com.newsman.newsman.ServerEntities.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GetPictureByIdFromRest extends GetFromRest {

    private Picture picture;
    private int id = -1;

    public GetPictureByIdFromRest(int id) {
        this.id = id;
    }

    @Override
    public String getRoute() {
        return Constant.PICTURE_ROUTE + id;
    }

    @Override
    public Picture readObject(JsonReader jsonReader) throws IOException {
        return JsonSerializer.readPicture(jsonReader);
    }

    @Override
    public void readJsonArray(JsonReader jsonReader) throws IOException {
        picture = readObject(jsonReader);
    }

    @Override
    public void updateDB(Context context) {
        PictureDao pictureDao = AppDatabase.getInstance(context).pictureDao();
        pictureDao.insertPicture(picture);
        //PictureLoader.savePictureData(context, picture);
    }
}
