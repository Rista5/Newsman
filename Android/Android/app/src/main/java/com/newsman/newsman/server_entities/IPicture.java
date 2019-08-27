package com.newsman.newsman.server_entities;

import android.graphics.Bitmap;

public interface IPicture {
    void setId(int id);

    String getName();

    String getDescription();

    int getBelongsToNewsId();

    Bitmap getPictureData();
    void setPictureData(Bitmap pictureData);

}
