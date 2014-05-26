package org.vladimir.model;

import java.util.Date;

/**
 *
 * @author Vladimir
 */
public class User {
    
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
    
    public void setUserId(int userId) {
    
        this.userId = userId;
        
    }
    
    public void setFirstName(String firstName) {
    
        this.firstName = firstName;
        
    }
    
    public void setLastName(String lastName) {
    
        this.lastName = lastName;
        
    }
    
    public void setEmail(String email) {
    
        this.email = email;
        
    }
    
    public void setDob(Date dob) {
    
        this.dob = dob;
    
    }
    
    public int getUserId(){
    
        return userId;
        
    }
    
    public String getFirstName() {
    
        return firstName;
    
    }
    
    public String getLastName() {
    
        return lastName;
    }
    
    public String email() {
    
        return email;
    
    }
    
    public Date getDate(){
    
        return dob;
        
    }
    
    @Override
    public String toString() {
    
        return "User [userId=" + userId  + " , firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", dob=" + dob + "]";
        
    }
}