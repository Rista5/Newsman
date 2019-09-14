package com.newsman.newsman.picture_management.ConnectionDI;

import android.graphics.Bitmap;

import com.newsman.newsman.rest_connection.rest_connectors.BitmapConnector;
import com.newsman.newsman.thread_management.AppExecutors;

public class BitmapRetrofitRestConnection implements BitmapRestConnection {
    @Override
    public void getBitmap(int pictureId, int newsId) {
        AppExecutors.getInstance().getNetworkIO().execute(
            BitmapConnector.loadBitmap(pictureId, newsId));
    }

    @Override
    public void putBitmap(int pictureId, int newsId, Bitmap bitmap) {
        AppExecutors.getInstance().getNetworkIO().execute(BitmapConnector.saveBitmap(newsId,pictureId,bitmap));
    }
}
