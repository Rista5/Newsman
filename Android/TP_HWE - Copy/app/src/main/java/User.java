/**
 * Created by Me on 1/9/2019.
 */

public class User {
    private String username;
    private int ID;

    public String getUsername() { return username; }
    public int getID() { return ID; }

    public User(int identification, String username) {
        this.ID = identification;
        this.username = username;
    }
}
