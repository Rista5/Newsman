package com.example.me.tp_hwe;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class News implements Serializable {
    private int id;
    private String title;
    private String content;
    private List<Comment> comments;
    private String lastModified;

    public News (int id, String title, String content, List<Comment> comments, String lastModified){
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.lastModified = lastModified;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return "Id: " + id+ "   "+title;
    }
}
