package com.serendipity.controller;

import com.serendipity.dao.DAOCustomer;
import com.serendipity.dao.DAOUser;
import com.serendipity.model.Customer;
import com.serendipity.model.User;
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
@WebServlet(name="CustomerController",
            loadOnStartup = 1,
            urlPatterns = { "/customer/customer",
                            "/customer/register"})
public class CustomerController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL_COOKIE = "/WEB-INF/view/customer";
    private static final String END_URL = ".jsp";
    
    public CustomerController() {
        super();
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward;
        String action = request.getServletPath();
        
//        get - register page request
        if(action.equalsIgnoreCase("/customer/register")) {
            forward = "/register";
        }
        
//        get - customer page request
        else if(action.equalsIgnoreCase("/customer/customer")) {
            if(request.getParameterMap().containsKey("id")) {
                String customerId = request.getParameter("id");
                
                int validCustomerId;
                try
                {
                    validCustomerId = Integer.parseInt(customerId);
                    User user = getUserByCustomerId(validCustomerId);
                    if(user != null) {
                        request.setAttribute("user", user);
                        Customer customer = getCustomerByCustomerId(validCustomerId);
                        if(customer  != null) {
                            request.setAttribute("customer", customer);
                            forward = "/customer";
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
        
//        error page
        else {
            forward = "/error/error_404";
        }
        
//      create view and forward request
        try {
            
            String url = START_URL_COOKIE + forward + END_URL;
            RequestDispatcher view = request.getRequestDispatcher(url);
            view.forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private User getUserByCustomerId(int customerId) {
        DAOUser daoUser = new DAOUser();
        User user = daoUser.getUserById(customerId);
        if(user != null) {
            return user;
        }
        return null;
    }
    
    private Customer getCustomerByCustomerId(int customerId) {
        DAOCustomer daoCustomer = new DAOCustomer();
        Customer customer = daoCustomer.getCustomerById(customerId);
        if(customer != null) {
            return customer;
        }
        return null;
    }
}