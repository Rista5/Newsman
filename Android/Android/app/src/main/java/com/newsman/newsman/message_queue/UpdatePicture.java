package com.newsman.newsman.message_queue;

import android.content.Context;
import android.util.Base64;

import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Picture;

import org.json.JSONException;
import org.json.JSONObject;

class UpdatePicture extends DBUpdate {

    private Picture picture;

    UpdatePicture(String operation, JSONObject json, Context context) throws JSONException {
        super(operation, json, context);
        picture = parsePicture(json);
    }

    @Override
    public void update() {
        switch (mOperation) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).pictureDao().insertPicture(picture);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).pictureDao().updatePicture(picture);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).pictureDao().deletePicture(picture);
                break;
        }
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
