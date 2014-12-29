package com.vladimir.dao;

import com.vladimir.model.Image;
import com.vladimir.util.DbUtil;
import com.vladimir.util.ToJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Vladimir
 */
public class DAOImage {
    
    @SuppressWarnings("FieldMayBeFinal")
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
        
        String sql1 = "UPDATE image SET default_image=0 where book_id=?;";
        String sql2 = "INSERT INTO image VALUES(null,?,?,1);";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
            preparedStatement = conn.prepareStatement(sql1);
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
            
            preparedStatement = conn.prepareStatement(sql2);
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
     * @param image
     * @return HTTP status
     */
    public int deleteImage(Image image) {
         
        String sql = "DELETE FROM image WHERE path=? and book_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, image.getPath());
            preparedStatement.setInt(2, image.getBookId());
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

    /**
     * This method will allow you to get category data by id from the database.
     * 
     * @param bookId
     * @return 
     */
    public List<String> getImageByBookId(int bookId){
        
        Image image;
        List<String> imagePathList = new LinkedList<String>();
        
        String sql = "SELECT * FROM image WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            rs = preparedStatement.executeQuery();
            
//            creates a category object
            while(rs.next()) {
                int imageId = rs.getInt("image_id");
                String imagePath = rs.getString("path");
                int imageBookId = rs.getInt("book_id");
                int imageDefault = rs.getInt("default_image");
                image = new Image(imageId, imagePath, imageBookId);
                
//                if image is default, add 1_ flag
                if(imageDefault == 1)
                    imagePathList.add("1_" + image.getPath());
                else
                    imagePathList.add(image.getPath());
            }
            
//                if there is only one image, make it default
            if(imagePathList.size() == 1 && !imagePathList.get(0).contains("1_")) {
                imagePathList.set(0, "1_" + imagePathList.get(0));                  
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Could not select image by book id.", ex);
        } finally {
            db.closeConnection();
        }
        
        return imagePathList;
    }
    
    /**
     * This method will allow you to get category data by id from the database.
     * 
     * @param bookId
     * @return 
     */
    public Image getDefaultImageByBookId(int bookId){
        
        Image image = null;
        
        String sql = "SELECT * FROM image WHERE book_id=? AND default_image=1;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            rs = preparedStatement.executeQuery();
            
//            get the first default image
            if(rs.next()) {
                int imageId = rs.getInt("image_id");
                String imagePath = rs.getString("path");
                int imageBookId = rs.getInt("book_id");
                image = new Image(imageId, imagePath, imageBookId);
            }
            
//            close connections
            rs.close();
            rs = null;
            preparedStatement.close();
            preparedStatement = null;
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImage.class.getName()).log(Level.SEVERE, "Could not select image by book id.", ex);
        } finally {
            db.closeConnection();
        }
        if(image != null) {
            return image;
        }
        return null; 
    }
    
    /**
     * This method will allow you to update book image data.
     * 
     * @param bookId
     * @param defaultImage
     * @return HTTP status
     */
    public int setDefaultImage(String defaultImage, int bookId) {

        String sql1 = "UPDATE image SET default_image=0 where book_id=?;";
        String sql2 = "UPDATE image SET default_image=1 WHERE path LIKE ? AND book_id=?;";
        
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
            preparedStatement = conn.prepareStatement(sql1);
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();            
            
            preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setString(1, "%" + defaultImage + "%");
            preparedStatement.setInt(2, bookId);
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
