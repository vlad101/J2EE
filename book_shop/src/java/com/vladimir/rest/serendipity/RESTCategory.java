package com.vladimir.rest.serendipity;

import com.vladimir.dao.DAOCategory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;


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
}