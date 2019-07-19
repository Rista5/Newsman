package com.newsman.newsman.ServerEntities;

public class UserWithPassword {

    private int id;
    private String username;
    private String password;

    public UserWithPassword() {}
    public UserWithPassword(String username, String password) {
        this.username = username;
        this.password = password;
        id = -1;
    }
    public UserWithPassword(int id, String username, String password) {
        this.username = username;
        this.password = password;
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
