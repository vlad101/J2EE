package com.vladimir.rest.serendipity;

import com.vladimir.dao.DAOCategory;
import com.vladimir.model.Category;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


/**
 *
 * @author Vladimir
 */
@Path("/v1/category")
public class RESTCategory {
    
    /**
     * The method creates its own HTTP response with the list of categories
     * Ex: http://localhost:8080/book_shop/api/v1/category
     * 
     * @return - the response with the category list
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryList() throws Exception {
        
        Response response;
        
        DAOCategory daoCategory = new DAOCategory();
        JSONArray jsonCategoryList = daoCategory.getAllCategories();
        
        response = Response.ok(jsonCategoryList.toString()).build();
        
        return response;
    }

    /**
     * The method creates its own HTTP response with the category based on the
     * id passed by the client
     * Ex: http://localhost:8080/book_shop/api/v1/category/id/2
     * 
     * @return - the response with the category name
     * @throws Exception 
     */
    @GET
    @Path("/id/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(
            @PathParam("categoryId") String categoryId) 
                        throws Exception {
        
        Response response;
        int id;
        
        try {
            id = Integer.parseInt(categoryId);
        } catch (NumberFormatException e) {
            return Response.status(400).entity("Please supply the valid category id for this search").build();
        }
        
        DAOCategory daoCategory = new DAOCategory();
        JSONArray category = daoCategory.getCategoryById(id);
        
        response = Response.ok(category.toString()).build();
        
        return response;
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON}) // access both form and json data
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(String categoryInfo) throws Exception {
        
        String returnString = null;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        DAOCategory daoCategory = new DAOCategory();
         
        try {
            
            JSONObject partsData = new JSONObject(categoryInfo);
            int http_code = daoCategory.addCategory(partsData.optString("category_title"));
            
            System.err.println(http_code);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Category has been entered successfully");
                returnString = jsonArray.put(jsonObject).toString();
                
                System.out.println(returnString);
                
            } else {
                return Response.status(500).entity("Unable to process add category").build();
            }
            
        } catch (Exception e) {
            return Response.status(500).entity("Server unable to process request.").build();
        }
        
        return Response.ok(returnString).build();
    }
}