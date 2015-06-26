package com.serendipity.dao;

import com.serendipity.model.Book;
import com.serendipity.util.DbUtil;
import com.serendipity.util.ToJSON;
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

    private final DbUtil db;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    public DAOBook() {
        
        db = new DbUtil();
        
    }
    
    public int addBook(Book book) {
        
        int bookId = 500;
        String title = book.getTitle();
        String author = book.getAuthor();
        int quantity = book.getQuantity();
        double price = book.getPrice();
        String description = book.getDescription();
        int categoryId = book.getCategoryId();
        
        ResultSet generatedKeys = null;
        String sql = "INSERT INTO book (title, author, quantity, price, description, category_id) "
                                                          + "VALUES(?,?,?,?,?,?);";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql, preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, description);
            preparedStatement.setInt(6, categoryId);
                        
            int affectedRows = preparedStatement.executeUpdate();
            
            if(affectedRows == 0) {
                throw new SQLException("Insert book failed, no rows affected");
            }
            
//            get generated id after an insert success
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                bookId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating book failed, no generated key obtained.");
            }
            
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
            } finally {
                if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIException) { }
            }
            return 500;
        } finally {
            db.closeConnection();
        }
        
        return bookId; //200;
    }
    
    public int updateBook(Book book) {
        
        String sql = "UPDATE book SET title=?, author=?, quantity=?, price=?, description=?, "
                                            + "category_id=? WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getQuantity());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setInt(6, book.getCategoryId());
            preparedStatement.setInt(7, book.getBookId());
            
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
        
        return 200;
    }
    
    public int updateQtyByBookId(int bookId, int bookQty) {
        
        String sql = "UPDATE book SET quantity=? WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookQty);
            preparedStatement.setInt(2, bookId);
            
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Coud not update book quantity.", ex);
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

    /**
     * Get book by book id.
     * @param bookId
     * @return 
     */
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
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("last_update");
                int categoryId = rs.getInt("category_id");
                book = new Book(bookId, title, author, quantity, price, 
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
    
    /**
     * 
     * @return 
     */
    public List<Book> getBookList(){
        
        List<Book> bookList = new ArrayList<Book>();
        String sql = "SELECT * FROM book;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("last_update");
                int categoryId = rs.getInt("category_id");
                Book book = new Book(bookId, title, author, quantity, price, 
                                        description, lastUpdate, categoryId);
                bookList.add(book);
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
        
        return bookList;
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
    
    public List<String> getBookListInfoByCategoryId(int categoryId) {
        
        List<String> bookListInfo = new ArrayList<String>();
        
        String sql = "SELECT title, author FROM book WHERE category_id=?;";
        
        try {
            
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            rs = preparedStatement.executeQuery();
            while( rs.next() ) {
                
                String title = rs.getString("title");
                String author = rs.getString("author");
                bookListInfo.add('"' + title + '"' + " by " + author);
                
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
        
        return bookListInfo;
    }
    
    public List<Book> getBookListByCategoryId(int categoryId) {
        
        List<Book> bookList = new ArrayList<Book>();
        
        String sql = "SELECT * FROM book WHERE category_id=?;";
        
        try {
            
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            rs = preparedStatement.executeQuery();
            while( rs.next() ) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                Date lastUpdate = rs.getDate("last_update");
                Book book = new Book(bookId, title, author, quantity, price, 
                                        description, lastUpdate, categoryId);
                bookList.add(book);
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
    
    /**
     * Get book list by book title
     * @param bookTitle
     * @return 
     */
    public JSONArray searchBookByTitle(String bookTitle){
        
        JSONArray bookJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM book WHERE title LIKE ?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "%" + bookTitle + "%");
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
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select book by book title.", ex);
        } catch (Exception ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeConnection();
        }
        
        return bookJsonArray;
    }
    
    /**
     * Get book list by book author
     * @param bookAuthor
     * @return 
     */
    public JSONArray searchBookByAuthor(String bookAuthor){
        
        JSONArray bookJsonArray = new JSONArray();
        
        String sql = "SELECT * FROM book WHERE author LIKE ?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "%" + bookAuthor + "%");
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
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select book by book author.", ex);
        } catch (Exception ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeConnection();
        }
        
        return bookJsonArray;
    }
    
    public List<String> getBookListByCustomerOrderId(int customerOrderId) {
        
        List<String> bookList = new ArrayList<String>();
        
        String sql = "SELECT book.book_id, ordered_book.customer_order_id, ordered_book.quantity, " + 
            "book.title, book.author, ordered_book.quantity " + 
            "FROM book LEFT JOIN ordered_book ON book.book_id = ordered_book.book_id " + 
            "LEFT JOIN customer_order ON " + 
            "ordered_book.customer_order_id = customer_order.customer_order_id " +
            "WHERE customer_order.customer_order_id = ?;";
        
//        SELECT book.book_id, ordered_book.customer_order_id, ordered_book.quantity, 
//        book.title, book.author, ordered_book.quantity FROM book LEFT JOIN ordered_book ON 
//        book.book_id = ordered_book.book_id LEFT JOIN customer_order ON 
//        ordered_book.customer_order_id = customer_order.customer_order_id WHERE 
//        customer_order.customer_order_id = ?
        
        try {
            
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, customerOrderId);
            rs = preparedStatement.executeQuery();
            while( rs.next() ) {
                
                String title = rs.getString("title");
                String author = rs.getString("author");
                String quantity = rs.getString("quantity");
                bookList.add('"' + title + '"' + " by " + author + "  [Qty: " + quantity + "]");
                
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch(SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select book by customer order id", ex);
        } finally {
            db.closeConnection();
        }
        
        return bookList;
    }
    
    /**
     * Get book quantity by book id
     * 
     * @param bookId
     * @return 
     */
    public int getBookQtyByBookId(int bookId){
        
        int qty = -1;
        
        String sql = "SELECT quantity FROM book WHERE book_id = ?;";
        
        try {
        
            conn = db.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            rs = preparedStatement.executeQuery();

            if(rs.next()) {
                qty = rs.getInt("quantity");
            }
            
            rs.close();
            rs = null;
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, "Could not select book by book author.", ex);
            return qty;
        } catch (Exception ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, null, ex);
            return qty;
        } finally {
            db.closeConnection();
        }
        
        return qty;
    }
    
    public int updateQtyByBookIdonClearShoppingCart(int bookId, int bookQty) {
        
        String sql = "UPDATE book SET quantity=quantity+? WHERE book_id=?;";
        
        try {
        
            conn = db.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookQty);
            preparedStatement.setInt(2, bookId);
            
            preparedStatement.executeUpdate();
            conn.commit();
            
            preparedStatement.close();
            preparedStatement = null;
            
            conn.close();
            conn = null;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOBook.class.getName()).log(Level.SEVERE, 
                    "Coud not update book quantity on shopping cart clear.", ex);
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
}