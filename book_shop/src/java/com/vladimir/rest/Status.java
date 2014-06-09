package com.vladimir.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * This is the root path for a RESTful Web Service API.
 * In the web.xml file, we specified that /api/* need to be in the url to 
 * get to this class.
 * 
 * We are versioning the class in the URL path. This the first version v1.
 * Example how to get to the root of this api resource:
 * http://localhost:8080/book_shop/api/v1/status/
 * 
 * @author Vladimir
 */
@Path("/v1/status")
public class Status {
    
    private static final String api_title = "<p>Java Web Service</p>";
    private static final String api_version = "00.01.00";
    
    /**
     * This method sits at the root of the api. It will return the name of the api.
     * Example: http://localhost:8080/book_shop/api/v1/status
     * 
     * @return String - Title of the api.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String returnTitle() {
        return api_title;
    }
    
    /**
     * This method will return the version number of the api.
     * Note: this is the nested one down from the root. 
     * You will need to add version into the url path.
     * Example: http://localhost:8080/book_shop/api/v1/status/version
     * 
     * @return String - version number of the api.
     */
    
    @Path("/version")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String returnVersion() {
        return "<p>Version: " + api_version + "</p>";
    }
    
}