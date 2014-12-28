package com.vladimir.dao;

import com.vladimir.model.Category;
import com.vladimir.util.DbUtil;
import com.vladimir.util.ToJSON;
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
     * This method will allow you to get all category data from the category table for REST request.
     * 
     * @return JSON object with all category data from the table. 
     */
    public JSONArray getAllCategoriesJSON() { 
        
        JSONArray categoryJsonArray = new JSONArray();
        
        String sql = "SELECT category_id, category_name FROM category;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            categoryJsonArray = converter.toJSONArray(rs);
            
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
        
        return categoryJsonArray;
    }
    
    /**
     * This method will allow you to get all category data from the category table.
     * 
     * @return Category list with all category data from the table. 
     */
    public List<Category> getCategoryList() { 
        
        List<Category> categoryList = new ArrayList<Category>();
        
        String sql = "SELECT category_id, category_name FROM category;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            // get Category data and add it to a category list
            while(rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                categoryList.add(new Category(categoryId, categoryName));
            }
                        
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
        
        return categoryList;
    }
}