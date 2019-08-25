package com.newsman.newsman.Auxiliary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.newsman.newsman.ServerEntities.Picture;

import java.io.FileInputStream;

public class PictureTransportLoader extends AsyncTaskLoader<Picture> {

    private String file = "";
    private Picture picture;
    public PictureTransportLoader(@NonNull Context context, Picture picture, String fileName) {
        super(context);
        this.picture = picture;
        this.file = fileName;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Picture loadInBackground() {
        Bitmap bmp = null;
        try {
            FileInputStream is = getContext().openFileInput(file);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        picture.setPictureData(bmp);
        return picture;
    }
}
