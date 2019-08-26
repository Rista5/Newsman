package com.newsman.newsman.server_entities;

import java.util.Date;

public class CommentWithUsername {

    private int id;
    private String content;
    private int belongsToNewsId;
    private Date postDate;
    private int createdById;
    private String username;

    public CommentWithUsername(Comment c, String username) {
        id = c.getId();
        content = c.getContent();
        belongsToNewsId = c.getBelongsToNewsId();
        postDate = c.getPostDate();
        createdById = c.getCreatedById();
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getBelongsToNewsId() {
        return belongsToNewsId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public int getCreatedById() {
        return createdById;
    }

    public String getUsername() {
        return username;
    }

}
