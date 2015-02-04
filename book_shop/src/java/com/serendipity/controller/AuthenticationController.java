package com.serendipity.controller;

import com.serendipity.dao.DAOCategory;
import com.serendipity.model.Category;
import com.serendipity.util.CookieUtil;
import java.io.IOException;
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
            urlPatterns = {"/login",
                           "/logout",
                           "/login/userlogin"})
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

        String forward = "";
        String action = request.getServletPath();
        
        if(action.equalsIgnoreCase("/login")) {
            
//          TODO: implement admin page
            forward = "/login/login";
        }
        
        else if(action.equalsIgnoreCase("/logout")) {
            response.setContentType("text/html");
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    System.out.println("JSESSIONID="+cookie.getValue());
                }
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            }
            //invalidate the session if exists
            HttpSession session = request.getSession(false);
            if(session != null){
                session.invalidate();
            }
            //no encoding because we have invalidated the session
            forward = "/login/login";
        }
        
//   create authentication function to validate user
        else if(action.equalsIgnoreCase("/login/userlogin")) {
            
            // get request parameters for userID and password
            String username = request.getParameter("username");
                       
            //setting session to expiry in 30 mins
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);
            
            //allow access only if session exists
            if(username.equals("admin")) {
                session = request.getSession();
                session.setAttribute("username", username);
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30*60);
                Cookie userName = new Cookie("username", username);
                response.addCookie(userName);
                request.setAttribute("categoryList", getCategoryList());
                
                
//                String user = (String) session.getAttribute("user");
                CookieUtil cookieUtil = new CookieUtil();
                Cookie[] cookies = request.getCookies();
                if(cookies !=null){
                    
                    @SuppressWarnings("static-access")
                    String userNameCookie = cookieUtil.getCookieValue(cookies, "user");
                    request.setAttribute("username", userNameCookie);
                    
                    @SuppressWarnings("static-access")
                    String sessionIdCookie = cookieUtil.getCookieValue(cookies, "JSESSIONID");
                    request.setAttribute("sessionID", sessionIdCookie);
                    
                } else {
                    request.setAttribute("sessionID", session.getId());
                }
                
                forward = "/index";
                
            } else {
                String error = "Either user name or password is wrong.";
                request.setAttribute("error", error);
                forward = "/login/login";
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
}