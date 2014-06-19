package com.vladimir.dao;

import com.vladimir.model.Category;
import com.vladimir.util.DbUtil;
import com.vladimir.util.ToJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Business logic for the category data - Category table in the database.
 * 
 * @author Vladimir
 */
public class DAOCategory {
    
    private DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOCategory() {
        
        db = new DbUtil();
        
    }
    
    /**
     * The method will allow you to add category to the database.
     * 
     * @param categoryName
     * @return HTTP status
     */
    public int addCategory(String categoryName) {
                
        if (categoryName == null || categoryName.length() == 0)
            return 500;
            
        String sql = "INSERT INTO category (category_name) VALUES(?);";
        
        try {
            
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not add category.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex1);
                return 500;
            }
            return 500;
            
        } finally {
            db.closeConnection();
        }
        return 200; //success
    }
    
    /**
     * This method will allow you to update category data.
     * 
     * @param category
     * @return HTTP status
     */
    public int updateCategory(Category category) {
        
        String categoryName = category.getCategoryName();
        if(categoryName == null || categoryName.length() == 0)
            return 500;
        
        String sql = "UPDATE category SET category_name=? WHERE category_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setInt(2, category.getCategoryId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException e) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not update category.", e);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not update category.", ex);
                return 500;
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return 200; // success
    }
    
    /**
     * This method will allow you to delete data in the category table.
     * Consider storing data in the temporary table and not to delete completely.
     * 
     * @param categoryId
     * @return HTTP status
     */
    public int deleteCategory(int categoryId) {
         
        String sql = "DELETE FROM category WHERE category_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not delete category.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex1);
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
     * @param categoryId
     * @return 
     */
    public JSONArray getCategoryById(int categoryId){
        
        JSONArray json = new JSONArray();
        
        String sql = "SELECT category_name FROM category WHERE category_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            rs = preparedStatement.executeQuery();

            ToJSON converter = new ToJSON();
            json = converter.toJSONArray(rs);
            
//            creates a category object  
//            method declaration: public Category getCategoryById(int categoryId){
//            while(rs.next()) {
//                String categoryName = rs.getString("category_name");
//                category = new Category(categoryId, categoryName);
//            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not select category by ID.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return json;
    }    
    
    /**
     * This method will allow you to get all category data from the category table.
     * 
     * @return JSON object with all category data from the table. 
     */
    public JSONArray getAllCategories() { 
        
        JSONArray jsonArray = new JSONArray();
        
        String sql = "SELECT category_id, category_name FROM category;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            jsonArray = converter.toJSONArray(rs);
            
            // get books belonging to category
            for(int i = 0; i <jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                DAOBook daoBook = new DAOBook();
                int categoryId = obj.getInt("category_id");
                List<String> bookList = daoBook.getBookListByCategoryId(categoryId);
                obj.put("book_list", bookList);
            }
            
//            creates a list of categories
//            method declaration:public List<Category> getCategories(){
//            while(rs.next()) {
//                int categoryId = rs.getInt("category_id");
//                String categoryName = rs.getString("category_name");
//                Category category = new Category(categoryId, categoryName);
//                categoryList.add(category);
//            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not select categories.", ex);
        } catch (Exception e) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not create a JSON object", e);  
        } finally {
            db.closeConnection();
        }
        
        return jsonArray;
    }
}