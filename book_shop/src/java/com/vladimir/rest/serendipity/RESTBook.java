package com.vladimir.rest.serendipity;

import com.vladimir.dao.DAOBook;
import com.vladimir.dao.DAOCategory;
import com.vladimir.model.Book;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                                
                obj.put("book_id", obj.getInt("book_id"));
                obj.put("title", obj.getString("title"));
                obj.put("author", obj.getString("author"));
                obj.put("quantity", obj.getInt("quantity"));
                obj.put("price", obj.getDouble("price"));
                obj.put("description", obj.getString("description"));
                
//                Date Update
                String lastUpdate = obj.getString("last_update");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateUpdate = sdf.parse(lastUpdate);
                obj.put("last_update", dateUpdate);
                
                obj.put("category_id", obj.getInt("category_id"));
                obj.put("category_name", daoCategory.getCategoryById(obj.getInt("category_id")).getCategoryName());
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
     * @return - the response with the book name
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
            int addBookId = 0;
            
            String bookTitle = partsData.optString("book_title");
            String bookAuthor = partsData.optString("book_author");
            String bookQuantity = partsData.optString("book_quantity");
            String bookPrice = partsData.optString("book_price");
            String bookDescription = partsData.optString("book_description");
            String bookCategoryName = partsData.optString("book_category_name");
            
            System.out.println("-------------addbook - restbook--------------");
            System.out.println("title - " + bookTitle);
            System.out.println("author - " + bookAuthor);
            System.out.println("quantity - " + bookQuantity);
            System.out.println("price - " + bookPrice);
            System.out.println("description - " + bookDescription);
            System.out.println("category name - " + bookCategoryName);
            System.out.println("---------------------------");
            
//            validate numbers
            double addBookPrice;
            int addBookCategoryId;
            int addBookQuantity;
            
//            validate number values
            DAOCategory daoCategory = null;
            
            try {
                addBookPrice = Double.parseDouble(bookPrice);
                addBookCategoryId = daoCategory.getCategoryIdByName(bookCategoryName);
                if(addBookCategoryId == -1)
                    daoCategory.addCategory(bookCategoryName);
                    
                addBookQuantity = Integer.parseInt(bookQuantity);
                
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter valid number values!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }

//            validate title, author, and category name
            if (bookTitle == null || bookTitle.length() == 0 || 
                    bookAuthor == null || bookAuthor.length() == 0 ||
                    bookCategoryName.length() == 0 || bookCategoryName == null ) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid author, title, and category!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }            
            
            Book book = new Book(addBookId,bookTitle, bookAuthor, addBookQuantity, addBookPrice,bookDescription, null, addBookCategoryId);
            
            int http_code = daoBook.addBook(book);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Book has been entered successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add book").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid book info!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            //return Response.status(500).entity("Server unable to process request.").build();
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process request - add a book");
            returnString = jsonArray.put(jsonObject).toString();
        }
        
        return Response.ok(returnString).build();
    }
    
    /**
     * This method will allow you to update data in the book table.
     * In this example we are using both PathParams and the message body (payload).
     */
    @PUT
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(String bookInfo) throws Exception {
        
        Book book;
        DAOBook daoBook = new DAOBook();
        
        DAOCategory daoCategory = new DAOCategory();
        
        String returnString;
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            
            JSONObject partsData = new JSONObject(bookInfo);
            String bookId = partsData.optString("book_id");
            String bookTitle = partsData.optString("book_title");
            String bookAuthor = partsData.optString("book_author");
            String bookQuantity = partsData.optString("book_quantity");
            String bookPrice = partsData.optString("book_price");
            String bookDescription = partsData.optString("book_description");
            String bookCategoryName = partsData.optString("book_category_name"); 
            
            int updateBookId;
            double updateBookPrice;
            int updateBookCategoryId;
            int updateBookQuantity;

//            validate text values
            if(bookTitle == null || bookTitle.length() == 0 || 
                    bookAuthor == null || bookAuthor.length() == 0 || 
                    bookCategoryName.length() == 0 || bookCategoryName == null) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid book info!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
//            validate number values
            try {
                updateBookId = Integer.parseInt(bookId);
                updateBookPrice = Double.parseDouble(bookPrice);                
                updateBookCategoryId = daoCategory.getCategoryIdByName(bookCategoryName);
                
                System.out.println(updateBookCategoryId);
                if(updateBookCategoryId == -1)
                    daoCategory.addCategory(bookCategoryName);
                    
                updateBookQuantity = Integer.parseInt(bookQuantity);
                
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid number values!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
                        
            book = new Book(updateBookId, bookTitle, bookAuthor, updateBookQuantity, updateBookPrice, bookDescription, null, updateBookCategoryId);
            int http_code = daoBook.updateBook(book);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Book has been updated successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add book").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Book was not updated!");
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
     * This method will allow you to delete data in the book table.
     * In this example we are using both PathParams and the message body (payload).
     */
    @DELETE
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(String bookInfo) throws Exception {
        
        String bookId;
        DAOBook daoBook = new DAOBook();
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            
            JSONObject partsData = new JSONObject(bookInfo);
            bookId = partsData.optString("book_id");
            
            int deleteBookId;
            try {
                deleteBookId = Integer.parseInt(bookId);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Book id is not valid!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            int http_code = daoBook.deleteBook(deleteBookId);
            
            if(http_code == 200) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Book has been deleted successfully!");
                returnString = jsonArray.put(jsonObject).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add book").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Book was not deleted!");
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