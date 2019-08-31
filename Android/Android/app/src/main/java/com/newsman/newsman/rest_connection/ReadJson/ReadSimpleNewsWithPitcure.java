package com.newsman.newsman.rest_connection.ReadJson;

import android.content.Context;
import android.graphics.Bitmap;

//NE ide u prototype,
public class ReadSimpleNewsWithPitcure extends ReadSimpleNews {

    private int pictureTempId;
    private int pictureId;
    private int newsId;
    private Bitmap bitmap;


    public ReadSimpleNewsWithPitcure(int pictureTempId, int pictureId, int newsId, Bitmap bitmap){
        this.pictureTempId = pictureTempId;
        this.pictureId = pictureId;
        this.newsId = newsId;
        this.bitmap = bitmap;
    }

    @Override
    public void updateDB(Context context) {
        super.updateDB(context);
//        BitmapCache.getInstance().putBitmap(news.getBackgroundId(),news.getId());
    }

    @Override
    public ReadJson clone() {
        return new ReadSimpleNewsWithPitcure(pictureTempId, pictureId, newsId, bitmap);
    }
}
