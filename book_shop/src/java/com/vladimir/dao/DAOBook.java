package com.vladimir.dao;

import com.vladimir.model.Book;
import com.vladimir.util.DbUtil;
import com.vladimir.util.ToJSON;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Vladimir
 */
public class DAOBook {

    private DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOBook() {
        
        db = new DbUtil();
        
    }
    
    public int addBook(Book book) {
        
        String title = book.getTitle();
        String author = book.getAuthor();
        double price = book.getPrice();
        String description = book.getDescription();
        int categoryId = book.getCategoryId();
        
        String sql = "INSERT INTO book (title, author, price, description, category_id) "
                                                          + "VALUES(?,?,?,?,?);";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, categoryId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not add book.", ex);
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
    
    public int updateBook(Book book) {
        
        String sql = "UPDATE book SET title=?, author=?, price=?, description=?, "
                                            + "category_id=? WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setString(4, book.getDescription());
            preparedStatement.setInt(5, book.getCategoryId());
            preparedStatement.setInt(6, book.getBookId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not update book.", ex);
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
        
        return 500;
    }
    
    public int deleteBook(int bookId) {
               
        String sql = "DELETE FROM book WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not delete book.", ex);
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

    public Book getBookById(int bookId){
        
        Book book = null;
        
        String sql = "SELECT * FROM book WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
            
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("last_update");
                int categoryId = rs.getInt("category_id");
                book = new Book(bookId, title, author, price, 
                                        description, lastUpdate, categoryId);
                   
                // TODO: build a json object
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select book by ID.", ex);
        } finally {
            db.closeConnection();
        }
        
        return book;
    }    
    
    public JSONArray getAllBooks(){
        
        JSONArray bookJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM book;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            ToJSON converter = new ToJSON();
            bookJsonArray = converter.toJSONArray(rs);
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select books.", ex);
        } catch(Exception e) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not create a JSON Object.", e);
        } finally {
            db.closeConnection();
        }
        
        return bookJsonArray;
    }
    
    public List<String> getBookListByCategoryId(int category_id) {
        
        List<String> bookList = new ArrayList<String>();
        
        String sql = "SELECT title, author FROM book WHERE category_id=?;";
        
        try {
            
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, category_id);
            rs = preparedStatement.executeQuery();
            while( rs.next() ) {
                
                String title = rs.getString("title");
                String author = rs.getString("author");
                bookList.add('"' + title + '"' + " by " + author);
                
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch(SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select book by category id", ex);
        } finally {
            db.closeConnection();
        }
        
        return bookList;
    }
}