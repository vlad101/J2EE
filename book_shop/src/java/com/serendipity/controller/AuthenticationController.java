package com.serendipity.controller;

import com.serendipity.dao.DAOCategory;
import com.serendipity.dao.DAOCustomer;
import com.serendipity.dao.DAOShoppingCart;
import com.serendipity.dao.DAOUser;
import com.serendipity.model.Category;
import com.serendipity.model.Customer;
import com.serendipity.model.ShoppingCart;
import com.serendipity.model.User;
import com.serendipity.util.CookieUtil;
import com.serendipity.util.PasswordUtil;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vladimir
 */
@WebServlet(name="AuthenticationController",
            loadOnStartup = 1,
            urlPatterns = {"/login/login",
                           "/login/logout",
                           "/login/userlogin"})
public class AuthenticationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL_ADMIN = "/WEB-INF/view";
    private static final String END_URL = ".jsp";
    private PasswordUtil passwordUtil;
    
    public AuthenticationController() {
    
        super();
    
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, NoSuchAlgorithmException {

        String forward;
        HttpSession session = request.getSession();
        String action = request.getServletPath();
        
        if(action.equalsIgnoreCase("/login/login")) {
            forward = "/login/login";
        }
        
        else if(action.equalsIgnoreCase("/login/logout")) {
            response.setContentType("text/html");
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
//                    if(cookie.getName().equals("JSESSIONID")){
//                        System.out.println("JSESSIONID="+cookie.getValue());
//                    }
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
            
            //invalidate the session if exists
            String logoutMessage = "You have been logged out successfully";
            request.setAttribute("logoutMessage", logoutMessage);
            
            session = request.getSession(false);
            if(session != null){
                session.invalidate();
            }
            
            forward = "/login/login";
        }
        
//   create authentication function to validate user
        else if(action.equalsIgnoreCase("/login/userlogin")) {
            
            forward = "/login/login";
            request.setAttribute("categoryList", getCategoryList());
            String error = "Either user name or password is wrong.";
            
            if(session.getAttribute("username") != null) {
                forward = "/index";
            }
            
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60); 
            
            if(request.getParameterMap().containsKey("password") && 
                    request.getParameterMap().containsKey("username")) {
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                boolean validUser = isValidUser(username, password);
                
                if(validUser) {
//                    get and set session attributes
                    session = request.getSession();
                    session.setAttribute("username", username);
                    session.setMaxInactiveInterval(30*60);
                    
//                  set cookies
                    Cookie userName = new Cookie("username", username);
                    response.addCookie(userName);
                    Cookie[] cookies = request.getCookies();
                    if(cookies != null){
                        request.setAttribute("username", CookieUtil.getCookieValue(cookies, "user"));
                        request.setAttribute("sessionID", CookieUtil.getCookieValue(cookies, "JSESSIONID"));
                    } else {
                        request.setAttribute("sessionID", session.getId());
                        request.setAttribute("error", error);
                    }
                    
//                    Get user info
                    Customer customer = getCustomer(username);
                    
//                  Get shopping cart list count for the menu header
                    List shoppingCartList = getShoppingCartLisByCustomerId(customer.getCustomerId());
                    request.setAttribute("shoppingCartListCount", shoppingCartList.size());
                    
                    if(customer != null) {
                        
//                        is user admin? set admin value
                        if(isUserAdmin(customer.getCustomerId())) {
                            session.setAttribute("isAdmin", true);
                        } else {
                            session.setAttribute("isAdmin", false);
                        }
                        
//                        set customer
                        session.setAttribute("customer", customer);
                        forward = "/index";
                    }
                } else {
                    request.setAttribute("error", error);
                }
            }
        }
        
//        Error page
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
            
                url = START_URL_ADMIN + forward + END_URL;
                
            }
            
            RequestDispatcher view;
            if(action.equalsIgnoreCase("/login/userlogin")) {
                
                int userId = -1;
                
                // Get shopping cart items
                if(session.getAttribute("username") != null) {
                    
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
                
                // Get the encoded URL string
                view = request.getRequestDispatcher(response.encodeRedirectURL(url));
            }
            else {
                view = request.getRequestDispatcher(url);
            }
            
            view.forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            
            processRequest(request, response);
            
        } catch (ParseException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    
    private Customer getCustomer(String username) {
        int userId = -1;
        Customer customer = null;
        DAOCustomer daoCustomer = new DAOCustomer();
        DAOUser daoUser = new DAOUser();
        
        User user = daoUser.getUserByUsername(username);
        if(user != null) {
            userId = user.getUserId();
        }
        
        if(userId != -1) {
            customer = daoCustomer.getCustomerById(userId);
        }
        return customer;
    }
    
    private boolean isValidUser(String username, String password) 
                        throws NoSuchAlgorithmException, IOException {
        boolean validUser = false;
        if(password != null && password.trim().length() > 0 &&
                    username != null && username.trim().length() > 0) {
            passwordUtil = new PasswordUtil();
            validUser = passwordUtil.authenticate(username, password);
        }
        return validUser;
    }
    
    private boolean isUserAdmin(int userId) {
        DAOUser daoUser = new DAOUser();
        User user = daoUser.getUserById(userId);
        if(user != null) {
            int isAdmin = user.getIsAdmin();
            return (isAdmin == 1);
        }
        return false;
    }
    
    private List<ShoppingCart> getShoppingCartLisByCustomerId(int customerId) {
        DAOShoppingCart daoShoppingCart = new DAOShoppingCart();
        List shoppingCartList = daoShoppingCart.getShoppingCartByCustomerId(customerId);
        return shoppingCartList;
    }
}