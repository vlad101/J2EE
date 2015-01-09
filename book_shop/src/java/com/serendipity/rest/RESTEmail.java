package com.serendipity.rest;

import com.serendipity.dao.DAOEmail;
import com.serendipity.model.Email;
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


/**
 *
 * @author Vladimir
 */
@Path("/v1/email")
public class RESTEmail {
    
    /**
     * The method creates its own HTTP response with the email list
     * Ex: http://localhost:8080/book_shop/api/v1/email
     * 
     * @return - the response with the email list
     * @throws Exception t
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryList() throws Exception {
        
        String returnString = null;
        JSONArray jsonArrayEmailList = new JSONArray();
        
        DAOEmail daoEmail = new DAOEmail();
        JSONObject jsonObject = new JSONObject();
        
        try {
            
            jsonArrayEmailList = daoEmail.getEmailListJSONREST();
        
            // get books belonging to category
            for(int i = 0; i < jsonArrayEmailList.length(); i++) {
                
                JSONObject obj = jsonArrayEmailList.getJSONObject(i);
                
                obj.put("email_list_id", obj.getString("email_list_id"));
                obj.put("first_name", obj.getString("first_name"));
                obj.put("last_name", obj.getString("last_name"));
                obj.put("email", obj.getString("email"));
                returnString = jsonObject.put(Integer.toString(obj.getInt("email_list_id")), obj).toString();
            }
        
        } catch (Exception e) {
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process get email list request!");
            return Response.ok(jsonArrayEmailList.put(jsonObject).toString()).build();
        }
                
        return Response.ok(returnString).build();
    }
    
    /**
     * The method creates its own HTTP response and adds email to the 
     * email list database based on email passed by the client
     * 
     * @param emailInfo
     * @return - the response with the category name
     * @throws Exception 
     */
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON}) // access both form and json data
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmail(String emailInfo) throws Exception {
        
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        DAOEmail daoEmail = new DAOEmail();
         
        try {
            
            JSONObject partsData = new JSONObject(emailInfo);
            
            String firstName = partsData.optString("email_first_name_add");
            String lastName = partsData.optString("email_last_name_add");
            String email = partsData.optString("email_add");
            
            if (firstName == null || firstName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid first name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }

            if (lastName == null || lastName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid last name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }

            if (email == null || email.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid email!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }            
            
            Email emailEntry = new Email(0, firstName, lastName, email);
            int http_code = daoEmail.addEmail(emailEntry);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Email has been to email list successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Unable to add input entry!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process add email request!");
            returnString = jsonArray.put(jsonObject).toString();
        }
        
        return Response.ok(returnString).build();
    }
    
    /**
     * This method will allow you to update data in the email list table.
     * @param emailInfo
     * @return 
     * @throws java.lang.Exception
     */
    @PUT
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmailListInfo(String emailInfo) throws Exception {
        
        String emailListId;
        String firstName, lastName, email;
        Email emailListEntry;
        DAOEmail daoEmail = new DAOEmail();
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        
        try {
            
            JSONObject partsData = new JSONObject(emailInfo);
            emailListId = partsData.optString("email_list_id_update");
            firstName = partsData.optString("email_first_name_update");
            lastName = partsData.optString("email_last_name_update");
            email = partsData.optString("email_update");
            
//            validate email id
            int emailId;
            try {
                emailId = Integer.parseInt(emailListId);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Email id is not valid!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
//            validate first name
            if(firstName == null || firstName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid first name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
//            validate last name
            if(lastName == null || lastName.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid last name!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
//            validate email
            if(email == null || email.length() == 0) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid email!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            emailListEntry = new Email(emailId, firstName, lastName, email);
            
            int http_code = daoEmail.updateEmailInfo(emailListEntry);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Email info has been updated successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add category").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Email was not updated!");
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
     * This method will allow you to delete data in the email list table.
     * @param emailInfo
     * @return 
     * @throws java.lang.Exception
     */
    @DELETE
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmailListEntry(String emailInfo) throws Exception {
        
        String emailListId;
        DAOEmail daoEmail = new DAOEmail();
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            
            JSONObject partsData = new JSONObject(emailInfo);
            emailListId = partsData.optString("email_list_id");
            
            int emailId;
            try {
                emailId = Integer.parseInt(emailListId);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Email entry id id is not valid!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            int http_code = daoEmail.deleteEmailList(emailId);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Email list entry has been deleted successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Email list entry was not deleted!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process delete request!");
            returnString = jsonArray.put(jsonObject).toString();
        }
                
        return Response.ok(returnString).build();
    }  
}