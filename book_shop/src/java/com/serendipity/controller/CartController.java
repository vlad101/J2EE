package com.serendipity.controller;

import com.serendipity.dao.DAOShoppingCart;
import com.serendipity.model.ShoppingCart;
import com.google.gson.Gson;
import com.serendipity.dao.DAOBook;
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
            Gson gson = new Gson();
            try {
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = request.getReader().readLine()) != null) {
                    sb.append(s);
                }

                ShoppingCart shoppingCart = (ShoppingCart) gson.fromJson(sb.toString(), ShoppingCart.class);
                
                if(shoppingCart != null) {
                    DAOBook daoBook = new DAOBook();
                    int bookQty = daoBook.getBookQtyByBookId(shoppingCart.getBookId());
                    int cartBookQty = shoppingCart.getQuantity();
                    if(bookQty != -1 && bookQty >= cartBookQty && cartBookQty > 0) { 
                        DAOShoppingCart daoShoppingCart = new DAOShoppingCart();
                        int http = daoShoppingCart.addShoppingCart(shoppingCart);
                        if(http == 200) {
                            daoBook = new DAOBook();
                            int qtyUpdate = daoBook.updateQuantityByBookId(shoppingCart.getBookId(), (bookQty - cartBookQty));
                            if(qtyUpdate == 200) {
                                json.put("add", true);
                            } else {
                                json.put("add", false);
                                json.put("error", "Book quantity update error");
                            }
                        } else {
                            json.put("add", false);
                            json.put("error", "Add shopping cart error");
                        }
                    } else {
                        json.put("add", false);
                        json.put("error", "Invalid book quantity error");
                    }
                } else {
                    json.put("add", false);
                    json.put("error", "Shopping cart error");
                }

            } catch (Exception ex) {
                json.put("add", false);
                json.put("error", "Shopping cart request error");
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