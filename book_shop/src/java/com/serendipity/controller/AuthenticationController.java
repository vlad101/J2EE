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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vladimir
 */
@WebServlet(name="AuthenticationController",
            loadOnStartup = 1,
            urlPatterns = {"/login"})
public class AuthenticationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL_ADMIN = "/WEB-INF/view";
    private static final String END_URL = ".jsp";
    
    public AuthenticationController() {
    
        super();
    
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward;
        String action = request.getServletPath();
        
        if(action.equalsIgnoreCase("/login")) {
            
//          TODO: implement admin page
            forward = "/login/login";
        }
        
        else if(action.equalsIgnoreCase("/login/userlogin")) {
            
            // get request parameters for userID and password
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            //setting session to expiry in 30 mins
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);
            
            if(username.equals("admin")) {
                request.setAttribute("username", username);
                request.setAttribute("password", password);
            }
            
            
//            if(userID.equals(user) && password.equals(pwd)){
//                HttpSession session = request.getSession();
//                session.setAttribute("user", "Pankaj");
//                //setting session to expiry in 30 mins
//                session.setMaxInactiveInterval(30*60);
//                Cookie userName = new Cookie("user", user);
//                response.addCookie(userName);
//                //Get the encoded URL string
//                String encodedURL = response.encodeRedirectURL("LoginSuccess.jsp");
//                response.sendRedirect(encodedURL);
//            }else{
//                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
//                PrintWriter out= response.getWriter();
//                out.println("<font color=red>Either user name or password is wrong.</font>");
//                rd.include(request, response);
//            }
            
            forward = "/admin/admin";
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