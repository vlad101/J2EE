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
@WebServlet(name="BookController",
            loadOnStartup = 1,
            urlPatterns = { "/book/booklist",
                            "/book/book"})
public class BookController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view/book";
    private static final String END_URL = ".jsp";
    
    public BookController() {
        super();
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward = "";
        String action = request.getServletPath();

//      get - book list page request
        if(action.equalsIgnoreCase("/book/booklist")) {
            List bookList = getBookList();
            request.setAttribute("bookList", getBookList());
            request.setAttribute("defaultImageMap", getDefaultImageMap(bookList));
            forward = "/booklist";
        }
        
//      get - book page request
        else if(action.equalsIgnoreCase("/book/book")) {
            if(request.getParameterMap().containsKey("id")) {
                String bookId = request.getParameter("id");
                
                int validBookId;
                try
                {
                    validBookId = Integer.parseInt(bookId);
                    Book book = getBookByBookId(validBookId);
                    if(book != null) {
                        request.setAttribute("book", book);
                        int categoryId = book.getCategoryId();
                        Category category = getCategoryByCategoryId(categoryId);
                        if(category  != null) {
                            request.setAttribute("category", category);
                            forward = "/book";
                        } else {
                            forward = "/error/error_404";
                        }
                    } else {
                        forward = "/error/error_404";
                    }
                } catch(NumberFormatException e) {
                    forward = "/error/error_404";
                }
            } else {
                forward = "/error/error_404";
            }
        }
        
        else {
            forward = "/error/error_404";
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
    private List<Book> getBookList() {
        DAOBook daoBook = new DAOBook();
        List<Book> bookList = daoBook.getBookList();
        return bookList;
    }

    private Book getBookByBookId(int bookId) {
        DAOBook daoBook = new DAOBook();
        Book book = daoBook.getBookById(bookId);
        if(book != null)
            return book;
        return null;
    }
    
    private Category getCategoryByCategoryId(int categoryId) {
        DAOCategory daoCategory = new DAOCategory();
        Category category = daoCategory.getCategoryById(categoryId);
        if(category != null)
            return category;
        return null;
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