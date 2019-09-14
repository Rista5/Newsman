package com.newsman.newsman.model.db_entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.picture_helpers.PictureConverter;

/**
 * Created by Me on 1/10/2019.
 */
@Entity(tableName = "picture")
public class Picture implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private String description;
    private int belongsToNewsId;
    private int onDisc;

    @Ignore
    private Bitmap pictureData;
    @Ignore
    private int tempID;

    public Picture() {
        onDisc = Constant.PICRURE_NOT_ON_DISC;
    }

    @Ignore
    public Picture(int id, String name, String description, int belongsToNewsId, Bitmap pictureData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.belongsToNewsId = belongsToNewsId;
        this.pictureData = pictureData;
        onDisc = Constant.PICRURE_NOT_ON_DISC;
    }

    protected Picture(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        belongsToNewsId = in.readInt();
        byte[] arr = new byte[in.readInt()];
        in.readByteArray(arr);
        Bitmap bmp = null;
        try{
            bmp = PictureConverter.getBitmap(arr);
        }catch (NullPointerException e ){
            e.printStackTrace();
        }
        pictureData = bmp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(belongsToNewsId);
        byte[] arr = PictureConverter.getBitmapBytes(pictureData);
        dest.writeInt(arr.length);
        dest.writeByteArray(arr);
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Picture){
            Picture p = (Picture) obj;
            return p.getId() == this.getId();
        }
        return false;
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

    public Bitmap getPictureData() {
        return this.pictureData;
    }

    public void setPictureData(Bitmap pictureData) {
        this.pictureData = pictureData;
    }

    public int getTempID() {
        return tempID;
    }

    public void setTempID(int tempID) {
        this.tempID = tempID;
    }

    public int getOnDisc() {
        return onDisc;
    }

    public void setOnDisc(int onDisc) {
        this.onDisc = onDisc;
    }
}
