package com.newsman.newsman.rest_connection.ConnectionStrategy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.OctetParam;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class InStreamConn extends com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionConsumer {

    private Context mContext;
    private int pictureId = Constant.INVALID_PICTURE_ID;
    private int newsId = Constant.INVALID_NEWS_ID;

    public InStreamConn(Context mContext, int pictureId) {
        super(new OctetParam());
        this.mContext = mContext;
        this.pictureId = pictureId;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        Bitmap bmp = null;
        if (connection.getResponseCode() == 200) {
            InputStream responseBody = connection.getInputStream();
            bmp = BitmapFactory.decodeStream(responseBody);
            BitmapCache.getInstance().setBitmap(pictureId, newsId, bmp);
//            PictureLoader.savePictureData(mContext, pictureId, bmp);
        }
        connection.disconnect();
    }

    @Override
    public String getType() {
        return "GET";
    }
}
