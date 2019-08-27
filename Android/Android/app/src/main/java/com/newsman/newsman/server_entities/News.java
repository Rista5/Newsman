package com.newsman.newsman.server_entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import android.os.Parcel;
import android.os.Parcelable;

import com.newsman.newsman.auxiliary.Constant;

import java.io.Serializable;
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
    private int subscribed;
    private int modifierId;
    private String modifierUsername;

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
        subscribed = Constant.UNSUBSCRIBED;
    }

    @Ignore
    public News(int id, String title, String content, List<Comment> comments, Date lastModified,
                List<Picture> pictures, int backgroundId, int modifierId, String modifierUsername) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = (comments != null) ? comments : new ArrayList<Comment>();
        this.pictures = (pictures != null) ? pictures : new ArrayList<Picture>();
        this.lastModified = lastModified;
        this.backgroundId = backgroundId;
        this.subscribed = Constant.UNSUBSCRIBED;
        this.modifierId = modifierId;
        this.modifierUsername = modifierUsername;
    }

    protected News(Parcel in) {
        this();
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        backgroundId = in.readInt();
        lastModified = new Date(in.readLong());
        subscribed = in.readInt();
        modifierId = in.readInt();
        modifierUsername = in.readString();
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
        dest.writeInt(subscribed);
        dest.writeInt(modifierId);
        dest.writeString(modifierUsername);
    }

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

    public int getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }

    public int getModifierId() {
        return modifierId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierUsername() {
        return modifierUsername;
    }

    public void setModifierUsername(String modifierUsername) {
        this.modifierUsername = modifierUsername;
    }
}
