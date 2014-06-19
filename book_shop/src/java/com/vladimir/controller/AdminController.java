/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private static final String END_URL = ".jsp";
    //private UserDao dao;
    
    public AdminController() {
    
        super();
        //dao = new UserDao();
    
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward = "";
        String action = request.getServletPath();
        
        if(action.equalsIgnoreCase("/admin")) {
            
//          TODO: implement admin page
            
            
            forward = "/admin";
        }
        
//        admin edit category
        else if(action.equalsIgnoreCase("/admin/editcategory")) {
            
//          TODO: implement edit category page
            
            
            forward = "/editcategory";
        }
        
 //        admin edit book
        else if(action.equalsIgnoreCase("/admin/editbook")) {
            
//          TODO: implement edit book page
            
            
            forward = "/editbook";
        }       
        
 //        admin edit customer
        else if(action.equalsIgnoreCase("/admin/editcustomer")) {
            
//          TODO: implement edit customer page
            
            
            forward = "/editcustomer";
        }
        
 //        admin edit customer order
        else if(action.equalsIgnoreCase("/admin/editcustomerorder")) {
            
//          TODO: implement edit customer order page
            
            
            forward = "/editcustomerorder";
        }
        
 //        admin edit ordered book
        else if(action.equalsIgnoreCase("/admin/editorderedbook")) {
            
//          TODO: implement edit ordered book page
            
            
            forward = "/editorderedbook";
        }
        
//        Error page
        else {
            forward = "/error";
        }
        
//      create view and forward request
        try {
            
            String url;
            
//                error page
            
            if( action.contains("error") ) {
                
                url = forward + END_URL;
                
            } else {
                
//               administrative pages
                url = START_URL_ADMIN + forward + END_URL;
                    
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
}