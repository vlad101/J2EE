package com.vladimir.util;

import com.vladimir.rest.serendipity.Status;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vladimir
 */
public class DbUtil {
    
    private Connection connection = null;
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/serendipity";
    private final String USER = "root";
    private final String PASSWORD = "cabbage";
     
    public DbUtil() {
                
        try {
            
            Class.forName(DRIVER);
                        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        try {
            
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (SQLException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        
        return connection;
        
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                
                connection.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public String getDatabaseStatus() throws Exception {
    
        PreparedStatement preparedStatement;
        String datetime;
        String returnDateTime = null;
        ResultSet rs;
        
        if (connection != null) {
            try {

                preparedStatement = connection.prepareStatement("SELECT CURDATE(), CURTIME();");
                rs = preparedStatement.executeQuery();

                while(rs.next()) {

                    datetime = rs.getString("CURDATE()") + " " + rs.getString("CURTIME()");
                    returnDateTime = "<p>Database status</p>" 
                                    + "<p>Database Date/Time: " + datetime + " </p>";

                }

                rs.close();
                rs = null;

                preparedStatement.close();
                preparedStatement = null;

            } catch (SQLException ex) {
                Logger.getLogger(Status.class.getName()).log(Level.SEVERE, "Could not get database time.", ex);
            }
        }
        
        return returnDateTime;
    }
}