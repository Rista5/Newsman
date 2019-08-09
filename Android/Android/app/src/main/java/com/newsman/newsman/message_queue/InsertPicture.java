package com.newsman.newsman.message_queue;

import android.content.Context;
import android.util.Base64;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Picture;

import org.json.JSONException;
import org.json.JSONObject;

class InsertPicture extends InsertObject{

    private Picture picture;

    public InsertPicture(JSONObject json, Context context) {
        super(json, context);
    }

    @Override
    public void insertRecord() throws JSONException {
        AppDatabase.getInstance(mContext).pictureDao().insertPicture(picture);
    }

    private Picture parsePicture(JSONObject json) throws JSONException {
        int pictureId = json.getInt("Id");
        String name = json.getString("Name");
        String description = json.getString("Description");
        int newsId = json.getInt("BelongsToNewsId");
        byte[] data = Base64.decode(json.getString("PictureData"), Base64.DEFAULT);
        return new Picture(pictureId, name, description, newsId, data);
    }
}
