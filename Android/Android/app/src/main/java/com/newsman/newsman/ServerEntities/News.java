package com.newsman.newsman.ServerEntities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

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
public class News implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String title;
    private String content;
    private Date lastModified;
//    @Relation(parentColumn = "id", entityColumn = "belongsToNewsId", entity = Comment.class)
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
    public News(int id, String title, String content, ArrayList<Comment> comments, String lastModified) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
        try{
            this.lastModified = DateGetter.getDate(lastModified);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
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

}
