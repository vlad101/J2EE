package com.vladimir.dao;

import com.vladimir.model.Category;
import com.vladimir.model.Customer;
import com.vladimir.model.CustomerOrder;
import com.vladimir.model.Image;
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
public class DAOImage {
    
    private DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOImage() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add book image path to the database.
     * 
     * @param image
     * @return HTTP status
     */
    public int addImage(Image image) {
        
        String imagePath = image.getPath();
        int bookId = image.getBookId();
        
        String sql = "INSERT INTO image VALUES(null,?,?);";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, imagePath);
            preparedStatement.setInt(2, bookId);
                        
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Coud not add image.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200;
    }
    
    /**
     * This method will allow you to update book image data.
     * 
     * @param image
     * @return HTTP status
     */
    public int updateImage(Image image) {

        String sql = "UPDATE image SET book_id=?,path=? WHERE image_id=?;";
        
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, image.getBookId());
            preparedStatement.setString(2, image.getPath());
            preparedStatement.setInt(3, image.getImageId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException e) {
            Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Could not update image.", e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Coud not update image.", ex);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to delete data in the book image table.
     * Consider storing data in the temporary table and not to delete completely.
     * 
     * @param imageId
     * @return HTTP status
     */
    public int deleteCustomerOrder(int imageId) {
         
        String sql = "DELETE FROM image WHERE image_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, imageId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Coud not delete image.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
//    /**
//     * This method will allow you to get category id by name from the database.
//     * 
//     * @param categoryId
//     * @return 
//     */
//    @SuppressWarnings("static-access")
//    public int getCategoryIdByName(String categoryName){
//        
//        int categoryId = -1;
//        
//        String sql = "SELECT category_id FROM category WHERE category_name=?;";
//        
//        try {
//        
//            conn = db.getConnection();
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, categoryName);
//            rs = preparedStatement.executeQuery();
//            
//            // read the first row - replaced while with if
//            if(rs.next()) {
//                categoryId = rs.getInt("category_id");
//            }
//            else {
////                 category does not exist, add a new category
//                ResultSet generatedKeys = null;
//                String sql2 = "INSERT INTO category (category_name) VALUES(?);";
//
//                try {
//                    conn.setAutoCommit(false);
//                    preparedStatement = conn.prepareStatement(sql2, preparedStatement.RETURN_GENERATED_KEYS);
//                    preparedStatement.setString(1, categoryName);
//                    int affectedRows = preparedStatement.executeUpdate();
//                    
//                    if (affectedRows == 0) {
//                        throw new SQLException("Insert category failed, no rows affected.");
//                    }
//                    
////                    get the generated id after an insert success
//                    generatedKeys = preparedStatement.getGeneratedKeys();
//                    if (generatedKeys.next()) {
//                        categoryId = generatedKeys.getInt(1);
//                    } else {
//                        throw new SQLException("Creating category failed, no generated key obtained.");
//                    }
//                    conn.commit();
//                } catch (SQLException ex) {
//                    Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not add category.", ex);
//                    try {
//                        conn.rollback();
//                    } catch (SQLException ex1) {
//                        Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex1);
//                    } finally {
//                        if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
//                    }
//                } 
//            }
//            
//            rs.close();
//            rs = null;
//            
//            preparedStatement.close();
//            preparedStatement = null;
//            
//            conn.close();
//            conn = null;
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not select category by ID.", ex);
//        } finally {
//            db.closeConnection();
//        }
//        
//        return categoryId;
//    }   

//    /**
//     * This method will allow you to get category data by id from the database.
//     * 
//     * @param categoryId
//     * @return 
//     */
//    public Category getCategoryById(int categoryId){
//        
//        Category category = null;
//        
//        String sql = "SELECT category_name FROM category WHERE category_id=?;";
//        
//        try {
//        
//            conn = db.getConnection();
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setInt(1, categoryId);
//            rs = preparedStatement.executeQuery();
//            
////            creates a category object
//            while(rs.next()) {
//                String categoryName = rs.getString("category_name");
//                category = new Category(categoryId, categoryName);
//            }
//            
//            rs.close();
//            rs = null;
//            
//            preparedStatement.close();
//            preparedStatement = null;
//            
//            conn.close();
//            conn = null;
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not select category by ID.", ex);
//        } finally {
//            db.closeConnection();
//        }
//        
//        return category;
//    }    
    
    /**
     * This method will allow you to get all image data from image table.
     * 
     * @return JSON object with all book image data from the table. 
     */
    public JSONArray getAllImages() { 
        
        JSONArray imageJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM image;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            imageJsonArray = converter.toJSONArray(rs);
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Could not select book images.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return imageJsonArray;
    }
}
