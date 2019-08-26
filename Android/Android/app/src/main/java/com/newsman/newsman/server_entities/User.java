package com.newsman.newsman.server_entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Me on 1/9/2019.
 */
@Entity(tableName = "user")
public class User implements Serializable {

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
