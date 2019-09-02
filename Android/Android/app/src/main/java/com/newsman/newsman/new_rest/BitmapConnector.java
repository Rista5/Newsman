package com.newsman.newsman.new_rest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newsman.newsman.auxiliary.PictureConverter;
import com.newsman.newsman.new_rest.retrofit_services.BitmapService;
import com.newsman.newsman.picture_management.BitmapCache;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class BitmapConnector {
    public static Runnable saveBitmap(final int newsId, final int pictureId, final Bitmap bitmap) {
        return () -> {
            Retrofit retrofit = RetrofitFactory.createSimple();
            BitmapService service = retrofit.create(BitmapService.class);
            byte[] pictureData = PictureConverter.getBitmapBytes(bitmap);
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), pictureData);
            Call<ResponseBody> resp = service.savePicture(pictureId, newsId, body);
            try {
                retrofit2.Response rr2 = resp.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable loadBitmap(final int newsId, final int pictureId) {
        return () ->{
            Retrofit retrofit = RetrofitFactory.createInstance();
            BitmapService service = retrofit.create(BitmapService.class);
            Call<ResponseBody> resp = service.getPictureData(pictureId, newsId);
            try {
                retrofit2.Response<ResponseBody> res = resp.execute();
                if (res.body() == null) return;
                Bitmap bmp = PictureConverter.getBitmap(res.body().bytes());
                BitmapCache.getInstance().setBitmap(pictureId, newsId, bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
