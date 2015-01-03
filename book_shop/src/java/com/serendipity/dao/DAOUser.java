package com.serendipity.dao;

import com.serendipity.model.Category;
import com.serendipity.model.User;
import com.serendipity.util.DbUtil;
import com.serendipity.util.ToJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;

/**
 * Business logic for the user data - User table in the database.
 * 
 * @author Vladimir
 */
public class DAOUser {
    
    private DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOUser() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add user to the database.
     * 
     * @param userId
     * @param username
     * @param password
     * @param isAdmin
     * @return HTTP status
     */
    public int addUser(int userId, String username, String password, int isAdmin) {
                            
        String sql = "INSERT INTO category (user_id, username, password, admin) VALUES(?,?,?,?);";
        
        try {
            
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(3, isAdmin);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Coud not add user.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
            
        } finally {
            db.closeConnection();
        }
        return 200; //success
    }
    
    /**
     * This method will allow you to update user data.
     * 
     * @param user
     * @return HTTP status
     */
    public int updateUserInfo(User user) {
                
        String sql = "UPDATE user SET user_id=?, username=?, password=? WHERE user_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getUserId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Coud not update user info.", e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Coud not update user.", ex);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to delete data in the user table.
     * Consider storing data in the temporary table and not to delete completely.
     * 
     * @param userId
     * @return HTTP status
     */
    public int deleteUser(int userId) {
         
        String sql = "DELETE FROM user WHERE user_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Coud not delete user.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to get user id by username from the database.
     * 
     * @param username
     * @return user id
     */
    @SuppressWarnings("static-access")
    public int getUserIdByName(String username){
        
        int userId = -1;
        
        String sql = "SELECT user_id FROM user WHERE username=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
                userId = rs.getInt("user_id");
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Could not select category by ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return userId;
    }   

    /**
     * This method will allow you to get user data by id from the database.
     * 
     * @param userId
     * @return 
     */
    public User getUserById(int userId){
        
        User user = null;
        
        String sql = "SELECT username, password, admin FROM user WHERE user_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            rs = preparedStatement.executeQuery();
            
//            creates a user object
            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int isAdmin = rs.getInt("admin");
                user = new User(userId, username, password, isAdmin);
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Could not select user by user ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return user;
    }
    
    /**
     * This method will allow you to get user data by id from the database.
     * 
     * @param username
     * @return 
     */
    public boolean isUniqueUsername(String username){
        
        User user = null;
        
        String sql = "SELECT username, password, admin FROM user WHERE username=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            rs = preparedStatement.executeQuery();
            
//            if the result is returned, the username name is not unique
            if(rs.next()) {
                return false;
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Could not select user by user ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return true;
    }
    
    /**
     * This method will allow you to get all user data from the user table.
     * 
     * @return JSON object with all category data from the table. 
     */
    public JSONArray getAllUsersJSONREST() { 
        
        JSONArray userJsonArray = new JSONArray();
        
        String sql = "SELECT user_id, username, password FROM user;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            userJsonArray = converter.toJSONArray(rs);
                        
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Could not select users.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return userJsonArray;
    }
}