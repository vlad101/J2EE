<%-- 
    Document   : cart
    Created on : May 31, 2014, 8:33:14 PM
    Author     : Vladimir
--%>

<!-- custom edit cart page js -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/cart.css" />" />

<!-- custom edit cart page js -->
<script type="text/javascript" src="<c:url value="/assets/js/cart.js" />" ></script>

<div id="centerColumn">

    <c:choose>
        <c:when test="${not empty bookList}">
            
            <p>Your shopping cart contains ${shoppingCartListCount} items.</p>
            
            <div id="actionBar">
                <!-- class="bubble hMargin"  -->
                <button type="button" id="clear_cart_customer_id_${sessionScope.customer.getCustomerId()}_button" class="btn btn-danger btn-small" >Clear Cart</button>
                <button type="button" id="continue_shopping_button" class="btn btn-primary btn-small" >Continue Shopping</button>
                <button type="button" id="checkout_button" class="btn btn-success btn-small" >Proceed to Checkout</button>
            </div>

            <h4 id="subtotal"><b>Subtotal: </b>$${subtotal}</h4>
            
            <table id="cartTable">
                <tr class="header">
                    <th>Image</th>
                    <th>Title</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Update</th>
                </tr>
                <c:forEach var="book" items="${bookList}" varStatus="loopStatus" >
                    <tr class="${loopStatus.index % 2 == 0 ? 'lightBlue' : 'white'}">
                        <td>
                            <p>
                                <img src="<c:url value="/assets/images/book/${defaultImageMap[book.getBookId()]}" />" height="70" width="50" alt="book image">
                            </p>
                        </td>
                        <td>
                            <p>
                                <a href="<c:url value="/book/book?id=${book.getBookId()}" />">${book.getTitle()}</a>
                            </p>
                        </td>
                        <td>
                            <p>
                                ${book.getPrice()}
                            </p>
                        </td>
                        <td>
                            <p>
                                <input type="text" id="book_id-${book.getBookId()}-qty-${shoppingCartBookQtyMap[book.getBookId()]}" class="book-quantity" value="${shoppingCartBookQtyMap[book.getBookId()]}" >
<!--                                <span id="book_id-${book.getBookId()}-invalid-qty" ></span>-->
                            </p>
                        </td>
                        <td>
                            <br>
                            <p>
                                <button type="button" id="update_cart_${book.getBookId()}_button" class="btn btn-primary btn-small" >Update</button>
                            </p>
                            <p>
                                <button type="button" id="delete_cart_${book.getBookId()}_button" class="btn btn-danger btn-small" >Delete</button>
                            </p>
                            <br>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <br><p><b>OOPS! Empty category cart!</b><br><br></p>
        </c:otherwise>
    </c:choose>                
</div>