package com.vladimir.model;

/**
 *
 * @author Vladimir
 */
public class Customer {
    
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;            
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String ccNumber;
    
    public Customer(int customerId, String firstName, String lastName,
                        String email, String phone, String address, String city,
                        String state, String zipCode, String ccNumber) {
    
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;            
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.ccNumber = ccNumber;
    
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", firstName=" 
                + firstName + ", lastName=" + lastName + ", email=" + email 
                + ", phone=" + phone + ", address=" + address + ", city=" 
                + city + ", state=" + state + ", zipCode=" + zipCode 
                + ", ccNumber=" + ccNumber + '}';
    }
}