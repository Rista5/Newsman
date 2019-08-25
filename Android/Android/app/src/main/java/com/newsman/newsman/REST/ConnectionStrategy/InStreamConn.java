package com.newsman.newsman.REST.ConnectionStrategy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsman.newsman.Auxiliary.PictureLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class InStreamConn extends com.newsman.newsman.REST.ConnectionStrategy.ConnectionConsumer {

    private Context mContext;
    private int picId = 0;

    public InStreamConn(Context mContext, int pictureId) {
        super(new OctetParam());
        this.mContext = mContext;
        picId = pictureId;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        Bitmap bmp = null;
        if (connection.getResponseCode() == 200) {
            InputStream responseBody = connection.getInputStream();
            bmp = BitmapFactory.decodeStream(responseBody);
            PictureLoader.savePictureData(mContext, picId, bmp);
        }
        connection.disconnect();
    }

    @Override
    public String getType() {
        return "GET";
    }
}
