package com.newsman.newsman.message_queue;

import android.content.Context;
import android.graphics.Bitmap;

import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.Auxiliary.PictureLoader;

import org.json.JSONException;

public class UpdateBackground extends UpdatePicture {

    UpdateBackground(MessageInfo info, Context context) throws JSONException {
        super(info, context);
    }

    @Override
    public void update() {
        switch (messageInfo.getOperation()) {
            case MQClient.opInsert:
                savePictureData();
                break;
            case MQClient.opUpdate:
                savePictureData();
                break;
            case MQClient.opDelete:
                PictureLoader.deletePictureData(mContext, messageInfo.getObjectId());
                break;
        }
    }

    private void savePictureData() {
        Bitmap bmp = picture.getPictureData();
        PictureLoader.savePictureData(mContext, picture.getId(), bmp);
    }
}
