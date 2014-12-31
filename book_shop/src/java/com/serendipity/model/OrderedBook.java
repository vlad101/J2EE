package com.serendipity.model;

/**
 *
 * @author Vladimir
 */
public class OrderedBook {
    
    private int bookId;
    private int customerOrderId;
    private int quantity;

    public OrderedBook(int bookId, int customerOrderId, int quantity) {
        this.bookId = bookId;
        this.customerOrderId = customerOrderId;
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderedBook{" + "bookId=" + bookId + ", customerOrderId=" 
                + customerOrderId + ", quantity=" + quantity + '}';
    }
}