package com.newsman.newsman.auxiliary.picture_helpers;

import android.graphics.Bitmap;

import com.newsman.newsman.model.db_entities.Picture;

public class PictureData {
    private Picture picture;
    private Bitmap bitmap;

    public PictureData(Picture picture, Bitmap bitmap) {
        this.picture = picture;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
