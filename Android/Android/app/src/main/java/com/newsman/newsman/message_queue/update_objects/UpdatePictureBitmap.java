package com.newsman.newsman.message_queue.update_objects;

import android.content.Context;

import com.newsman.newsman.message_queue.MQClient;
import com.newsman.newsman.message_queue.MessageInfo;
import com.newsman.newsman.picture_management.BitmapCache;

import org.json.JSONException;

public class UpdatePictureBitmap extends DBUpdate{

    private UpdatePictureInfo pictureInfo;
    UpdatePictureBitmap(MessageInfo info, Context context) {
        super(info, context);
        if(info.getJsonString() != null) {
            pictureInfo = deserializePictureInfo(info.getJsonString());
        }
    }
    @Override
    public void update() {
        switch (messageInfo.getOperation()){
            case MQClient.opUpdate:
                BitmapCache.getInstance().updateBitmap(mContext, pictureInfo.pictureId, pictureInfo.newsId);
                break;
                default:
                    break;
        }
    }

    private UpdatePictureInfo deserializePictureInfo(String jsonString){
        return GsonFactory.newIstance().fromJson(jsonString, UpdatePictureInfo.class);
    }

    class UpdatePictureInfo {
        private int pictureId;
        private int newsId;

        UpdatePictureInfo(int pictureId, int newsId) {
            this.pictureId = pictureId;
            this.newsId = newsId;
        }

        public int getPictureId() {
            return pictureId;
        }

        public void setPictureId(int pictureId) {
            this.pictureId = pictureId;
        }

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }
    }
}
