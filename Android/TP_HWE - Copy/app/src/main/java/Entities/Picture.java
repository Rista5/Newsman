package Entities;

import android.graphics.Bitmap;

/**
 * Created by Me on 1/10/2019.
 */

public class Picture {
    private int Id;
    private String Name;
    private String Description;
    private int BelongsToNewsId;
    //private byte[] PictureData;
    private Bitmap PictureData;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getBelongsToNewsId() {
        return BelongsToNewsId;
    }

    public void setBelongsToNewsId(int belongsToNewsId) {
        BelongsToNewsId = belongsToNewsId;
    }

    public Bitmap getPictureData() {
        return PictureData;
    }

    public void setPictureData(Bitmap pictureData) {
        PictureData = pictureData;
    }
}
