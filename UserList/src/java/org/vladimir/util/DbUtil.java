package org.vladimir.util;

//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
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
            
            Properties prop = new Properties();
//            InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("src\\db.properties");
//            Create db.properties file and save it directly under src folder
//            C:\Users\Vladimir\Documents\Local Projects\J2EE\shop\src\db.properties
//            prop.load(inputStream);
//            String driver = prop.getProperty("driver");
//            String url = prop.getProperty("url");
//            String user = prop.getProperty("user");
//            String password = prop.getProperty("password");
            
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/shop";
            String user = "root";
            String password = "cabbage";
            
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, e);
//        } catch (FileNotFoundException e) {
//            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, e);
//        } catch (IOException e) {
//            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return connection;
    }
}