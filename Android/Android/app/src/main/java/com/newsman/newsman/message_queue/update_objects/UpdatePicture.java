package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.google.gson.Gson;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.model.db_entities.Picture;
import com.newsman.newsman.model.dtos.PictureDTO;

import org.json.JSONException;

class UpdatePicture extends DBUpdate {

    private Picture picture;

    UpdatePicture(MessageInfo info, Context context) {
        super(info, context);
        if(info.getJsonString() != null) {
            picture = parsePicture(info.getJsonString());
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

    private Picture parsePicture(String jsonString) {
        return PictureDTO.getPicture(GsonFactory.newIstance().fromJson(jsonString, PictureDTO.class));
    }
}
