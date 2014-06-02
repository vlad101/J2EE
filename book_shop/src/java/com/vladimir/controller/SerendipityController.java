/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vladimir.controller;

import java.io.IOException;
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
            urlPatterns = {"/category",
                           "/addToCart",
                           "/viewCart",
                           "/updateCart",
                           "/checkout",
                           "/purchase",
                           "/chooseLanguage"})
public class SerendipityController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view";
    private static final String END_URL = ".jsp";
    //private UserDao dao;
    
    public SerendipityController() {
    
        super();
        //dao = new UserDao();
    
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String forward = "";
        String action = request.getServletPath();
        
//      category page request
        if(action.equals("/category")) {
            
//          TODO: category
            forward = "/category";
            
        }
        
//      cart page request
        else if(action.equals("/viewCart")) {
        
//          TODO: cart
            forward = "/cart";
        
        }
        
//      checkout page request
        else if(action.equals("/checkout")) {
        
//          TODO: checkout
            forward = "/checkout";
            
        }
        
//      switch language request
        else if(action.equals("chooseLanguage")) {
        
//          TODO: switch language
            
        
        }
        
//      create view and forward request
        try {
            
            String url = START_URL + forward + END_URL;
            RequestDispatcher view = request.getRequestDispatcher(url);
            view.forward(request, response);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String forward = "";
        String action = request.getServletPath();
        
//      add books to cart request
        if(action.equals("/addToCart")) {
        
//          TODO: implement add to cart
        
        }
        
//      update books in cart request
        else if(action.equals("/updateCart")) {
        
//          TODO: implement update cart
        
        }
        
//      purchase request
        else if(action.equals("/purchase")) {
        
//          TODO: implement purchase
            forward = "/confirmation";
        
        }
        
//      create view and forward request
        try {
            
            String url = START_URL + forward + END_URL;
            RequestDispatcher view = request.getRequestDispatcher(url);
            view.forward(request, response);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}