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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCategoryList() throws Exception {
    
        DAOCategory category = new DAOCategory();
        String categoryList = category.getCategories();
               
        return categoryList;
    }    
}