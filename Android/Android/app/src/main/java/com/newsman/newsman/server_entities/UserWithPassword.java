package com.newsman.newsman.server_entities;

import android.os.Parcel;
import android.os.Parcelable;

public class UserWithPassword implements Parcelable {

    private int id;
    private String username;
    private String password;

    public UserWithPassword() {}
    public UserWithPassword(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    protected UserWithPassword(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
    }

    public static final Creator<UserWithPassword> CREATOR = new Creator<UserWithPassword>() {
        @Override
        public UserWithPassword createFromParcel(Parcel in) {
            return new UserWithPassword(in);
        }

        @Override
        public UserWithPassword[] newArray(int size) {
            return new UserWithPassword[size];
        }
    };

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(password);
    }
}
