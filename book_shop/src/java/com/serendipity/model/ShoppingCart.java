package com.serendipity.model;

/**
 * Shopping cart database model.
 * 
 * @author vladimir
 */
public class ShoppingCart {
    
    private int shoppingCartId;
    private int bookId;
    private int quantity;
    private int customerId;
    
    public ShoppingCart(int shoppingCartId, int bookId, 
                            int quantity, int customerId) {
        this.shoppingCartId = shoppingCartId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.customerId = customerId;
    }
    
    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
    
    public int getShoppingCartId() {
        return shoppingCartId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }   

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public int getCustomerId() {
        return customerId;
    }       
    
    @Override
    public String toString(){
        return "ShoppingCart{" + "shoppingCartId=" + shoppingCartId + ", bookId=" 
                + bookId + ", quantity=" + quantity + ", customerId=" + customerId + '}';
    }
}