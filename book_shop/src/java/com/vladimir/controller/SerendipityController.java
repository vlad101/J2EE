package com.vladimir.controller;

import com.vladimir.dao.DAOCategory;
import com.vladimir.model.Category;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
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
@WebServlet(name="SerendipityController",
            loadOnStartup = 1,
            urlPatterns = {"/index",
                           "/book",
                           "/categorylist",
                           "/category",
                           "/addToCart",
                           "/cart",
                           "/updateCart",
                           "/checkout",
                           "/purchase",
                           "/chooseLanguage"})
public class SerendipityController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view";
    private static final String END_URL = ".jsp";
    
    public SerendipityController() {
        super();
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward = "";
        String action = request.getServletPath();
        
//      get - index page request
        if(action.equalsIgnoreCase("/index")) {
            request.setAttribute("categoryList", getCategoryList(action));
            forward = "/index";
        }
        
//      get - book page request
        else if(action.equalsIgnoreCase("/book")) {
            if(request.getParameterMap().containsKey("id")) {
                String bookId = request.getParameter("id");
                request.setAttribute("id", bookId);
                forward = "/book";
            } else {
                forward = "/error/error_404";
            }
        }
        
//      get - category list page request
        else if(action.equalsIgnoreCase("/categorylist")) {
            request.setAttribute("categoryList", getCategoryList(action));
            forward = "/categorylist";
        }
        
//      get - category page request
        else if(action.equalsIgnoreCase("/category")) {
            if(request.getParameterMap().containsKey("id")) {
                String categoryId = request.getParameter("id");
                System.out.println("!!!!!!!!!!!");
                System.out.println("CATEGORY ID: " + categoryId);
                System.out.println("!!!!!!!!!!!");
                request.setAttribute("id", categoryId);
                forward = "/category";
            } else {
                forward = "/error/error_404";
            }
        }
        
//      get - cart page request
        else if(action.equalsIgnoreCase("/cart")) {
        
//          TODO: cart
            forward = "/cart";
            
        }
        
//      get - checkout page request
        else if(action.equalsIgnoreCase("/checkout")) {
        
//          TODO: checkout
            forward = "/checkout";
            
        }
        
//      get - switch language request
        else if(action.equalsIgnoreCase("chooselanguage")) {
        
//          TODO: switch language
        
        }

//      post - add books to cart request
        else if(action.equalsIgnoreCase("/addtocart")) {
        
//          TODO: implement add to cart
        
        }
        
//      post - update books in cart request
        else if(action.equalsIgnoreCase("/updatecart")) {
        
//          TODO: implement update cart
        
        }
        
//      post - purchase request
        else if(action.equalsIgnoreCase("/purchase")) {
        
//          TODO: implement purchase
            forward = "/confirmation";
        
        }  else {
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
    
    private List<Category> getCategoryList(String indexPage) {
        DAOCategory daoCategory = new DAOCategory();
        List<Category> categoryList = daoCategory.getCategoryList();
//        if index page, return only four categories
//        if not index page, return all categories
        if(indexPage.equals("/index")) {
            int categoryListSize = categoryList.size();
            if(categoryListSize > 5);
                return categoryList.subList(0, 4);
        }
        return categoryList;
    }
}