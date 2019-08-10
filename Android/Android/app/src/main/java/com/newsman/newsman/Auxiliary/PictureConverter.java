package com.newsman.newsman.Auxiliary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class PictureConverter {

    public static Bitmap getImageViewBitmap(ImageView imageView) {
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }

    public static byte[] getBitmapBytes(Bitmap bmp) {
        int bmpQuality = 100;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, bmpQuality, bos);
        return bos.toByteArray();
    }

    public static Bitmap getBitmap(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        return BitmapFactory.decodeStream(bis);
    }
}
