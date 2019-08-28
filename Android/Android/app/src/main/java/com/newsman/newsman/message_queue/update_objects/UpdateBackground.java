package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;
import android.graphics.Bitmap;

import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.picture_management.BitmapCache;

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
//        Bitmap bmp = picture.getPictureData();
//        BitmapCache.getInstance().setBitmap(picture.getId(), messageInfo.getNewsId(), bmp);
//        PictureLoader.savePictureData(mContext, picture.getId(), bmp);
    }
}
