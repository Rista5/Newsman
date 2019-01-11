package Entities;

/**
 * Created by Me on 1/9/2019.
 */

public class User {
    private String username;
    private int ID;

    public String getUsername() { return username; }
    public int getID() { return ID; }

    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public User(int identification, String username) {
        this.ID = identification;
        this.username = username;
    }
}
