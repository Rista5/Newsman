package com.newsman.newsman.auxiliary;

import com.newsman.newsman.server_entities.UserWithPassword;

public class LoginState {
    private LoginState(){}

    private static LoginState instance;

    private int USER_ID = -1;
    private UserWithPassword user;

    public static LoginState getInstance(){
        if(instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    public static void logout() {

    }

    public void setUser(int userId, UserWithPassword user) {
        USER_ID = userId;
        this.user = user;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public UserWithPassword getUser() {
        return user;
    }
}
