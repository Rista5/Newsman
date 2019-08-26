package com.newsman.newsman.auxiliary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.newsman.newsman.server_entities.Picture;

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
