package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.server_entities.Picture;

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
                        .insertPicture(picture);
                break;
            case MQClient.opUpdate:
                AppDatabase.getInstance(mContext).pictureDao()
                        .updatePicture(picture);
//                BitmapCache.getInstance().updateBitmap(picture.getId(),picture.getBelongsToNewsId());
                break;
            case MQClient.opDelete:
                AppDatabase.getInstance(mContext).pictureDao()
                        .deletePictureById(messageInfo.getObjectId());
                break;
        }
    }

    protected Picture parsePicture(JSONObject json) throws JSONException {
        return JSONParser.parsePicture(json);
    }
}
