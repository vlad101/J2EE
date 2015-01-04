package com.serendipity.rest;

import com.serendipity.dao.DAOCustomer;
import com.serendipity.dao.DAOUser;
import com.serendipity.model.Customer;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


@Path("/v1/customer")
public class RESTCustomer {
    
    /**
     * The method creates its own HTTP response with the list of customers
     * Ex: http://localhost:8080/book_shop/api/v1/customer
     * 
     * @return - the response with the customer list
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerList() throws Exception {
        
        String returnString = null;
        JSONArray jsonArrayCustomerList = new JSONArray();
        
        DAOCustomer daoCustomer = new DAOCustomer();
        JSONObject jsonObject = new JSONObject();
        
        try {
            
            jsonArrayCustomerList = daoCustomer.getAllCustomers();
        
            // get all customers
            for(int i = 0; i < jsonArrayCustomerList.length(); i++) {
                
                JSONObject obj = jsonArrayCustomerList.getJSONObject(i);
                obj.put("customer_id", obj.getInt("customer_id"));
                obj.put("first_name", obj.getString("first_name"));
                obj.put("last_name", obj.getString("last_name"));
                obj.put("email", obj.getString("email"));
                obj.put("phone", obj.getString("phone"));
                obj.put("address", obj.getString("address"));
                obj.put("city", obj.getString("city"));
                obj.put("state", obj.getString("state"));
                obj.put("zipcode", obj.getString("zipcode"));
                obj.put("cc_number", obj.getString("cc_number"));
                returnString = jsonObject.put(Integer.toString(obj.getInt("customer_id")), obj).toString();
            }
        
        } catch (Exception e) {
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process get customer request!");
            return Response.ok(jsonArrayCustomerList.put(jsonObject).toString()).build();
        }
                
        return Response.ok(returnString).build();
    }
    
    /**
     * The method creates its own HTTP response and adds Customer to the database
     * 
     * @param customerInfo
     * @return - the response with the book name
     * @throws Exception 
     */
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON}) // access both form and json data
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(String customerInfo) throws Exception {
        
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        DAOCustomer daoCustomer = new DAOCustomer();
         
        try {
            
            JSONObject partsData = new JSONObject(customerInfo);
            
            String customerFirstName = partsData.optString("customer_first_name_add");
            String customerLastName = partsData.optString("customer_last_name_add");
            String userUsername = partsData.optString("customer_username_add");
            String userPassword1 = partsData.optString("customer_password1_add");
            String userPassword2 = partsData.optString("customer_password2_add");
            String customerEmail = partsData.optString("customer_email_add");
            String customerPhone = partsData.optString("customer_phone_add");
            String customerAddress = partsData.optString("customer_address_add");
            String customerCity = partsData.optString("customer_city_add");
            String customerState = partsData.optString("customer_state_add");
            String customerZipcode = partsData.optString("customer_zipcode_add");            
            String customerCcNumber = partsData.optString("customer_cc_number_add");
            
            // trim all white spaces from username and password
            userUsername = userUsername.replaceAll("\\s","");
            userPassword1 = userPassword1.replaceAll("\\s","");
            userPassword2 = userPassword2.replaceAll("\\s","");
            
//            validate text values
            if(customerFirstName == null || customerFirstName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer first name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            if(customerLastName == null || customerLastName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer last name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }

            if(userUsername == null || userUsername.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer username!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
             if(userPassword1 == null || userPassword1.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer password!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
             
            if(userPassword2 == null || userPassword2.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer password confirm!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            if(!userPassword1.equals(userPassword2)) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Cutstomer password and password confirm do not match!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            if(customerEmail == null || customerEmail.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer email!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
 
            if(customerPhone == null || customerPhone.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer phone!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            } 
             
            if(customerAddress == null || customerAddress.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer address!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            } 
            
            if(customerCity == null || customerCity.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer city!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            } 
            
            if(customerState == null || customerState.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer state!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
//            validate username is unique
            DAOUser daoUser = new DAOUser();
            if(!daoUser.isUniqueUsername(userUsername)) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "The customer username already in use!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
             
//            validate number values
            long updateCustomerCcNumber;
            long updateCustomerZipcode;
            
            try {
                updateCustomerCcNumber = Long.parseLong(customerCcNumber);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer credit card number!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            try {
                updateCustomerZipcode = Long.parseLong(customerZipcode);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer zipcode!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            Customer customer = new Customer(0,customerFirstName,
                    customerLastName,customerEmail,customerPhone,customerAddress,
                    customerCity,customerState,updateCustomerZipcode,updateCustomerCcNumber);
            
            int http_code = daoCustomer.addCustomer(customer, userUsername, userPassword1);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Customer has been entered successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add book").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer info!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            //return Response.status(500).entity("Server unable to process request.").build();
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process add a customer request");
            returnString = jsonArray.put(jsonObject).toString();
        }
        
        return Response.ok(returnString).build();
    }
    
    /**
     * This method will allow you to update data in the customer table.
     * In this example we are using both PathParams and the message body (payload).
     * @param customerInfo
     * @return 
     * @throws java.lang.Exception
     */
    @PUT
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(String customerInfo) throws Exception {
        
        Customer customer;
        DAOCustomer daoCustomer = new DAOCustomer();
        
        String returnString;
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            
            JSONObject partsData = new JSONObject(customerInfo);
            String customerId = partsData.optString("customer_id_update");
            String customerFirstName = partsData.optString("customer_first_name_update");
            String customerLastName = partsData.optString("customer_last_name_update");
            String customerEmail = partsData.optString("customer_email_update");
            String customerPhone = partsData.optString("customer_phone_update");
            String customerAddress = partsData.optString("customer_address_update");
            String customerCity = partsData.optString("customer_city_update");
            String customerState = partsData.optString("customer_state_update");
            String customerZipcode = partsData.optString("customer_zipcode_update");
            String customerCcNumber = partsData.optString("customer_cc_number_update");

//            validate text values
            if(customerFirstName == null || customerFirstName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer first name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            if(customerLastName == null || customerLastName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer last name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
             
            if(customerEmail == null || customerEmail.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer email!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
 
            if(customerPhone == null || customerPhone.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer phone!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            } 
             
            if(customerAddress == null || customerAddress.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer address!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            } 
            
            if(customerCity == null || customerCity.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer city!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            } 
            
            if(customerState == null || customerState.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid customer state!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
//            validate number values
            int updateCustomerId;
            long updateCustomerCcNumber;
            long updateCustomerZipcode;
            
            try {
                updateCustomerId = Integer.parseInt(customerId);
                updateCustomerCcNumber = Long.parseLong(customerCcNumber);
                updateCustomerZipcode = Long.parseLong(customerZipcode);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter valid number values!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
   
            customer = new Customer(updateCustomerId,customerFirstName,
                    customerLastName,customerEmail,customerPhone,customerAddress,
                    customerCity,customerState,updateCustomerZipcode,updateCustomerCcNumber);
            int http_code = daoCustomer.updateCustomer(customer);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Customer has been updated successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add customer").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Customer was not updated!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            //return Response.status(500).entity("Server unable to process request.").build();
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process update request!");
            returnString = jsonArray.put(jsonObject).toString();
        }
                
        return Response.ok(returnString).build();
    }
    
    /**
     * This method will allow you to delete data in the customer table.
     * In this example we are using both PathParams and the message body (payload).
     * @param customerInfo
     * @return 
     * @throws java.lang.Exception
     */
    @DELETE
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(String customerInfo) throws Exception {
        
        String customerId;
        DAOCustomer daoCustomer = new DAOCustomer();
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            
            JSONObject partsData = new JSONObject(customerInfo);
            customerId = partsData.optString("customer_id");
            
            int deleteCustomerId;
            try {
                deleteCustomerId = Integer.parseInt(customerId);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Customer id is not valid!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            int http_code = daoCustomer.deleteCustomer(deleteCustomerId);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Customer has been deleted successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add book").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Customer was not deleted!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            //return Response.status(500).entity("Server unable to process request.").build();
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process delete request!");
            returnString = jsonArray.put(jsonObject).toString();
        }
                
        return Response.ok(returnString).build();
    }  
}