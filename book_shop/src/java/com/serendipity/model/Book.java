package com.serendipity.model;

import java.util.Date;

/**
 *
 * @author Vladimir
 */
public class Book {
    
    private int bookId;
    private String title;
    private String author;
    private int quantity;
    private double price;
    private String description;
    private Date lastUpdate;
    private int categoryId;
    
    public Book(int bookId, String title, String author, int quantity, double price, String description, Date lastUpdate, int categoryId){
        
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.lastUpdate = lastUpdate;
        this.categoryId = categoryId; 
    
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Book{" + "bookId=" + bookId + ", title=" + title + 
                ", author=" + author + ", price=" + price + 
                ", description=" + description + ", lastUpdate=" + lastUpdate + 
                ", categoryId=" + categoryId + '}';
    }
}
