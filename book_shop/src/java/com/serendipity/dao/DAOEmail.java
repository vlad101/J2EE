package com.serendipity.dao;

import com.serendipity.model.Email;
import com.serendipity.util.DbUtil;
import com.serendipity.util.ToJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;

/**
 * Business logic for the email list data - Email list table in the database.
 * 
 * @author Vladimir
 */
public class DAOEmail {
    
    private final DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOEmail() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add user email to the database.
     * 
     * @param email
     * @return HTTP status
     */
    public int addEmail(Email email) {
                            
        String sql = "INSERT INTO email_list (email_list_id, first_name, last_name, email) VALUES(null,?,?,?);";

        try {
            
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email.getFirstName());
            preparedStatement.setString(2, email.getLastName());
            preparedStatement.setString(3, email.getEmail());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Coud not add email entry.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
            
        } finally {
            db.closeConnection();
        }
        return 200; //success
    }
    
    /**
     * This method will allow you to update user email.
     * 
     * @param email
     * @return HTTP status
     */
    public int updateEmailInfo(Email email) {
                
        String sql = "UPDATE email_list SET email_list_id=?, first_name=?, last_name=?,email=? WHERE email_list_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, email.getEmailListId());
            preparedStatement.setString(2, email.getFirstName());
            preparedStatement.setString(3, email.getLastName());
            preparedStatement.setString(4, email.getEmail());
            preparedStatement.setInt(5, email.getEmailListId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException e) {
            Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Coud not update email ist info.", e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Coud not update email.", ex);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to delete data in the email list table.
     * Consider storing data in the temporary table and not to delete completely.
     * 
     * @param emailId
     * @return HTTP status
     */
    public int deleteEmailList(int emailId) {
         
        String sql = "DELETE FROM email_list WHERE email_list_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, emailId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Coud not delete email.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, null, ex1);
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
     * @param email
     * @return user id
     */
    @SuppressWarnings("static-access")
    public int getEmailListIdByEmail(String email){
        
        int emailId = -1;
        
        String sql = "SELECT email_list_id FROM email_list WHERE email=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
                emailId = rs.getInt("email_list_id");
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Could not select email by ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return emailId;
    }   

    /**
     * This method will allow you to get user email list data by id from the database.
     * 
     * @param emailId
     * @return 
     */
    public Email getEmailById(int emailId){
        
        Email emailEntry = null;
        
        String sql = "SELECT email_list_id, first_name, last_name, email FROM email_list WHERE email_list_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,emailId);
            rs = preparedStatement.executeQuery();
            
//            creates a user object
            while(rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                emailEntry = new Email(emailId, firstName, lastName, email);
            }
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Could not select email by email_list_id.", ex);
        } finally {
            db.closeConnection();
        }
        
        return emailEntry;
    }
    
    /**
     * This method will allow you to check if email is unique.
     * 
     * @param email
     * @return 
     */
    public boolean isUniqueEmail(String email){
                
        String sql = "SELECT * FROM email_list WHERE email=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            
//            if the result is returned, the email is not unique
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
            Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Could not select email by email.", ex);
        } finally {
            db.closeConnection();
        }
        return true; //unique username
    }
    
    /**
     * This method will allow you to get all user email data from the email list table.
     * 
     * @return JSON object with all email data from the table. 
     */
    public JSONArray getEmailListJSONREST() { 
        
        JSONArray emailListJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM email_list;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            emailListJsonArray = converter.toJSONArray(rs);
                        
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Could not select users.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOEmail.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return emailListJsonArray;
    }
}