package com.newsman.newsman.new_rest.dtos;

import com.newsman.newsman.server_entities.UserWithPassword;

public class UserWithPasswordDTO {
    private int Id;
    private String Username;
    private String Password;

    public UserWithPasswordDTO(int id, String username, String password) {
        Id = id;
        Username = username;
        Password = password;
    }

    public UserWithPasswordDTO(UserWithPassword user) {
        Id = user.getId();
        Username = user.getUsername();
        Password = user.getPassword();
    }

    public static UserWithPassword getUserWithPassword(UserWithPasswordDTO user) {
        return new UserWithPassword(user.getId(), user.getUsername(), user.Password);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
