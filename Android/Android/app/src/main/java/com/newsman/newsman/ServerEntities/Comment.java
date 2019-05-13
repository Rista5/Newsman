package com.newsman.newsman.ServerEntities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import com.newsman.newsman.Auxiliary.DateGetter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Me on 1/9/2019.
 */
@Entity(tableName = "comment",
        foreignKeys = {
                @ForeignKey(
                    entity = News.class,
                    parentColumns = "id",
                    childColumns = "belongsToNewsId",
                    onDelete = CASCADE),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "createdById",
                        onDelete = CASCADE)
        },
        indices = {
                @Index("belongsToNewsId"),
                @Index("createdById")
        })
public class Comment implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String content;
    private int belongsToNewsId;
    private int createdById;
    @Ignore
    private User createdBy;
    private Date postDate;

    //Constructor
    public Comment(){
    }

    @Ignore
    public Comment(int id, String content, User createdBy, int belongsToNewsId, String postDate) {
        this.id = id;
        this.content = content;
        this.belongsToNewsId = belongsToNewsId;
        this.createdBy = createdBy;
        this.setCreatedBy(createdBy);
        try{
            this.postDate = DateGetter.getDate(postDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //Getters
    public int getId() { return  id; }

    public String getContent() {
        return content;
    }

    public int getBelongsToNewsId() {
        return belongsToNewsId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Date getPostDate() {
        return postDate;
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBelongsToNewsId(int belongsToNewsId) {
        this.belongsToNewsId = belongsToNewsId;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        this.createdById = createdBy !=null ? createdBy.getId() : 0;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }


    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }
}
