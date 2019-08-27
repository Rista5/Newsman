package com.newsman.newsman.rest_connection.ConnectionStrategy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.ConnectionParam;
import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.OctetParam;
import com.newsman.newsman.rest_connection.ReadJson.ReadJson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class GetBitmap extends ConnectionConsumer {

    private int Id;
    public GetBitmap(int id){
        super(new OctetParam());
        this.Id = id;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        Bitmap bmp = null;
        if (connection.getResponseCode() == 200) {
            InputStream responseBody = connection.getInputStream();
            bmp = BitmapFactory.decodeStream(responseBody);
            BitmapCache.getInstance().setBitmap(this.Id,bmp);
        }
        connection.disconnect();
    }

    @Override
    public String getType() {
        return Get.GET;
    }
}
