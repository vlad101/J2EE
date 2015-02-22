package com.serendipity.controller;

import com.serendipity.dao.DAOShoppingCart;
import com.serendipity.model.ShoppingCart;
import com.google.gson.Gson;
import com.serendipity.dao.DAOBook;
import com.serendipity.dao.DAOCustomer;
import com.serendipity.dao.DAOImage;
import com.serendipity.dao.DAOUser;
import com.serendipity.model.Book;
import com.serendipity.model.Customer;
import com.serendipity.model.Image;
import com.serendipity.model.User;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class ShoppingCartController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String START_URL = "/WEB-INF/view/cart";
    private static final String END_URL = ".jsp";
    HashMap map = new HashMap();
    
    public ShoppingCartController() {
        super();
    }
    
    @SuppressWarnings({"UseSpecificCatch", "empty-statement"})
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
            
                try {

    //                Get user/customer info
                    User user;
                    if(session.getAttribute("username") != null) {
                        String userName = (String)session.getAttribute("username");
                        user = getUserByUsername(userName);
                    } else {
                        user = null;
                    }


    //                Get shopping cart list by customer ID
                    if(user != null) {
                        List<ShoppingCart> shoppingCartList = getShoppingCartLisByCustomerId(user.getUserId());
                        request.setAttribute("shoppingCartList", shoppingCartList);
                        request.setAttribute("shoppingCartListCount", shoppingCartList.size());
                        session.setAttribute("username", user.getUsername());

//                        Get book details
                        if(!shoppingCartList.isEmpty()) {
                            List<Book> bookList = getBookList(shoppingCartList);
                            request.setAttribute("bookList", bookList);
                            request.setAttribute("defaultImageMap", getDefaultImageMap(bookList));
                            request.setAttribute("shoppingCartBookQtyMap", getShoppingCartBookQtyMap(bookList, user.getUserId()));
                        }
                        
    //                  Get customer info
                        DAOCustomer daoCustomer = new DAOCustomer();
                        Customer customer = daoCustomer.getCustomerById(user.getUserId());
                        if(customer != null) {
                            session.setAttribute("customer", customer);
                        } else {
                            forward = "/login/login";
                        }
                    } else {
                        forward = "/login/login";
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
                } 
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
                        
                        
                        int httpAddShoppingCart = -1;
                        
//                        book already exists, update book quantity
                        DAOShoppingCart daoShoppingCart = new DAOShoppingCart();
                        ShoppingCart shoppingCartEntry = daoShoppingCart.getShoppingCartByBookId(shoppingCart.getCustomerId(), shoppingCart.getBookId());
                        if(shoppingCartEntry != null) {
                            int sCartId = shoppingCartEntry.getShoppingCartId();
                            int sCartBookId = shoppingCartEntry.getBookId();
                            int sCartBookQty = shoppingCartEntry.getQuantity() + shoppingCart.getQuantity();
                            int sCartCustId = shoppingCartEntry.getCustomerId();
                            ShoppingCart shoppingCartEntryExist = new ShoppingCart(sCartId, sCartBookId, sCartBookQty, sCartCustId);
                            daoShoppingCart = new DAOShoppingCart();
                            httpAddShoppingCart = daoShoppingCart.updateShoppingCartInfo(shoppingCartEntryExist);
                        } else {
//                            Add unique book
                            daoShoppingCart = new DAOShoppingCart();
                            httpAddShoppingCart = daoShoppingCart.addShoppingCart(shoppingCart);
                        }
                        
                        if(httpAddShoppingCart == 200) {
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
            } else if(forward.equals("/login/login")) {
                url = "/WEB-INF/view" + forward + END_URL;;
            } else {
//                user pages
                url = START_URL + forward + END_URL;
            }
            
            RequestDispatcher view = request.getRequestDispatcher(url);
            view.forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ShoppingCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<ShoppingCart> getShoppingCartLisByCustomerId(int customerId) {
        DAOShoppingCart daoShoppingCart = new DAOShoppingCart();
        List shoppingCartList = daoShoppingCart.getShoppingCartByCustomerId(customerId);
        return shoppingCartList;
    }
    
    private User getUserByUsername(String userName) {
        
        User user;
        DAOUser daoUser = new DAOUser();
        user = daoUser.getUserByUsername(userName);

        if(user != null) { 
            return user;
        }
        return null;
    }
    
    /**
     * Get default image maps with key book id and value default image map
     * If image contains a default image, the actual image path will be used
     * If image does not contain a default image, the default "no_image.jpg" will be used
     * 
     * @param bookList
     * @return 
     */
    private Map<Integer, String> getDefaultImageMap(List<Book> bookList) {
        Map<Integer, String> defaultImageMap = new HashMap<Integer, String>();
        for(Book book : bookList) {
            int bookId = book.getBookId();
            DAOImage daoImage = new DAOImage();
            Image image = daoImage.getDefaultImageByBookId(bookId);

            if(image != null) {
                String defaultImagePath = image.getPath();
                defaultImageMap.put(bookId, defaultImagePath);
            } else {
                defaultImageMap.put(bookId, "no_image.jpg");
            }
        }
        return defaultImageMap;
    }
    
    /**
     * Get default image maps with key book id and value default image map
     * If image contains a default image, the actual image path will be used
     * If image does not contain a default image, the default "no_image.jpg" will be used
     * 
     * @param bookList
     * @return 
     */
    private Map<Integer, Integer> getShoppingCartBookQtyMap(List<Book> bookList, int userId) {
        Map<Integer, Integer> shoppingCartBookQty = new HashMap<Integer, Integer>();
        for(Book book : bookList) {
            int bookId = book.getBookId();
            DAOShoppingCart daoShoppingCart = new DAOShoppingCart();
            ShoppingCart shoppingCart = daoShoppingCart.getShoppingCartByBookId(userId, bookId);
            if(shoppingCart != null) {
                shoppingCartBookQty.put(bookId, shoppingCart.getQuantity());
            }
        }
        return shoppingCartBookQty;
    }
    
    private List<Book> getBookList(List<ShoppingCart> shoppingCartList) {
        List<Book> bookList = new LinkedList<Book>();
        for(ShoppingCart shoppingCart : shoppingCartList) {
            int bookId = shoppingCart.getBookId();
            DAOBook daoBook = new DAOBook();
            Book book = daoBook.getBookById(bookId);
            if(book != null && !bookList.contains(book)) {
                bookList.add(book);
            }
        }
        return bookList;
    }
}