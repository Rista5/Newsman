package com.newsman.newsman.rest_connection.ReadJson;

import android.content.Context;
import android.graphics.Bitmap;

import com.newsman.newsman.picture_management.BitmapCache;

public class ReadSimpleNewsWithPitcure extends ReadSimpleNews {

    @Override
    public void updateDB(Context context) {
        super.updateDB(context);
        //BitmapCache.getInstance().putBitmap(news.getBackgroundId(),news.getId());
    }

    @Override
    public ReadJson clone() {
        return new ReadSimpleNewsWithPitcure();
    }
}
