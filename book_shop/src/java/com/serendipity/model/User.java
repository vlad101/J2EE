package com.serendipity.model;

/**
 * User authentication credentials details.
 * @author vladimir
 */
public class User {
    
    private int userId;
    private String username;
    private String password;
    private String salt;
    private int isAdmin;
    
    public User(int userId, String username, String password, int isAdmin, String salt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.salt = salt;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUsername (String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setPassword (String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public int getIsAdmin() {
        return isAdmin;
    }
    
    @Override
    public String toString(){
        return "User{" + "userId=" + userId + ", username=" 
                + username + ", password=" + password + ", salt=" 
                    + salt + ", isAdmin=" + isAdmin + '}';
    }
}