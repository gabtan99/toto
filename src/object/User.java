package object;

import java.io.File;

public class User {

    private int user_id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private boolean is_artist;
    private File avatarURL;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean isIs_artist() {
        return is_artist;
    }

    public void setIs_artist(boolean is_artist) {
        this.is_artist = is_artist;
    }

    public File getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(File avatarURL) {
        this.avatarURL = avatarURL;
    }
}

