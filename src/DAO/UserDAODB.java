package DAO;

import object.User;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAODB implements UserDAO{
    Connection connection = dbConnection.getInstance();

    @Override
    public boolean addUser(User user) {
        String usernameTemp = user.getUsername();
        String passwordTemp = user.getPassword();
        String firstNameTemp = user.getFirst_name();
        String lastNameTemp = user.getLast_name();
        int isArtistTemp = (user.isIs_artist()) ? 1:0;
        FileInputStream avatarStream = null;
        if(user.getAvatarURL()!=null) {
            try {
                avatarStream = new FileInputStream(user.getAvatarURL());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String query = "INSERT INTO user VALUES(NULL,?,?,?,?,?,?)";

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstNameTemp);
            statement.setString(2, lastNameTemp);
            statement.setString(3, usernameTemp);
            statement.setString(4, passwordTemp);
            statement.setInt(5, isArtistTemp);
            statement.setBinaryStream(6, avatarStream);

            statement.executeUpdate();
            statement.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int user_id) {
        String query = "DELETE FROM user WHERE user.user_id = " + user_id;

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        int userID = user.getUser_id();
        String firstNameTemp = user.getFirst_name();
        String lastNameTemp = user.getLast_name();
        String usernameTemp = user.getUsername();
        String passwordTemp = user.getPassword();
        int isArtistTemp = (user.isIs_artist()) ? 1:0;
        FileInputStream avatarStream = null;
        if(user.getAvatarURL()!=null) {
            try {
                avatarStream = new FileInputStream(user.getAvatarURL());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String query = "UPDATE user SET " +
                "user.first_name = ?, " +
                "user.last_name = ?, " +
                "user.username = ?, " +
                "user.password = ?, " +
                "user.is_artist = ?, " +
                "user.avatar = ? WHERE user.user_id = " + userID;

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstNameTemp);
            statement.setString(2, lastNameTemp);
            statement.setString(3, usernameTemp);
            statement.setString(4, passwordTemp);
            statement.setInt(5, isArtistTemp);
            statement.setBinaryStream(6, avatarStream);

            statement.executeUpdate();
            statement.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(String username, String password) {
        User user = new User();
        String query = "SELECT * FROM user";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                String dbUsername = rs.getString("user.username");
                String dbPassword = rs.getString("user.password");
                if(dbUsername.equals(username) && dbPassword.equals(password)) {
                    user = toUser(rs);
                    statement.close();
                    rs.close();
                    return user;
                }
            }
            statement.close();
            rs.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getProfile(int user_id){
        User user = new User();
        String query = "SELECT * FROM user WHERE user.user_id = " +user_id;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                user = toUser(rs);
                statement.close();
                rs.close();
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkUsername(String username) {
        String query = 	"SELECT user.username FROM user";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                String usernameTemp = rs.getString("user.username");
                if(usernameTemp.equals(username)){
                    statement.close();
                    rs.close();
                    return true;
                }
            }
            statement.close();
            rs.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getFollowers(int user_id) {
        String query = "SELECT user.user_id, user.username, user.first_name, user.last_name, user.password, user.is_artist, user.avatar FROM user INNER JOIN follower_mapping\n" +
                "ON user.user_id = follower_mapping.follower_id\n" +
                "WHERE follower_mapping.user_id = " + user_id;

        List<User> userList = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs= statement.executeQuery();
            while(rs.next()){
                userList.add(toUser(rs));
            }
            statement.close();
            rs.close();
            return userList;
        }catch (SQLException e){
            e.printStackTrace();
            return userList;
        } catch (IOException e) {
            e.printStackTrace();
            return userList;
        }

    }

    @Override
    public List<User> getFollowedListeners(int user_id) {
        String query = "SELECT * FROM user INNER JOIN follower_mapping \n" +
                "ON user.user_id = follower_mapping.user_id\n" +
                "WHERE follower_mapping.follower_id = ? AND user.is_artist = 0";
        List<User> userList = new ArrayList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                userList.add(toUser(rs));
            }
            statement.close();
            rs.close();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<User> getFollowedArtists(int user_id) {
        String query = "SELECT * FROM user INNER JOIN follower_mapping \n" +
                "ON user.user_id = follower_mapping.user_id\n" +
                "WHERE follower_mapping.follower_id = ? AND user.is_artist = 1";
        List<User> userList = new ArrayList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                userList.add(toUser(rs));
            }
            statement.close();
            rs.close();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean followerUser(int user_id, int follower_id) {
        String query = "INSERT INTO follower_mapping VALUES(?,?)";

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, follower_id);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unfollowerUser(int user_id, int follower_id) {
        String query = "DELETE FROM follower_mapping WHERE follower_mapping.user_id = ? AND follower_mapping.follower_id = ?";

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, follower_id);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkIfFollowed(int user_id, int follower_id) {
        String query = "SELECT * FROM follower_mapping WHERE follower_mapping.user_id = ? AND follower_mapping.follower_id = ?";

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, follower_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                rs.close();
                statement.close();
                return true;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> searchArtists(String keyword, int user_id) {
        String query = "SELECT * FROM user WHERE user.user_id != ? AND user.is_artist = 1 AND CONCAT(user.first_name, ' ', user.last_name) LIKE ?";
        List<User> userList = new ArrayList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setString(2, "%"+keyword+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                userList.add(toUser(rs));
            }
            statement.close();
            rs.close();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<User> searchListeners(String keyword, int user_id) {
        String query = "SELECT * FROM user WHERE user.user_id != ? AND user.is_artist = 0 AND CONCAT(user.first_name, ' ', user.last_name) LIKE ?";
        List<User> userList = new ArrayList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setString(2, "%"+keyword+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                userList.add(toUser(rs));
            }
            statement.close();
            rs.close();
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private User toUser(ResultSet rs) throws SQLException, IOException {
        User user = new User();

        user.setUsername(rs.getString("user.username"));
        user.setUser_id(rs.getInt("user.user_id"));
        user.setPassword(rs.getString("user.password"));
        user.setFirst_name(rs.getString("user.first_name"));
        user.setLast_name(rs.getString("user.last_name"));
        user.setIs_artist((rs.getInt("user.is_artist")!=0));
        user.setAvatarURL(toFile(rs));
        return user;
    }

    private File toFile(ResultSet rs) throws SQLException, IOException {
        if(rs.getBinaryStream("user.avatar")!=null) {
            File file = new File(System.getProperty("user.home") + "/documents/Beatify/PictureCache/" + rs.getString("user.user_id") + "_avatar.png");
            OutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = rs.getBinaryStream("user.avatar");
            byte[] buffer = new byte[4096];
            while (inputStream.read(buffer) > 0) {
                outputStream.write(buffer);
            }
            return file;
        }else{
            return null;
        }
    }
}
