package com.serendipity.controller;

import com.serendipity.dao.DAOCategory;
import com.serendipity.model.Category;
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

/**
 *
 * @author Vladimir
 */
@WebServlet(name="EmailListController",
            loadOnStartup = 1,
            urlPatterns = { "/email/emaillist"})
public class EmailListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view/email";
    private static final String END_URL = ".jsp";
    
    public EmailListController() {
        super();
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String forward = "";
        String action = request.getServletPath();
        
//      get - index page request
        if(action.equalsIgnoreCase("/email/emaillist")) {
//            TO DO
            System.out.println("!!!!!!!!!!!!!!!!!!!");
            System.out.println("Email list hello!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!");
            forward = "/emaillist";
        }
//        error page
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
}