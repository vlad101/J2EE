package com.serendipity.dao;

import com.serendipity.model.User;
import com.serendipity.util.DbUtil;
import com.serendipity.util.ToJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import com.serendipity.util.PasswordUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Business logic for the user data - User table in the database.
 * 
 * @author Vladimir
 */
public class DAOUser {
    
    private final DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
//    password security
    
    
    public DAOUser() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add user to the database.
     * 
     * @param user
     * @return HTTP status
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public int addUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        // get secured password and salt
        PasswordUtil passwordUtil = new PasswordUtil();
        String[] passwordSecurity = passwordUtil.securePasswordOnCreateOrUpdate(user.getPassword());
        
        String sql = "INSERT INTO user (user_id, username, password, salt, admin) VALUES(?,?,?,?,?);";
        
        try {
            
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, passwordSecurity[0]);
            preparedStatement.setString(4, passwordSecurity[1]);
            preparedStatement.setInt(5, user.getIsAdmin());
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
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public int updateUserInfo(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
            
        // get secured password and salt
        PasswordUtil passwordUtil = new PasswordUtil();
        String[] passwordSecurity = passwordUtil.securePasswordOnCreateOrUpdate(user.getPassword());
        passwordUtil = new PasswordUtil();
        passwordUtil.authenticate();
        
        String sql = "UPDATE user SET user_id=?, username=?, password=?, salt=?, admin=? WHERE user_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, passwordSecurity[0]);
            preparedStatement.setString(4, passwordSecurity[1]);
            preparedStatement.setInt(5, user.getIsAdmin());
            preparedStatement.setInt(6, user.getUserId());
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
        
        String sql = "SELECT username, password, salt, admin FROM user WHERE user_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            rs = preparedStatement.executeQuery();
            
//            creates a user object
            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String salt = rs.getString("salt");
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
     * This method will allow you to check if username is unique.
     * 
     * @param username
     * @return 
     */
    public boolean isUniqueUsername(String username){
                
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
        return true; //unique username
    }
    
    /**
     * This method will allow you to get all user data from the user table.
     * 
     * @return JSON object with all category data from the table. 
     */
    public JSONArray getAllUsersJSONREST() { 
        
        JSONArray userJsonArray = new JSONArray();
        
        String sql = "SELECT user_id, username, password, salt, admin FROM user;"; // do not use * for production code
        
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