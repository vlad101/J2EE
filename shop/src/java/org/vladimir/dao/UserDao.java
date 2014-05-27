package org.vladimir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vladimir.model.User;
import org.vladimir.util.DbUtil;

/**
 *
 * @author Vladimir
 */
public class UserDao {
    
    private final Connection connection;
    
    public UserDao() {
    
        connection = DbUtil.getConnection();
    
    }
    
    public void addUser(User user) {
        
        try {
        
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (firstname, lastname, email, dob) VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDate(4, new java.sql.Date(user.getDob().getTime()));
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void updateUser(User user) {
    
        try {
            
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET firstname=?, lastname=?, email=?, dob=? where userid=?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDate(4, new java.sql.Date(user.getDob().getTime()));
            preparedStatement.setInt(5, user.getUserId());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteUser(int userId) {
    
        try {
            
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE userid=?");
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        }   
    }
    
    public User getUserById(int userId) {
    
        User user = new User();
        
        try {
            
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE userid=?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                
                user.setUserId(rs.getInt("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setDob(rs.getDate("dob"));
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
        
    public List<User> getAllUsers() {
    
        List<User> users = new ArrayList<User>();
        
        try {
            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user");
        
            while(rs.next()) {
            
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setDob(rs.getDate("dob"));
                users.add(user);
            
            }
            
        } catch (SQLException e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return users;
    }
}