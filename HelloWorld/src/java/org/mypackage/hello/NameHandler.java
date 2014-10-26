package org.mypackage.hello;

public class NameHandler 
{
    private String firstName;
    private String lastName;
    
    public NameHandler()
    {
        firstName = null;
        lastName = null;
    }

    public String getFirstName() 
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
