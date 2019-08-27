package com.newsman.newsman.server_entities;

import android.graphics.Bitmap;

public class PictureProxy implements IPicture {

    private Picture picture;

    public PictureProxy(Picture picture){
        this.picture = picture;
    }

    @Override
    public void setId(int id) {
        picture.setId(id);
    }

    @Override
    public String getName() {
        return picture.getName();
    }

    @Override
    public String getDescription() {
        return picture.getDescription();
    }

    @Override
    public int getBelongsToNewsId() {
        return picture.getBelongsToNewsId();
    }

    @Override
    public Bitmap getPictureData() {
//        if(picture.getPictureData() == null) {
//            return null;
//        }
        return null;
    }

    @Override
    public void setPictureData(Bitmap pictureData) {
        picture.setPictureData(pictureData);
    }
}
