package com.newsman.newsman.auxiliary.picture_helpers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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

    public static Bitmap getBitmap(byte[] data) throws NullPointerException {
        if(data == null)
            throw new NullPointerException("Data for converting was null");
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        return BitmapFactory.decodeStream(bis);
    }

    public static Bitmap getBitmap(byte[] data, int reqWidth, int reqHeight) throws NullPointerException {
        if(data == null)
            throw new NullPointerException("Data for converting was null");
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Rect padding = new Rect(-1, -1, -1,-1);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(bis, padding, options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(bis, padding, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap loadResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
