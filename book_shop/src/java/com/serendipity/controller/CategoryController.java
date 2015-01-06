package com.serendipity.controller;

import com.serendipity.dao.DAOBook;
import com.serendipity.dao.DAOCategory;
import com.serendipity.dao.DAOImage;
import com.serendipity.model.Book;
import com.serendipity.model.Category;
import com.serendipity.model.Image;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vladimir
 */
@WebServlet(name="CategoryController",
            loadOnStartup = 1,
            urlPatterns = { "/categorylist",
                            "/category"})
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view/category";
    private static final String END_URL = ".jsp";
    
    public CategoryController() {
        super();
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward = "";
        String action = request.getServletPath();
        
//      get - index page request
        if(action.equalsIgnoreCase("/category/categorylist")) {
            request.setAttribute("categoryList", getCategoryList());
            forward = "/categorylist";
        }
        
//      get - category page request
        else if(action.equalsIgnoreCase("/category/category")) {
            if(request.getParameterMap().containsKey("id")) {
                String categoryId = request.getParameter("id");
                
//              check the category is valid integer
                if(isInteger(categoryId)) {
//                    check the category and book are valid
                    Category category = isValidCategory(categoryId);
                    if(category != null) {
                        int validCategoryId = Integer.parseInt(categoryId);
                        List<Book> bookList = getBookList(validCategoryId);
                        request.setAttribute("category", category);
                        request.setAttribute("bookList", bookList);
                        request.setAttribute("defaultImageMap", getDefaultImageMap(bookList));
                        
                        forward = "/category";
                    } else {
                        forward = "/error/error_404";
                    }
                } else {
                    forward = "/error/error_404";
                }
            }
        }
        
//      create view and forward request
        try {
            
            String url;
            
//                error page
            if( action.contains("error") ) {
                url = forward + END_URL;
            } else {
//                user pages
                url = START_URL + forward + END_URL;
            }
            
            RequestDispatcher view = request.getRequestDispatcher(url);
            view.forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(SerendipityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(SerendipityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(SerendipityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    Helper functions
    
    /**
     * Get category list
     * @param indexPage
     * @return
     * If index page, return only four categories
     * If not index page, return all categories 
     */
    private List<Category> getCategoryList() {
        DAOCategory daoCategory = new DAOCategory();
        List<Category> categoryList = daoCategory.getCategoryList();
        return categoryList;
    }
    
    /**
     * Get book list by category id
     * @param categoryId
     * @return
     * If index page, return only four categories
     * If not index page, return all categories 
     */
    private List<Book> getBookList(int categoryId) {
        DAOBook daoBook = new DAOBook();
        List<Book> bookList = daoBook.getBookListByCategoryId(categoryId);
        return bookList;
    }
    
    /**
     * Determine if category is an integer value
     * @param str
     * @return 
     */
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Return a valid category.
     * @param str
     * @return 
     */
    private Category isValidCategory(String str) {
        int categoryId = Integer.parseInt(str);
        DAOCategory daoCategory = new DAOCategory();
        Category category = daoCategory.getCategoryById(categoryId);
        return (category != null) ? category : null;
    }
    
    /**
     * Get default image maps with key book id and value default image map
     * If image contains a default image, the actual image path will be used
     * If image does not contain a default image, the default "no_image.jpg" will be used
     * 
     * @param bookList
     * @return 
     */
    private Map<Integer, String> getDefaultImageMap(List<Book> bookList) {
        Map<Integer, String> defaultImageMap = new HashMap<Integer, String>();
        for(Book book : bookList) {
            int bookId = book.getBookId();
            DAOImage daoImage = new DAOImage();
            Image image = daoImage.getDefaultImageByBookId(bookId);

            if(image != null) {
                String defaultImagePath = image.getPath();
                defaultImageMap.put(bookId, defaultImagePath);
            } else {
                defaultImageMap.put(bookId, "no_image.jpg");
            }
        }
        return defaultImageMap;
    }
}