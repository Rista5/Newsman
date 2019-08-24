package com.newsman.newsman.ServerEntities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import com.newsman.newsman.Auxiliary.DateGetter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Me on 1/10/2019.
 */
@Entity(tableName = "news")
public class News implements Serializable, Parcelable {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String title;
    private String content;
    private Date lastModified;
    private int backgroundId;

    @Ignore
    private Picture backgroundPic;
    @Ignore
    private List<Comment> comments;
    @Ignore
    private List<Picture> pictures;
    @Ignore
    private List<Audio> audioRecordings;

    public News() {
        comments = new ArrayList<>();
        pictures = new ArrayList<>();
        audioRecordings = new ArrayList<>();
    }

    @Ignore
    public News(int id, String title, String content,
                List<Comment> comments, Date lastModified, List<Picture> pictures, int backgroundId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = (comments != null) ? comments : new ArrayList<Comment>();
        this.pictures = (pictures != null) ? pictures : new ArrayList<Picture>();
        this.lastModified = lastModified;
        this.backgroundId = backgroundId;
    }

    protected News(Parcel in) {
        this();
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        backgroundId = in.readInt();
        lastModified = new Date(in.readLong());
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Audio> getAudioRecordings() {
        return audioRecordings;
    }

    public void setAudioRecordings(List<Audio> audioRecordings) {
        this.audioRecordings = audioRecordings;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public Picture getBackgroundPic() {
        return backgroundPic;
    }

    public void setBackgroundPic(Picture backgroundPic) {
        this.backgroundPic = backgroundPic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(backgroundId);
        dest.writeLong(lastModified.getTime());
    }
}
