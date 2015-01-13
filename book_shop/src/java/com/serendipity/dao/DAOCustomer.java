package com.serendipity.dao;

import com.serendipity.model.Customer;
import com.serendipity.model.User;
import com.serendipity.util.DbUtil;
import com.serendipity.util.ToJSON;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;

/**
 * Business logic for the customer data - Customer table in the database.
 * 
 * @author Vladimir
 */
public class DAOCustomer {
    
    private final DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOCustomer() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add customer to the database.
     * 
     * @param customer
     * @param username
     * @param password
     * @param admin
     * @return HTTP status
     */
    public int addCustomer(Customer customer, String username, String password, int admin) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        ResultSet generatedKeys;
        int customerId = -1;
        
        String customerFirstName = customer.getFirstName();
        String customerLastName = customer.getLastName();
        String customerEmail = customer.getEmail();
        String customerPhone = customer.getPhone();
        String customerAddress = customer.getAddress();
        String customerCity = customer.getCity();
        String customerState = customer.getState();
        long customerZipcode = customer.getZipCode();
        long customerCcNumber = customer.getCcNumber();
        
        String sql = "INSERT INTO customer VALUES(null,?,?,?,?,?,?,?,?,?);";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
//            get the customer id of the inserted customer
            preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customerFirstName);
            preparedStatement.setString(2, customerLastName);
            preparedStatement.setString(3, customerEmail);
            preparedStatement.setString(4, customerPhone);
            preparedStatement.setString(5, customerAddress);
            preparedStatement.setString(6, customerCity);
            preparedStatement.setString(7, customerState);
            preparedStatement.setLong(8, customerZipcode);
            preparedStatement.setLong(9, customerCcNumber);
                        
            int affectedRows = preparedStatement.executeUpdate();
            
//            get the generated customer_id after insert success
            if(affectedRows == 0) {
                Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Coud not add customer.");
                return 500;
            }
            
            conn.commit();
            
//            get customer id of the added customer
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                customerId = generatedKeys.getInt(1);
            }
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Coud not add customer.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        }
        
        if(customerId != -1) {
            // create and add user to database
            DAOUser daoUser = new DAOUser();
            User user = new User(customerId, username, password, admin);
            int http = daoUser.addUser(user);
            if(http != 200) {
                return 500;
            }
        } else {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Coud not add customer.");
            return 500;
        }
        return 200;
    }
    
    /**
     * This method will allow you to update customer data.
     * 
     * @param customer
     * @return HTTP status
     */
    public int updateCustomer(Customer customer) {

        String sql = "UPDATE customer SET first_name=?,last_name=?,email=?,phone=?,address=?,city=?,state=?,zipcode=?,cc_number=? WHERE customer_id=?;";
        
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setString(7, customer.getState());
            preparedStatement.setLong(8, customer.getZipCode());
            preparedStatement.setLong(9, customer.getCcNumber());
            preparedStatement.setInt(10, customer.getCustomerId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException e) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not update customer.", e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not update customer.", ex);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to delete data in the customer table.
     * Consider storing data in the temporary table and not to delete completely.
     * 
     * @param customerId
     * @return HTTP status
     */
    public int deleteCustomer(int customerId) {
         
        String sql = "DELETE FROM customer WHERE customer_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Coud not delete customer.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to get all customer data from the customer table.
     * 
     * @return JSON object with all customer data from the table. 
     */
    public JSONArray getAllCustomers() { 
        
        JSONArray customerJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM customer;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            customerJsonArray = converter.toJSONArray(rs);
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Could not select customers.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return customerJsonArray;
    }
    
    /**
     * This method will allow you to get all customer data from the customer table.
     * 
     * @param customerId
     * @return JSON object with all customer data from the table. 
     */
    public Customer getCustomerById(int customerId){
        
        Customer customer = null;
        
        String sql = "SELECT * FROM customer WHERE customer_id=? LIMIT 1;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                
                String customerFirstName = rs.getString("first_name");
                String customerLastName = rs.getString("last_name");
                String customerEmail = rs.getString("email");
                String customerPhone = rs.getString("phone");
                String customerAddress = rs.getString("address");
                String customerCity = rs.getString("city");
                String customerState = rs.getString("state");
                long customerZipcode = rs.getLong("zipcode");
                long customerCcNumber = rs.getLong("cc_number");
                
                customer = new Customer(customerId,customerFirstName,
                    customerLastName,customerEmail,customerPhone,customerAddress,
                    customerCity,customerState,customerZipcode,customerCcNumber);
                   
                // TODO: build a json object
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Could not select customer by ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return customer;
    } 
}