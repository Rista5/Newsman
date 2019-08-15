package com.newsman.newsman.REST.Get;

import android.content.Context;
import android.util.JsonReader;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.Database.PictureDao;
import com.newsman.newsman.ServerEntities.JsonDeserializer;
import com.newsman.newsman.ServerEntities.Picture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPictureFromRest extends GetFromRest {

    private List<Picture> pictureList;

    @Override
    public String getRoute() {
        return Constant.PICTURE_ROUTE;
    }

    @Override
    public Picture readObject(JsonReader jsonReader) throws IOException {
        return JsonDeserializer.readPicture(jsonReader);
    }

    @Override
    public void readJsonArray(JsonReader jsonReader) throws IOException {
        pictureList = new ArrayList<>();
        while(jsonReader.hasNext()){
            pictureList.add(readObject(jsonReader));
        }
    }

    @Override
    public void updateDB(Context context) {
        PictureDao pictureDao = AppDatabase.getInstance(context).pictureDao();
        for(Picture p: pictureList){
            pictureDao.insertPicture(p);
        }
    }
}
