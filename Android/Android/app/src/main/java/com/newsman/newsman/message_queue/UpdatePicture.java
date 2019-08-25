package com.newsman.newsman.message_queue;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.Database.AppDatabase;
import com.newsman.newsman.ServerEntities.Picture;

import org.json.JSONException;
import org.json.JSONObject;

class UpdatePicture extends DBUpdate {

    protected Picture picture;

    UpdatePicture(MessageInfo info, Context context) throws JSONException {
        super(info, context);
        if(info.getJsonObject() != null) {
            picture = parsePicture(info.getJsonObject());
        }
    }

    @Override
    public void update() {
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                AppDatabase.getInstance(mContext).pictureDao()
                        .insertPictureWithLoader(mContext, picture);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).pictureDao()
                        .updatePictureWithLoader(mContext, picture);
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).pictureDao()
                        .deletePictureByIdWithData(mContext, messageInfo.getObjectId());
                break;
        }
    }

    protected Picture parsePicture(JSONObject json) throws JSONException {
        int pictureId = json.getInt("Id");
        String name = json.getString("Name");
        String description = json.getString("Description");
        int newsId = json.getInt("BelongsToNewsId");
        byte[] data = Base64.decode(json.getString("PictureData"), Base64.DEFAULT);
        Bitmap bmp = null;
        try{
            bmp = PictureConverter.getBitmap(data);
        }catch (NullPointerException e ){
            e.printStackTrace();
        }
        return new Picture(pictureId, name, description, newsId, bmp);
    }
}
