package com.newsman.newsman.ServerEntities;

public class UserWithPassword {

    private String username;
    private String password;

    public UserWithPassword() {}
    public UserWithPassword(String username, String password) {
        this.username = username;
        this.password = password;
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
