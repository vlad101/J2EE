package com.serendipity.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vladimir
 */
@WebServlet(name="CookieController",
            loadOnStartup = 1,
            urlPatterns = {"/cookie"})
public class CookieController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL_COOKIE = "/WEB-INF/view/cookie";
    private static final String END_URL = ".jsp";
    
    public CookieController() {
    
        super();
    
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward;
        String action = request.getServletPath();
        
        if(action.equalsIgnoreCase("/cookie/deleteCookies")) {
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies) {
                cookie.setMaxAge(0); // delete the cookies
                cookie.setPath("/"); // allow the entire application to access it
                response.addCookie(cookie);
            }
            forward = "/delete_cookies";
        } else {
            forward = "/error/error_404";
        }
        
//      create view and forward request
        try {
            
            String url = START_URL_COOKIE + forward + END_URL;
            RequestDispatcher view = request.getRequestDispatcher(url);
            view.forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(CookieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CookieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CookieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}