package Entities;

import java.util.Date;

/**
 * Created by Me on 1/9/2019.
 */

public class Comment {

    int Id;
    String Content;
    int BelongsToNewsId;
    User CreatedBy;
    Date PostDate;

    //Getters
    public int getId() { return  Id; }

    public String getContent() {
        return Content;
    }

    public int getBelongsToNewsId() {
        return BelongsToNewsId;
    }

    public User getCreatedBy() {
        return CreatedBy;
    }

    public Date getPostDate() {
        return PostDate;
    }

    //Setters

    public void setId(int id) {
        Id = id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setBelongsToNewsId(int belongsToNewsId) {
        BelongsToNewsId = belongsToNewsId;
    }

    public void setCreatedBy(User createdBy) {
        CreatedBy = createdBy;
    }

    public void setPostDate(Date postDate) {
        PostDate = postDate;
    }

    //Constructor

    public Comment(int id, String content, int belongsToNewsId, User createdBy, Date postDate) {
        Id = id;
        Content = content;
        BelongsToNewsId = belongsToNewsId;
        CreatedBy = createdBy;
        PostDate = postDate;
    }
}
