package com.vladimir.controller;

import java.io.IOException;
import java.text.ParseException;
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
            forward = "/index";
        }
        
//      get - book page request
        else if(action.equalsIgnoreCase("/book")) {
            String bookId = request.getParameter("id");
            request.setAttribute("id", bookId);
            forward = "/book";
        }
        
//      get - category page request
        else if(action.equalsIgnoreCase("/category")) {
            
//          TODO: category
            forward = "/category";
            
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
}