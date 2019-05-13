package com.newsman.newsman.ServerEntities;

/**
 * Created by Me on 1/10/2019.
 */

public class Picture {
    private int id;
    private String name;
    private String description;
    private int belongsToNewsId;
    private byte[] pictureData;
    //    private Bitmap PictureData;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBelongsToNewsId() {
        return belongsToNewsId;
    }

    public void setBelongsToNewsId(int belongsToNewsId) {
        this.belongsToNewsId = belongsToNewsId;
    }

    public byte[] getPictureData() {
        return this.pictureData;
    }

    public void setPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
    }
}
