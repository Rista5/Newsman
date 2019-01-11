package Entities;

import java.util.Date;
import java.util.List;

/**
 * Created by Me on 1/10/2019.
 */

public class News {
    private int Id;
    private String Title;
    private String Content;
    private Date LastModified;
    private List<Comment> Comments;
    private List<Picture> Pictures;
    private List<Audio> AudioRecordings;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getLastModified() {
        return LastModified;
    }

    public void setLastModified(Date lastModified) {
        LastModified = lastModified;
    }

    public List<Comment> getComments() {
        return Comments;
    }

    public void setComments(List<Comment> comments) {
        Comments = comments;
    }

    public List<Picture> getPictures() {
        return Pictures;
    }

    public void setPictures(List<Picture> pictures) {
        Pictures = pictures;
    }

    public List<Audio> getAudioRecordings() {
        return AudioRecordings;
    }

    public void setAudioRecordings(List<Audio> audioRecordings) {
        AudioRecordings = audioRecordings;
    }
}
