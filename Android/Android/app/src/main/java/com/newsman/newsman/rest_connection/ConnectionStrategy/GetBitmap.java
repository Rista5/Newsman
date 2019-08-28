package com.newsman.newsman.rest_connection.ConnectionStrategy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureLoader;
import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.rest_connection.ConnectionStrategy.ConnectionParameters.OctetParam;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class GetBitmap extends ConnectionConsumer {

    private int pictureId;
    private int newsId;
    private Context context;
    public GetBitmap(int pictureId, int newsId, Context context){
        super(new OctetParam());
        this.pictureId = pictureId;
        this.newsId = newsId;
        this.context = context;
    }

    @Override
    public void useConnection(HttpURLConnection connection) throws IOException {
        Bitmap bmp = null;
        if (connection.getResponseCode() == 200) {
            InputStream responseBody = connection.getInputStream();
            bmp = BitmapFactory.decodeStream(responseBody);
            BitmapCache.getInstance().setBitmap(pictureId, newsId,bmp);
            //TODO loader na file sistem
//            PictureLoader.savePictureData(context,pictureId,bmp);
//            AppDatabase.getInstance(context).pictureDao().setPictureDiscFlag(pictureId, Constant.PICTURE_ON_DISC);
        }
        connection.disconnect();
    }

    @Override
    public String getType() {
        return Get.GET;
    }
}
