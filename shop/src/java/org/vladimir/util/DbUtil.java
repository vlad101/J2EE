package org.vladimir.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vladimir
 */
public class DbUtil {
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
        if(connection != null)
            return connection;
        
        try {
        
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/shop";
            String user = "root";
            String password = "cabbage";
            
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            
        } catch (ClassNotFoundException e) {
            
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, e);
            
        }
        
        return connection;
    }
}