package com.serendipity.model;

/**
 *
 * @author vladimir
 */
public class Cart {
    
    private int bookId;
    private int clientId;
    private int quantity;
    
    public Cart(int bookId, int clientId, int quantity) {
        this.bookId = bookId;
        this.clientId = clientId;
        this.quantity = quantity;
    }
    
    public void setBookid (int bookId) {
        this.bookId = bookId;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public void setClientId (int clientId) {
        this.clientId = clientId;
    }
    
    public int getClientId() {
        return clientId;
    }

    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    @Override
    public String toString() {
        return "Cart{" + "bookId=" + bookId + ", clientId=" + clientId + 
                ", quantity=" + quantity + '}';
    }
}