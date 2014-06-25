package com.vladimir.rest.serendipity;

import com.vladimir.dao.DAOBook;
import com.vladimir.dao.DAOCategory;
import com.vladimir.model.Book;
import com.vladimir.model.Category;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


/**
 *
 * @author Vladimir
 */
@Path("/v1/book")
public class RESTBook {
    
    /**
     * The method creates its own HTTP response with the list of books
     * Ex: http://localhost:8080/book_shop/api/v1/book
     * 
     * @return - the response with the book list
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookList() throws Exception {
        
        String returnString = null;
        JSONArray jsonArrayBookList = new JSONArray();
        
        DAOBook daoBook = new DAOBook();
        JSONObject jsonObject = new JSONObject();
        
        try {
            
            jsonArrayBookList = daoBook.getAllBooks();
        
            // get books belonging to category
            for(int i = 0; i < jsonArrayBookList.length(); i++) {
                
                JSONObject obj = jsonArrayBookList.getJSONObject(i);
                DAOCategory daoCategory = new DAOCategory();
                                
                obj.put("book_id", obj.getString("book_id"));
                obj.put("title", obj.getString("title"));
                obj.put("price", obj.getDouble("price"));
                obj.put("description", obj.getString("description"));
                
//                Date Update
                String lastUpdate = obj.getString("last_update");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateUpdate = sdf.parse(lastUpdate);
                obj.put("last_update", dateUpdate);
                
                obj.put("category", daoCategory.getCategoryById(obj.getInt("category_id") ).getCategoryName());
                returnString = jsonObject.put(Integer.toString(obj.getInt("book_id")), obj).toString();
            }
        
        } catch (Exception e) {
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process get book request!");
            return Response.ok(jsonArrayBookList.put(jsonObject).toString()).build();
        }
                
        return Response.ok(returnString).build();
    }
    
    /**
     * The method creates its own HTTP response and adds Book to the database
     * 
     * @return - the response with the category name
     * @throws Exception 
     */
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON}) // access both form and json data
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(String bookInfo) throws Exception {
        
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        DAOBook daoBook = new DAOBook();
         
        try {
            
            JSONObject partsData = new JSONObject(bookInfo);
            
            Book book = new Book(   null,     
                                    partsData.optString("book_title"),
                                    partsData.optString("book_author"),
                                    partsData.optDouble("book_price"),
                                    partsData.optString("book_description"), 
                                    null,
                                    partsData.optInt("book_category_id"));
            
            int http_code = daoBook.addBook(book);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Book has been entered successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add book").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid category!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            //return Response.status(500).entity("Server unable to process request.").build();
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process request!");
            returnString = jsonArray.put(jsonObject).toString();
        }
        
        return Response.ok(returnString).build();
    }
    
    /**
     * This method will allow you to update data in the category table.
     * In this example we are using both PathParams and the message body (payload).
     */
    @PUT
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(String categoryInfo) throws Exception {
        
        String categoryId;
        String categoryName;
        Category category;
        DAOCategory daoCategory = new DAOCategory();
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            
            JSONObject partsData = new JSONObject(categoryInfo);
            categoryId = partsData.optString("category_id");
            categoryName = partsData.optString("category_name");
            
            int catId;
            try {
                catId = Integer.parseInt(categoryId);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Category id is not valid!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            category = new Category(catId, categoryName);
            int http_code = daoCategory.updateCategory(category);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Category has been updated successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add category").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Category was not updated!");
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
     * This method will allow you to delete data in the category table.
     * In this example we are using both PathParams and the message body (payload).
     */
    @DELETE
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(String categoryInfo) throws Exception {
        
        String categoryId;
        DAOCategory daoCategory = new DAOCategory();
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            
            JSONObject partsData = new JSONObject(categoryInfo);
            categoryId = partsData.optString("category_id");
            
            int catId;
            try {
                catId = Integer.parseInt(categoryId);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Category id is not valid!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            int http_code = daoCategory.deleteCategory(catId);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Category has been deleted successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add category").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Category was not deleted!");
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