package com.newsman.newsman.Entities;

import java.util.Date;

public class NewsItem {

    private String title;
    private Date lastModified;
    private String userModifier;
    private String content;

    public NewsItem() {}

    public NewsItem(String title, Date lastModified, String userModifier, String content) {
        this.title = title;
        this.lastModified = lastModified;
        this.userModifier = userModifier;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getUserModifier() {
        return userModifier;
    }

    public void setUserModifier(String userModifier) {
        this.userModifier = userModifier;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
