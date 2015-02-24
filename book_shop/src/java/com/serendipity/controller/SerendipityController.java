package com.serendipity.controller;

import com.serendipity.dao.DAOCategory;
import com.serendipity.dao.DAOCustomer;
import com.serendipity.dao.DAOShoppingCart;
import com.serendipity.dao.DAOUser;
import com.serendipity.model.Category;
import com.serendipity.model.Customer;
import com.serendipity.model.ShoppingCart;
import com.serendipity.model.User;
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

/**
 *
 * @author Vladimir
 */
@WebServlet(name="SerendipityController",
            loadOnStartup = 1,
            urlPatterns = { "/search",
                            "/customerservice",
                            "/index",
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
        HttpSession session = request.getSession();
        String action = request.getServletPath();
        
        // Get shopping cart items
        if(session.getAttribute("username") != null) {
            
            int userId = -1;

//                   Get User Info
            String userName = (String) session.getAttribute("username");

            DAOUser daoUser = new DAOUser();
            User user = daoUser.getUserByUsername(userName);
            if(user != null) {
                userId = user.getUserId();
            }

            if(userId != -1) {
//                  Get shopping cart list count for the menu header
                List shoppingCartList = getShoppingCartLisByCustomerId(userId);
                request.setAttribute("shoppingCartListCount", shoppingCartList.size());

//                        Get customer id
                DAOCustomer daoCustomer = new DAOCustomer();
                Customer customer = daoCustomer.getCustomerById(userId);

                if(customer != null) {
                    session.setAttribute("customer", customer);
                }
            }
        }
        
//      get - index page request
        if(action.equalsIgnoreCase("/index")) {
            request.setAttribute("categoryList", getCategoryList());
            forward = "/index";
        }
        
//      get - search page request
        else if(action.equalsIgnoreCase("/search")) {
            forward = "/search";
        }

//      get - customer service page request
        else if(action.equalsIgnoreCase("/customerservice")) {
            forward = "/customerservice";
        }
        
//      get - switch language request
        else if(action.equalsIgnoreCase("chooselanguage")) {
        
//          TODO: switch language
        
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
        int categoryListSize = categoryList.size();
        if(categoryListSize > 5) {
            return categoryList.subList(0, 4);
        }
        return categoryList;
    }
    
    private List<ShoppingCart> getShoppingCartLisByCustomerId(int customerId) {
        DAOShoppingCart daoShoppingCart = new DAOShoppingCart();
        List shoppingCartList = daoShoppingCart.getShoppingCartByCustomerId(customerId);
        return shoppingCartList;
    }
}