package com.serendipity.rest;

import com.serendipity.dao.DAOBook;
import com.serendipity.dao.DAOCategory;
import com.serendipity.dao.DAOImage;
import com.serendipity.model.Book;
import com.serendipity.model.Image;
import com.serendipity.util.ImageFileUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


/**
 *
 * @author Vladimir
 */
@Path("/v1/book")
public class RESTBook {

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") String bookId) throws JSONException {

        DAOBook daoBook = new DAOBook();
        Book book;
        String returnString;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject obj = new JSONObject();

        try {
            
            int getBookId;
            try {
                getBookId = Integer.parseInt(bookId);
            } catch (NumberFormatException e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Book id is not valid!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }

            try {
                
                book = daoBook.getBookById(getBookId);
            
                DAOCategory daoCategory = new DAOCategory();

                obj.put("book_id", book.getBookId());
                obj.put("title", book.getTitle());
                obj.put("author", book.getAuthor());
                obj.put("quantity", book.getQuantity());
                obj.put("price", book.getPrice());
                obj.put("description", book.getDescription());
                obj.put("category_id", book.getCategoryId());
                obj.put("category_name", daoCategory.getCategoryById(book.getCategoryId()).getCategoryName());
                
//                Set a Date format
                String updateDateFormat = "yyyy-MM-dd"; // yyyy-MM-dd HH:mm:ss
                SimpleDateFormat customFormat = new SimpleDateFormat(updateDateFormat );
                customFormat.setLenient(false);
                obj.put("last_update", customFormat.format(book.getLastUpdate()));
                
//                get image path for book
                DAOImage daoImage = new DAOImage();
                List<String> listImage = daoImage.getImageByBookId(book.getBookId());
                if(!listImage.isEmpty())
                    obj.put("image_path", listImage);
            } catch (Exception e) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Server unable to process get book request!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }
            
            if(book != null) {
                
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("MSG", "Book was retrieved successfully!");
                returnString = jsonObject.put(Integer.toString(book.getBookId()), obj).toString();
                
            } else {
                //return Response.status(500).entity("Unable to process add book").build();
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Book was not retrieved successfully!");
                returnString = jsonArray.put(jsonObject).toString();
            }
            
        } catch (Exception e) {
            //return Response.status(500).entity("Server unable to process request.").build();
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process get book request!");
            returnString = jsonArray.put(jsonObject).toString();
        }
                
        return Response.ok(returnString).build();
    }
    
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
                
//                get image path for book
                DAOImage daoImage = new DAOImage();
                List<String> listImage = daoImage.getImageByBookId(obj.getInt("book_id"));
                if(!listImage.isEmpty())
                    obj.put("image_path", listImage);
                
                returnString = jsonObject.put(Integer.toString(obj.getInt("book_id")), obj).toString();
            }
        
        } catch (JSONException e) {
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process get book request!");
            return Response.ok(jsonArrayBookList.put(jsonObject).toString()).build();
        } catch (ParseException e) {
            jsonObject.put("HTTP_CODE", "500");
            jsonObject.put("MSG", "Server unable to process get book request!");
            return Response.ok(jsonArrayBookList.put(jsonObject).toString()).build();
        }
                
        return Response.ok(returnString).build();
    }
    
    /**
     * The method creates its own HTTP response and adds Book to the database
     * 
     * @param bookInfo
     * @return - the response with the book name
     * @throws Exception 
     */
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON}) // access both form and json data
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(String bookInfo) throws Exception {
        
        String returnString = null;
        String bookImage = null;
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
            bookImage = partsData.optString("book_image_add");
   
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
                    bookCategoryName == null || bookCategoryName.length() == 0 ) {
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("MSG", "Enter a valid author, title, and category!");
                return Response.ok(jsonArray.put(jsonObject).toString()).build();
            }            
            
            Book book = new Book(addBookId,bookTitle, bookAuthor, addBookQuantity, addBookPrice,bookDescription, null, addBookCategoryId);

//            return id of the added book for the image path purpose instead of 200
            int http_code = daoBook.addBook(book);
            
//            add an image to the book
//            the file name consists of the book id and unique date
            if(bookImage.contains("data")) {
                
                String ext;
                if(bookImage.contains("data:image/jpg"))
                    ext = ".jpg";
                else if(bookImage.contains("data:image/jpeg"))
                    ext = ".jpeg";
                else
                    ext = ".png";
                
                Date date = new Date();
                String fileName = Integer.toString(http_code) + " " + date.toString();
                ImageFileUtil imageUtil = new ImageFileUtil();
                imageUtil.decodeImage(bookImage, fileName);
                
                Image image = new Image(0, fileName.replaceAll(" ", "").replaceAll(":", "") + ext, http_code);
                DAOImage daoImage = new DAOImage();
                http_code = daoImage.addImage(image);
            }
            
            if(http_code != 500) {
                
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
     * @param bookInfo
     * @return 
     * @throws java.lang.Exception 
     */
    @PUT
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(String bookInfo) throws Exception {
        
        Book book;
        DAOBook daoBook = new DAOBook();
        
        DAOCategory daoCategory = new DAOCategory();
        
        String returnString;
        String bookImage;
        String deleteImageList;
        String defaultImage;
        
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
            bookImage = partsData.optString("book_image_update");
            deleteImageList = partsData.optString("book_image_list_delete_update");
            defaultImage = partsData.optString("book_image_default_update");
                        
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
            
//            add an image to the book
//            the file name consists of the book id and unique date
            if(bookImage.contains("data")) {
                
                String ext;
                if(bookImage.contains("data:image/jpg"))
                    ext = ".jpg";
                else if(bookImage.contains("data:image/jpeg"))
                    ext = ".jpeg";
                else
                    ext = ".png";
                
                Date date = new Date();
                String fileName = Integer.toString(updateBookId) + " " + date.toString();
                ImageFileUtil imageUtil = new ImageFileUtil();
                imageUtil.decodeImage(bookImage, fileName);
                
                Image image = new Image(0, fileName.replaceAll(" ", "").replaceAll(":", "") + ext, updateBookId);
                DAOImage daoImage = new DAOImage();
                http_code = daoImage.addImage(image);
            }
            
            if(deleteImageList != null && deleteImageList.length() != 0) {
                List<String> imageList = Arrays.asList(deleteImageList.split("\\s*,\\s*"));
                for(String imageName : imageList) {
                    Image image = new Image(0, imageName, updateBookId);
                    DAOImage daoImage = new DAOImage();
                    http_code = daoImage.deleteImage(image);
                }
            }
            
            if(deleteImageList != null && deleteImageList.length() != 0) {
                List<String> imageList = Arrays.asList(deleteImageList.split("\\s*,\\s*"));
                for(String imageName : imageList) {
                    Image image = new Image(0, imageName, updateBookId);
                    DAOImage daoImage = new DAOImage();
                    http_code = daoImage.deleteImage(image);
                }
            }
            
            if(defaultImage != null && defaultImage.length() != 0) {
                DAOImage daoImage = new DAOImage();
                http_code = daoImage.setDefaultImage(defaultImage, updateBookId);
            }
            
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
     * In this example we are using both PathParams and the message body (payload)
     * @param bookInfo.
     * @return 
     * @throws java.lang.Exception
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