package com.vladimir.model;

import java.util.Date;

/**
 *
 * @author Vladimir
 */
public class CustomerOrder {
    
    private int customerOrderId;
    private double amount;
    private Date dateCreated;
    private long confirmationNumber;
    private int customerId;

    public CustomerOrder(int customerOrderId, double amount, Date dateCreated, 
                                    long confirmationNumber, int customerId) {
        this.customerOrderId = customerOrderId;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.confirmationNumber = confirmationNumber;
        this.customerId = customerId;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(long confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" + "customerOrderId=" + customerOrderId 
                + ", amount=" + amount + ", dateCreated=" + dateCreated 
                + ", confirmationNumber=" + confirmationNumber 
                + ", customerId=" + customerId + '}';
    }
}