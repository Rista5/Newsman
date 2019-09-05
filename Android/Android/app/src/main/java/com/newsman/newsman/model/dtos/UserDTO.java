package com.newsman.newsman.model.dtos;

import com.newsman.newsman.model.db_entities.User;

public class UserDTO {

    private int id;
    private String username;

    public UserDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
