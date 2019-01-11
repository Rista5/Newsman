package com.example.me.tp_hwe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comment implements Serializable {
    private int id;
    private String content;
    private User user;
    private int belongsToNewsId;

    public Comment(int id, String content, User user, int belongsToNewsId){
        this.id = id;
        this.content = content;
        this.belongsToNewsId=belongsToNewsId;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBelongsToNewsId() {
        return belongsToNewsId;
    }

    @Override
    public String toString() {
        return user.getUsername()+":    "+ content;
    }

    public static List<String> commentsToStrings(List<Comment> comments){
        List<String> result = new ArrayList<>();
        for(Comment c: comments){
            result.add(c.toString());
        }
        return result;
    }
}
