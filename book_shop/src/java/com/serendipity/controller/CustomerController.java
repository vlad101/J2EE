package com.serendipity.controller;

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
            urlPatterns = {"/customer"})
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
        if(action.equalsIgnoreCase("/customer/register")) {
            forward = "/register";
        } else {
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
}