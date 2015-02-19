package com.serendipity.controller;

import com.serendipity.dao.DAOShoppingCart;
import com.serendipity.model.ShoppingCart;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Vladimir
 */
@WebServlet(name="CartController",
            loadOnStartup = 1,
            urlPatterns = { "/cart/addToCart",
                            "/cart/cart",
                            "/cart/updateCart"})
public class CartController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view/cart";
    private static final String END_URL = ".jsp";
    HashMap map = new HashMap();
    
    public CartController() {
        super();
    }
    
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, JSONException {

        String forward;
        HttpSession session = request.getSession();
        String action = request.getServletPath();
        JSONObject json = new JSONObject();
        
        if(action.equalsIgnoreCase("/cart/cart")) {
        
//          TODO: cart
            
            if(session.getAttribute("username") == null) {
                forward = "/login/login";
            } else {
                forward = "/cart";
            }
        }

//      post - add books to cart request
        else if(action.equalsIgnoreCase("/cart/addtocart")) {
            
            
//            Parse json
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                reader.close();
            }
            
            parseJSON(sb.toString().replace(':', '=').replaceAll("[\\[\\]()\"]",""));
            
            int customerId = -1;
            int bookId = -1;
            int bookQty = -1;
            
            String cId = getJSONValue("customer_id");
            String bId = getJSONValue("book_id");
            String bQty = getJSONValue("book_qty");

            customerId = Integer.parseInt(cId);
            bookId = Integer.parseInt(bId);
            bookQty = Integer.parseInt(bQty);
            
            try {
                if(customerId != -1 && bookId != -1 && bookQty != -1) {
                    
                    
                    System.out.println("!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!");
                    
                    
                    System.out.println("customerId = " + customerId);
                    System.out.println("bookId = " + bookId);
                    System.out.println("bookQty = " + bookQty);
                    ShoppingCart shoppingCart = new ShoppingCart(0, bookId, bookQty, customerId);
                    DAOShoppingCart daoShoppingCart = new DAOShoppingCart();
                    
                    // must check item quantity and update item quantity
                    int http = daoShoppingCart.addShoppingCart(shoppingCart);
                    
                    if(http == 200) {
                        json.put("add", true);
                    } else {
                        json.put("add", true);
                    }
                    
                    System.out.println("!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!");
                    
                    
                } else {
                    json.put("add", false);
                }                 
            } catch (JSONException e) { 
                json.put("add", false);
            }
            
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
            return;
        }
        
//      post - update books in cart request
        else if(action.equalsIgnoreCase("/cart/updatecart")) {
        
//          TODO: implement update cart
            return;
        }
        
//     error page   
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
        } catch (JSONException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(SerendipityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parseJSON(String foo) {
        String foo2 = foo.substring(1, foo.length() - 1);  // hack off braces
        StringTokenizer st = new StringTokenizer(foo2, ",");
        while (st.hasMoreTokens()) {
          String thisToken = st.nextToken();
          StringTokenizer st2 = new StringTokenizer(thisToken, "=");

        map.put(st2.nextToken(), st2.nextToken());
  }
}

        String getJSONValue(String key) {
         return map.get(key).toString();
       }
}