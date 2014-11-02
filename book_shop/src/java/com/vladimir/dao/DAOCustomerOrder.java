package com.vladimir.dao;

import com.vladimir.model.Category;
import com.vladimir.model.Customer;
import com.vladimir.model.CustomerOrder;
import com.vladimir.util.DbUtil;
import com.vladimir.util.ToJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Vladimir
 */
public class DAOCustomerOrder {
    
    private DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOCustomerOrder() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add customer to the database.
     * 
     * @param categoryName
     * @return HTTP status
     */
    public int addCustomer(Customer customer) {
        
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
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, customerFirstName);
            preparedStatement.setString(2, customerLastName);
            preparedStatement.setString(3, customerEmail);
            preparedStatement.setString(4, customerPhone);
            preparedStatement.setString(5, customerAddress);
            preparedStatement.setString(6, customerCity);
            preparedStatement.setString(7, customerState);
            preparedStatement.setLong(8, customerZipcode);
            preparedStatement.setLong(9, customerCcNumber);
                        
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not add customer.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200;
    }
    
    /**
     * This method will allow you to update customer order data.
     * 
     * @param customerOrder
     * @return HTTP status
     */
    public int updateCustomerOrder(CustomerOrder customerOrder) {

        String sql = "UPDATE customer_order SET amount=?,confirmation_number=? WHERE customer_order_id=?;";
        
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, customerOrder.getAmount());
            preparedStatement.setLong(2, customerOrder.getConfirmationNumber());
            preparedStatement.setInt(3, customerOrder.getCustomerOrderId());
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
     * This method will allow you to delete data in the customer_order table.
     * Consider storing data in the temporary table and not to delete completely.
     * 
     * @param customerOrderId
     * @return HTTP status
     */
    public int deleteCustomerOrder(int customerOrderId) {
         
        String sql = "DELETE FROM customer_order WHERE customer_order_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, customerOrderId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, "Coud not delete customer order.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCustomerOrder.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to get category id by name from the database.
     * 
     * @param categoryName
     * @return 
     */
    @SuppressWarnings("static-access")
    public int getCategoryIdByName(String categoryName){
        
        int categoryId = -1;
        
        String sql = "SELECT category_id FROM category WHERE category_name=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, categoryName);
            rs = preparedStatement.executeQuery();
            
            // read the first row - replaced while with if
            if(rs.next()) {
                categoryId = rs.getInt("category_id");
            }
            else {
//                 category does not exist, add a new category
                ResultSet generatedKeys = null;
                String sql2 = "INSERT INTO category (category_name) VALUES(?);";

                try {
                    conn.setAutoCommit(false);
                    preparedStatement = conn.prepareStatement(sql2, preparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, categoryName);
                    int affectedRows = preparedStatement.executeUpdate();
                    
                    if (affectedRows == 0) {
                        throw new SQLException("Insert category failed, no rows affected.");
                    }
                    
//                    get the generated id after an insert success
                    generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        categoryId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating category failed, no generated key obtained.");
                    }
                    conn.commit();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not add category.", ex);
                    try {
                        conn.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex1);
                    } finally {
                        if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
                    }
                } 
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not select category by ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return categoryId;
    }   

    /**
     * This method will allow you to get category data by id from the database.
     * 
     * @param categoryId
     * @return 
     */
    public Category getCategoryById(int categoryId){
        
        Category category = null;
        
        String sql = "SELECT category_name FROM category WHERE category_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            rs = preparedStatement.executeQuery();
            
//            creates a category object
            while(rs.next()) {
                String categoryName = rs.getString("category_name");
                category = new Category(categoryId, categoryName);
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not select category by ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return category;
    }    
    
    /**
     * This method will allow you to get all customer order data from the customer table.
     * 
     * @return JSON object with all customer order data from the table. 
     */
    public JSONArray getAllCustomerOrders() { 
        
        JSONArray customerOrderJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM customer_order;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            customerOrderJsonArray = converter.toJSONArray(rs);
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomerOrder.class.getName()).log(Level.SEVERE, "Could not select customer orders.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOCustomerOrder.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return customerOrderJsonArray;
    }
}
