package org.vladimir.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vladimir.model.User;
import org.vladimir.dao.UserDao;

/**
 *
 * @author Vladimir
 */
public class UserController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final String LIST_USER = "/listUser.jsp";
    private static final String INSERT_OR_EDIT = "/user.jsp";
    private UserDao dao;
    
    public UserController() {
    
        super();
        dao = new UserDao();
    
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String forward;
        String action = request.getParameter("action");
        
        if(action.equalsIgnoreCase("delete")) {
            
            int userId = Integer.parseInt(request.getParameter("userid"));
            dao.deleteUser(userId);
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
            
        } else if(action.equalsIgnoreCase("edit")) {
            
            forward = INSERT_OR_EDIT;
            int userId = Integer.parseInt(request.getParameter("userid"));
            User user = dao.getUserById(userId);
            request.setAttribute("user", user);
            
        } else if(action.equalsIgnoreCase("listUser")) {
            
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
        
        } else {
        
            forward = INSERT_OR_EDIT;
        
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        User user = new User();
        user.setFirstName(request.getParameter("firstname"));
        user.setLastName(request.getParameter("lastname"));
        user.setEmail(request.getParameter("email"));
        
        try {
        
            Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("dob"));
            user.setDob(dob);
            
        } catch (ParseException e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        }
        
        String userId = request.getParameter("userid");
        
        if(userId == null || userId.isEmpty())
            dao.addUser(user);
        else {
            
            user.setUserId(Integer.parseInt(userId));
            dao.updateUser(user);
            
        }
        
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("users", dao.getAllUsers());
        view.forward(request, response);
    }
}