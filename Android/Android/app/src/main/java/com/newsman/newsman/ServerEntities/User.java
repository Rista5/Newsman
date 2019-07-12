package com.newsman.newsman.ServerEntities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Me on 1/9/2019.
 */
@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() { return username; }
    public int getId() { return id; }

    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

}