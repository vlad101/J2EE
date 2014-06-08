package com.vladimir.dao;

import com.vladimir.model.Category;
import com.vladimir.util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
        
        try {
            
            conn.setAutoCommit(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not set auto commit to false.", ex);
        }
        
    }
    
    public boolean addCategory(Category category) {
        
        boolean flag = false;
               
        String sql = "INSERT INTO category (category_name) VALUES(?);";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
            flag = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not add category.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex1);
            }
            flag = false;
        } finally {
            db.closeConnection();
        }
        
        return flag;
    }
    
    public boolean updateCategory(Category category) {
        
        boolean flag = false;
               
        String sql = "UPDATE category SET category_name=? WHERE category_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setInt(2, category.getCategoryId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
            flag = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not update category.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex1);
            }
            flag = false;
        } finally {
            db.closeConnection();
        }
        
        return flag;
    }
    
    public boolean deleteCategory(int categoryId) {
        
        boolean flag = false;
               
        String sql = "DELETE FROM category WHERE category_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
            flag = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Coud not delete category.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex1);
            }
            flag = false;
        } finally {
            db.closeConnection();
        }
        
        return flag;
    }

    public Category getCategoryById(int categoryId){
        
        Category category = null;
        
        String sql = "SELECT * FROM category WHERE category_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
            
                String categoryName = rs.getString("category_name");
                category = new Category(categoryId, categoryName);
                   
                // TODO: build a json object
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
    
    public List<Category> getCategories(){
    
        List<Category> categoryList = new ArrayList<Category>();
        
        String sql = "SELECT * FROM category;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
            
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                Category category = new Category(categoryId, categoryName);
                categoryList.add(category);
                   
                // TODO: build a json object
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, "Could not select categories.", ex);
        } finally {
            db.closeConnection();
        }
        
        return categoryList;
    }
    
}
