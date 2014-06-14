package com.vladimir.dao;

import com.vladimir.model.Category;
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
public class DAOCategory {
    
    private DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOCategory() {
        
        db = new DbUtil();
        
    }
    
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
    
    public boolean deleteCategory(int categoryId) {
        
        boolean flag = false;
               
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
    
    public JSONArray getAllCategories() { 
        
        JSONArray json = new JSONArray();
        
        String sql = "SELECT category_id, category_name FROM category;"; // do not use * for production code
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            json = converter.toJSONArray(rs);
            
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
        
        return json;
    }
}