package com.serendipity.controller;

import com.serendipity.dao.DAOCategory;
import com.serendipity.model.Category;
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
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Vladimir
 */
@WebServlet(name="CartController",
            loadOnStartup = 1,
            urlPatterns = { "/cart/addToCart",
                            "/cart/cart",
                            "/cart/updateCart"})
public class CartController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view/cart";
    private static final String END_URL = ".jsp";
    
    public CartController() {
        super();
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, JSONException {

        String forward;
        HttpSession session = request.getSession();
        String action = request.getServletPath();
        JSONObject json = new JSONObject();
        
        if(action.equalsIgnoreCase("/cart/cart")) {
        
//          TODO: cart
            
            if(session.getAttribute("username") == null) {
                forward = "/login/login";
            } else {
                forward = "/cart";
            }
        }

//      post - add books to cart request
        else if(action.equalsIgnoreCase("/cart/addtocart")) {
            
            int customerId = -1;
            int bookId = -1;
            int bookQty = -1;
            
            if(request.getParameterMap().containsKey("customer_id")) {
                customerId = Integer.parseInt(request.getParameter("customer_id"));
            }
            if(request.getParameterMap().containsKey("book_id")) {
                bookId = Integer.parseInt(request.getParameter("book_id"));
            }
            if(request.getParameterMap().containsKey("book_qty")) {
                bookQty = Integer.parseInt(request.getParameter("book_qty"));
            }
            
            try {
                if(customerId != -1 && bookId != -1 && bookQty != -1) {
                    System.out.println("customerId = " + customerId);
                    System.out.println("bookId = " + bookId);
                    System.out.println("bookQty = " + bookQty);
                    json.put("add", true);
                } else {
                    json.put("add", false);
                }                 
            } catch (JSONException e) { 
                json.put("add", false);
            }
            
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
            return;
        }
        
//      post - update books in cart request
        else if(action.equalsIgnoreCase("/cart/updatecart")) {
        
//          TODO: implement update cart
            return;
        }
        
//     error page   
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
        } catch (JSONException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(SerendipityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}