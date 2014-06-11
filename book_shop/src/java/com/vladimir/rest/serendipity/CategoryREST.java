package com.vladimir.rest.serendipity;

import com.vladimir.model.Category;
import com.vladimir.dao.DAOCategory;

import com.vladimir.util.DbUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author Vladimir
 */
@Path("/v1/category")
public class CategoryREST {
    
    /**
     * The method creates its own HTTP resonse with the list of categories
     * 
     * @return - the response with the category list
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryList() throws Exception {
        
        Response response;
        DAOCategory category = new DAOCategory();
        String categoryList = category.getCategories();
        
        response = Response.ok(categoryList).build();
        
        return response;
    }    
}