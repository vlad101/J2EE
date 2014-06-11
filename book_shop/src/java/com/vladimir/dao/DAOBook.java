package com.vladimir.dao;

import com.vladimir.model.Book;
import com.vladimir.util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public boolean addBook(Book book) {
        
        boolean flag = false;
               
        String sql = "INSERT INTO book (title, price, description, category_id) "
                                                          + "VALUES(?,?,?,?);";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setString(4, book.getDescription());
            preparedStatement.setDate(5, new java.sql.Date(book.getLastUpdate().getTime()));
            preparedStatement.setInt(6, book.getCategoryId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
            flag = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not add book.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex1);
            }
            flag = false;
        } finally {
            db.closeConnection();
        }
        
        return flag;
    }
    
    public boolean updateBook(Book book) {
        
        boolean flag = false;
               
        String sql = "UPDATE book SET title=?, price=?, description=?, "
                                            + "category_id=? WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setDouble(2, book.getPrice());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getCategoryId());
            preparedStatement.setInt(5, book.getBookId());
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
            flag = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not update book.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex1);
            }
            flag = false;
        } finally {
            db.closeConnection();
        }
        
        return flag;
    }
    
    public boolean deleteBook(int bookId) {
        
        boolean flag = false;
               
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
            
            flag = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not delete book.", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex1);
            }
            flag = false;
        } finally {
            db.closeConnection();
        }
        
        return flag;
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
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("last_update");
                int categoryId = rs.getInt("category_id");
                book = new Book(bookId, title, price, 
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
    
    public List<Book> getBooks(){
    
        List<Book> bookList = new ArrayList<Book>();
        
        String sql = "SELECT * FROM book;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
            
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("last_update");
                int categoryId = rs.getInt("category_id");
                Book book = new Book(bookId, title, price, 
                                        description, lastUpdate, categoryId);
                bookList.add(book);
                   
                // TODO: build a json object
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select books.", ex);
        } finally {
            db.closeConnection();
        }
        
        return bookList;
    }
}