package com.serendipity.dao;

import com.serendipity.model.ShoppingCart;
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
 * Business logic for the shopping cart data - Shopping cart table in the database.
 * 
 * @author Vladimir
 */
public class DAOShoppingCart {
    
    private final DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOShoppingCart() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add shopping cart data to the database.
     * 
     * @param shoppingCart
     * @return HTTP status
     */
    public int addShoppingCart(ShoppingCart shoppingCart) {
                            
        String sql = "INSERT INTO shopping_cart (shopping_cart_id, book_id, quantity, customer_id) VALUES(null,?,?,?);";

        try {
            
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, shoppingCart.getBookId());
            preparedStatement.setInt(2, shoppingCart.getQuantity());
            preparedStatement.setInt(3, shoppingCart.getCustomerId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, "Coud not add shopping cart entry.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
            
        } finally {
            db.closeConnection();
        }
        return 200; //success
    }
    
    /**
     * This method will allow you to update shopping cart.
     * 
     * @param shoppingCart
     * @return HTTP status
     */
    public int updateShoppingCartInfo(ShoppingCart shoppingCart) {
                
        String sql = "UPDATE shopping_cart SET book_id=?, quantity=?,customer_id=? WHERE shoppoing_cart_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, shoppingCart.getBookId());
            preparedStatement.setInt(2, shoppingCart.getQuantity());
            preparedStatement.setInt(3, shoppingCart.getCustomerId());
            preparedStatement.setInt(4, shoppingCart.getShoppingCartId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException e) {
            Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, "Coud not update shopping cart info.", e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, "Coud not update shopping cart.", ex);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to delete data in the shopping cart table.
     * Consider storing data in the temporary table and not to delete completely.
     * 
     * @param customerId
     * @return HTTP status
     */
    public int deleteShoppingCartByCustomerId(int customerId) {
         
        String sql = "DELETE FROM shopping_cart WHERE customer_id=?;";
        
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
            Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, "Coud not delete shoppoing cart books.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to get shopping cart books by customer id from the database.
     * 
     * @param customerId
     * @return user id
     */
    public List<ShoppingCart> getShoppingCartByCustomerId(int customerId){
        
        List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
        
        String sql = "SELECT shopping_cart_id, book_id, quantity, customer_id FROM shopping_cart WHERE customer_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,customerId);
            rs = preparedStatement.executeQuery();
            
//            creates a user object
            while(rs.next()) {
                int shoppingCartId = rs.getInt("shopping_cart_id");
                int bookId = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
                ShoppingCart shoppingCartEntry = new ShoppingCart(shoppingCartId, bookId, quantity, customerId);
                shoppingCartList.add(shoppingCartEntry);
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, "Could not select shopping cart by customer id.", ex);
        } finally {
            db.closeConnection();
        }
        
        return shoppingCartList;
    }
    
    /**
     * This method will allow you to get all user shopping cart data from the email list table.
     * 
     * @return JSON object with all email data from the table. 
     */
    public JSONArray getShoppingCartListJSONREST() { 
        
        JSONArray shoppingCartListJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM shopping_cart;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            shoppingCartListJsonArray = converter.toJSONArray(rs);
                        
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, "Could not select shopping cart data.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOShoppingCart.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return shoppingCartListJsonArray;
    }
}