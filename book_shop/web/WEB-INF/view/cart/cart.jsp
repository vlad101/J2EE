<%-- 
    Document   : cart
    Created on : May 31, 2014, 8:33:14 PM
    Author     : Vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/cart.css" />" />

<div id="centerColumn">

    <c:choose>
        <c:when test="${not empty bookList}">
            
            <p>Your shopping cart contains ${shoppingCartListCount} items.</p>
            
            <div id="actionBar">
                <a href="#" class="bubble hMargin">clear cart</a>
                <a href="#" class="bubble hMargin">continue shopping</a>
                <a href="#" class="bubble hMargin">proceed to checkout</a>
            </div>

            <h4 id="subtotal">[ subtotal: xxx ]</h4>
            
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
                                <input type="text" id="book_id-${book.getBookId()}-qty-${book.getQuantity()}" class="book-quantity" value="${shoppingCartBookQtyMap[book.getBookId()]}" >
                                <span id="book_id-${book.getBookId()}-invalid-qty" ></span>
                            </p>
                        </td>
                        <td>
                            <p>
                                <button type="button" id="add_book_${book.getBookId()}_to_cart_button" class="btn btn-primary btn-small" >Update</button>
                            </p>
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