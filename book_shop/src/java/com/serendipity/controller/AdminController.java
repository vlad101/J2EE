package com.serendipity.controller;

import com.serendipity.dao.DAOCustomer;
import com.serendipity.dao.DAOShoppingCart;
import com.serendipity.dao.DAOUser;
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
@WebServlet(name="AdminController",
            loadOnStartup = 1,
            urlPatterns = {"/admin",
                           "/admin/editCategory",
                           "/admin/editBook",
                           "/admin/editCustomer",
                           "/admin/editCustomerOrder",
                           "/admin/editOrderedBook"})
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL_ADMIN = "/WEB-INF/view/admin";
    private static final String START_URL = "/WEB-INF/view";
    private static final String END_URL = ".jsp";
    private boolean isLoggedIn = true;
    
    public AdminController() {
    
        super();
        //dao = new UserDao();
    
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward;
        HttpSession session = request.getSession();
        String action = request.getServletPath();
        forward = "/login/login";
        
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

//                  Get customer id
                DAOCustomer daoCustomer = new DAOCustomer();
                Customer customer = daoCustomer.getCustomerById(userId);

                if(customer != null) {
                    session.setAttribute("customer", customer);
                }
            }
        }
        
        String username = (String) session.getAttribute("username");
        int userId = -1;
        Customer customer = null;
        User user;
        DAOUser daoUser = new DAOUser();
        
        if(action.equalsIgnoreCase("/admin")) {
            
            if(session.getAttribute("username") != null) {
                
                isLoggedIn = true;
                
//               Get User Info
                user = daoUser.getUserByUsername(username);
                if(user != null) {
                    userId = user.getUserId();
                }
                
//               Get Customer info
                if(userId != -1) {
                    customer = getCustomer(username);
                }

                if(customer != null) {
                        
                    forward = "/admin";
                    
//                    is user admin? set admin value
                    if(isUserAdmin(customer.getCustomerId())) {
                        session.setAttribute("isAdmin", true);
                    } else {
                        session.setAttribute("isAdmin", false);
                    }

//                        set customer
                    session.setAttribute("customer", customer);
                }
            } else {
                isLoggedIn = false;
            }
        }
        
//        admin edit category
        else if(action.equalsIgnoreCase("/admin/editcategory")) {
            
            if(session.getAttribute("username") != null) {
                
                isLoggedIn = true;
                
//               Get User Info
                user = daoUser.getUserByUsername(username);
                if(user != null) {
                    userId = user.getUserId();
                }
                
//               Get Customer info
                if(userId != -1) {
                    customer = getCustomer(username);
                }

                if(customer != null) {
                    
                    forward = "/editcategory";
                        
//                    is user admin? set admin value
                    if(isUserAdmin(customer.getCustomerId())) {
                        session.setAttribute("isAdmin", true);
                    } else {
                        session.setAttribute("isAdmin", false);
                    }

//                        set customer
                    session.setAttribute("customer", customer);
                }
            } else {
                isLoggedIn = false;
            }
        }
        
 //        admin edit book
        else if(action.equalsIgnoreCase("/admin/editbook")) {
            
            if(session.getAttribute("username") != null) {
                
                isLoggedIn = true;
                
//               Get User Info
                user = daoUser.getUserByUsername(username);
                if(user != null) {
                    userId = user.getUserId();
                }
                
//               Get Customer info
                if(userId != -1) {
                    customer = getCustomer(username);
                }

                if(customer != null) {
                    
                    forward = "/editbook";
                        
//                    is user admin? set admin value
                    if(isUserAdmin(customer.getCustomerId())) {
                        session.setAttribute("isAdmin", true);
                    } else {
                        session.setAttribute("isAdmin", false);
                    }

//                        set customer
                    session.setAttribute("customer", customer);
                }
            } else {
                isLoggedIn = false;
            }
        }       
        
 //        admin edit customer
        else if(action.equalsIgnoreCase("/admin/editcustomer")) {
            
            if(session.getAttribute("username") != null) {
                
                isLoggedIn = true;
                
//               Get User Info
                user = daoUser.getUserByUsername(username);
                if(user != null) {
                    userId = user.getUserId();
                }
                
//               Get Customer info
                if(userId != -1) {
                    customer = getCustomer(username);
                }

                if(customer != null) {
                    
                    forward = "/editcustomer";
                        
//                    is user admin? set admin value
                    if(isUserAdmin(customer.getCustomerId())) {
                        session.setAttribute("isAdmin", true);
                    } else {
                        session.setAttribute("isAdmin", false);
                    }

//                        set customer
                    session.setAttribute("customer", customer);
                }
            } else {
                isLoggedIn = false;
            }
        }
        
 //        admin edit customer order
        else if(action.equalsIgnoreCase("/admin/editcustomerorder")) {
            
            if(session.getAttribute("username") != null) {
                
                isLoggedIn = true;
                
//               Get User Info
                user = daoUser.getUserByUsername(username);
                if(user != null) {
                    userId = user.getUserId();
                }
                
//               Get Customer info
                if(userId != -1) {
                    customer = getCustomer(username);
                }

                if(customer != null) {
                    
                    forward = "/editcustomerorder";
                        
//                    is user admin? set admin value
                    if(isUserAdmin(customer.getCustomerId())) {
                        session.setAttribute("isAdmin", true);
                    } else {
                        session.setAttribute("isAdmin", false);
                    }

//                        set customer
                    session.setAttribute("customer", customer);
                }
            } else {
                isLoggedIn = false;
            }
        }
        
 //        admin edit ordered book
        else if(action.equalsIgnoreCase("/admin/editorderedbook")) {
            
            if(session.getAttribute("username") != null) {
                
                isLoggedIn = true;
                
//               Get User Info
                user = daoUser.getUserByUsername(username);
                if(user != null) {
                    userId = user.getUserId();
                }
                
//               Get Customer info
                if(userId != -1) {
                    customer = getCustomer(username);
                }

                if(customer != null) {
                    
                    forward = "/editorderedbook";
                        
//                    is user admin? set admin value
                    if(isUserAdmin(customer.getCustomerId())) {
                        session.setAttribute("isAdmin", true);
                    } else {
                        session.setAttribute("isAdmin", false);
                    }

//                        set customer
                    session.setAttribute("customer", customer);
                }
            } else {
                isLoggedIn = false;
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
                
//               administrative pages
                if(isLoggedIn) {
                    url = START_URL_ADMIN + forward + END_URL;
                } else {
                    url = START_URL + forward + END_URL;
                }
            }
            
            RequestDispatcher view = request.getRequestDispatcher(url);
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
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
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