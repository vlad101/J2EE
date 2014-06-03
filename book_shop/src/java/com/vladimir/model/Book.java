package com.vladimir.model;

import java.util.Date;

/**
 *
 * @author Vladimir
 */
public class Book {
    
    private int bookId;
    private String title;
    private double price;
    private String description;
    private Date lastUpdate;
    private int categoryId;
    
    public Book(int bookId, String title, double price, String description, Date lastUpdate, int categoryId){
        
        this.bookId = bookId;
        this.title = title;
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
}
