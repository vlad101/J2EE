package com.serendipity.model;

/**
 * User authentication credentials details.
 * @author vladimir
 */
public class User {
    
    private int userId;
    private String username;
    private String password;
    
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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
    
    @Override
    public String toString(){
        return "User{" + "userId=" + userId + ", username=" 
                + username + ", password=" + password + '}';
    }
}