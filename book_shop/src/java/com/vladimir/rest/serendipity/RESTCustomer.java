package com.vladimir.rest.serendipity;

import com.vladimir.dao.DAOBook;
import com.vladimir.dao.DAOCategory;
import com.vladimir.dao.DAOCustomer;
import com.vladimir.model.Book;
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


///**
// *
// * @author Vladimir
// */
//@Path("/v1/category")
//public class RESTCategory {
//    
//    /**
//     * The method creates its own HTTP response with the list of categories
//     * Ex: http://localhost:8080/book_shop/api/v1/category
//     * 
//     * @return - the response with the category list
//     * @throws Exception 
//     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getCategoryList() throws Exception {
//        
//        String returnString = null;
//        JSONArray jsonArrayCategoryList = new JSONArray();
//        
//        DAOCategory daoCategory = new DAOCategory();
//        JSONObject jsonObject = new JSONObject();
//        
//        try {
//            
//            jsonArrayCategoryList = daoCategory.getAllCategories();
//        
//            // get books belonging to category
//            for(int i = 0; i < jsonArrayCategoryList.length(); i++) {
//                
//                JSONObject obj = jsonArrayCategoryList.getJSONObject(i);
//                DAOBook daoBook = new DAOBook();
//                
////                Create a json array of the following format:
////                {"category_id":{"category_id":"1","category_name":"Category 1","book_list":["Title 1","Title 2"]}, ...}
//                obj.put("category_id", obj.getString("category_id"));
//                obj.put("category_name", obj.getString("category_name"));
//                obj.put("book_list", daoBook.getBookListByCategoryId( obj.getInt("category_id") ));
//                returnString = jsonObject.put(Integer.toString(obj.getInt("category_id")), obj).toString();
//            }
//        
//        } catch (Exception e) {
//            jsonObject.put("HTTP_CODE", "500");
//            jsonObject.put("MSG", "Server unable to process get category request!");
//            return Response.ok(jsonArrayCategoryList.put(jsonObject).toString()).build();
//        }
//                
//        return Response.ok(returnString).build();
//    }
//    
//    /**
//     * The method creates its own HTTP response and adds category to the 
//     * database based on category name passed by the client
//     * 
//     * @return - the response with the category name
//     * @throws Exception 
//     */
//    @POST
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON}) // access both form and json data
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addCategory(String categoryInfo) throws Exception {
//        
//        String returnString;
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//        DAOCategory daoCategory = new DAOCategory();
//         
//        try {
//            
//            JSONObject partsData = new JSONObject(categoryInfo);
//            
//            String categoryTitle = partsData.optString("category_title");
//            
//            if (categoryTitle == null || categoryTitle.length() == 0) {
//                jsonObject.put("HTTP_CODE", "500");
//                jsonObject.put("MSG", "Enter a valid category name!");
//                return Response.ok(jsonArray.put(jsonObject).toString()).build();
//            }
//            
//            int http_code = daoCategory.addCategory(categoryTitle);
//            
//            if(http_code == 200) {
//                
//                jsonObject.put("HTTP_CODE", "200");
//                jsonObject.put("MSG", "Category has been entered successfully!");
//                returnString = jsonArray.put(jsonObject).toString();
//                
//            } else {
//                //return Response.status(500).entity("Unable to process add category").build();
//                jsonObject.put("HTTP_CODE", "500");
//                jsonObject.put("MSG", "Enter a valid category!");
//                returnString = jsonArray.put(jsonObject).toString();
//            }
//            
//        } catch (Exception e) {
//            //return Response.status(500).entity("Server unable to process request.").build();
//            jsonObject.put("HTTP_CODE", "500");
//            jsonObject.put("MSG", "Server unable to process request!");
//            returnString = jsonArray.put(jsonObject).toString();
//        }
//        
//        return Response.ok(returnString).build();
//    }
//    
//    /**
//     * This method will allow you to update data in the category table.
//     * In this example we are using both PathParams and the message body (payload).
//     */
//    @PUT
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateCategory(String categoryInfo) throws Exception {
//        
//        String categoryId;
//        String categoryName;
//        Category category;
//        DAOCategory daoCategory = new DAOCategory();
//        String returnString;
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//        
//        try {
//            
//            JSONObject partsData = new JSONObject(categoryInfo);
//            categoryId = partsData.optString("category_id");
//            categoryName = partsData.optString("category_name");
//            
////            validate category id
//            int catId;
//            try {
//                catId = Integer.parseInt(categoryId);
//            } catch (NumberFormatException e) {
//                jsonObject.put("HTTP_CODE", "500");
//                jsonObject.put("MSG", "Category id is not valid!");
//                return Response.ok(jsonArray.put(jsonObject).toString()).build();
//            }
//            
////            validate category title
//            if(categoryName == null || categoryName.length() == 0) {
//                jsonObject.put("HTTP_CODE", "500");
//                jsonObject.put("MSG", "Enter a valid category name!");
//                return Response.ok(jsonArray.put(jsonObject).toString()).build();
//            }
//            
//            category = new Category(catId, categoryName);
//            int http_code = daoCategory.updateCategory(category);
//            
//            if(http_code == 200) {
//                
//                jsonObject.put("HTTP_CODE", "200");
//                jsonObject.put("MSG", "Category has been updated successfully!");
//                returnString = jsonArray.put(jsonObject).toString();
//                
//            } else {
//                //return Response.status(500).entity("Unable to process add category").build();
//                jsonObject.put("HTTP_CODE", "500");
//                jsonObject.put("MSG", "Category was not updated!");
//                returnString = jsonArray.put(jsonObject).toString();
//            }
//            
//        } catch (Exception e) {
//            //return Response.status(500).entity("Server unable to process request.").build();
//            jsonObject.put("HTTP_CODE", "500");
//            jsonObject.put("MSG", "Server unable to process update request!");
//            returnString = jsonArray.put(jsonObject).toString();
//        }
//                
//        return Response.ok(returnString).build();
//    }
//    
//    /**
//     * This method will allow you to delete data in the category table.
//     * In this example we are using both PathParams and the message body (payload).
//     */
//    @DELETE
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteCategory(String categoryInfo) throws Exception {
//        
//        String categoryId;
//        DAOCategory daoCategory = new DAOCategory();
//        String returnString;
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            
//            JSONObject partsData = new JSONObject(categoryInfo);
//            categoryId = partsData.optString("category_id");
//            
//            int catId;
//            try {
//                catId = Integer.parseInt(categoryId);
//            } catch (NumberFormatException e) {
//                jsonObject.put("HTTP_CODE", "500");
//                jsonObject.put("MSG", "Category id is not valid!");
//                return Response.ok(jsonArray.put(jsonObject).toString()).build();
//            }
//            
//            int http_code = daoCategory.deleteCategory(catId);
//            
//            if(http_code == 200) {
//                
//                jsonObject.put("HTTP_CODE", "200");
//                jsonObject.put("MSG", "Category has been deleted successfully!");
//                returnString = jsonArray.put(jsonObject).toString();
//                
//            } else {
//                //return Response.status(500).entity("Unable to process add category").build();
//                jsonObject.put("HTTP_CODE", "500");
//                jsonObject.put("MSG", "Category was not deleted!");
//                returnString = jsonArray.put(jsonObject).toString();
//            }
//            
//        } catch (Exception e) {
//            //return Response.status(500).entity("Server unable to process request.").build();
//            jsonObject.put("HTTP_CODE", "500");
//            jsonObject.put("MSG", "Server unable to process delete request!");
//            returnString = jsonArray.put(jsonObject).toString();
//        }
//                
//        return Response.ok(returnString).build();
//    }  
//}
//

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
            
            String bookTitle = partsData.optString("book_title_add");
            String bookAuthor = partsData.optString("book_author_add");
            String bookQuantity = partsData.optString("book_quantity_add");
            String bookPrice = partsData.optString("book_price_add");
            String bookDescription = partsData.optString("book_description_add");
            String bookCategoryName = partsData.optString("book_category_name_add");
                        
//            validate numbers
            double addBookPrice;
            int addBookCategoryId;
            int addBookQuantity;
            
//            validate number values
            DAOCategory daoCategory = new DAOCategory();
            
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
            String bookId = partsData.optString("book_id_update");
            String bookTitle = partsData.optString("book_title_update");
            String bookAuthor = partsData.optString("book_author_update");
            String bookQuantity = partsData.optString("book_quantity_update");
            String bookPrice = partsData.optString("book_price_update");
            String bookDescription = partsData.optString("book_description_update");
            String bookCategoryName = partsData.optString("book_category_name_update"); 
            
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
     * This method will allow you to delete data in the customer table.
     * In this example we are using both PathParams and the message body (payload).
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