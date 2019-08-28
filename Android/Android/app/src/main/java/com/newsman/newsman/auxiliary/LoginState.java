package com.newsman.newsman.auxiliary;

import com.newsman.newsman.server_entities.UserWithPassword;

public class LoginState {
    private LoginState(){}

    private static LoginState instance;

    private UserWithPassword user;

    public static LoginState getInstance(){
        if(instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    public static void logout() {

    }

    public void setUser(UserWithPassword user) {
        this.user = user;
    }

    public int getUserId() {
        if(user != null)
            return user.getId();
        else return Constant.INVALID_USER_ID;
    }

    public UserWithPassword getUser() {
        return user;
    }
}
