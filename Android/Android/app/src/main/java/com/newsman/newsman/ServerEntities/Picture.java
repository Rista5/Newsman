package com.newsman.newsman.ServerEntities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Me on 1/10/2019.
 */
@Entity(tableName = "picture")
public class Picture implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private String description;
    private int belongsToNewsId;
    private byte[] pictureData;

    public Picture() {
        pictureData = new byte[1];
    }

    @Ignore
    public Picture(int id, String name, String description, int belongsToNewsId, byte[] pictureData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.belongsToNewsId = belongsToNewsId;
        this.pictureData = pictureData;
    }


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
