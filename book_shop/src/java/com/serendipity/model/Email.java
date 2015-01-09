package com.serendipity.model;

/**
 *
 * @author vladimir
 */
public class Email {
    
    private int emailListId;
    private String firstName;
    private String lastName;
    private String email;
    
    public Email(int emailListId, String firstName, String lastName, String email) {
        this.emailListId = emailListId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public void setEmailListId(int emailListId) {
        this.emailListId = emailListId;
    }
    
    public int getEmailListId() {
        return emailListId;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}