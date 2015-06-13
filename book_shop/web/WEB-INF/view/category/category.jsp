<%-- 
    Document   : category
    Created on : May 31, 2014, 8:33:31 PM
    Author     : Vladimir
--%>

<!-- custom edit category page css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/category.css" />" />

<!-- custom edit category page js -->
<script type="text/javascript" src="<c:url value="/assets/js/category.js" />" ></script>

<div id="categoryCenterColumn">
    
    <p id="pageTitle">${category.getCategoryName()}</p>
    
    <div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>

    <div id="ajax_add_book_response_error" class="alert alert-danger"></div>
    <div id="ajax_add_book_response_success" class="alert alert-success"></div>
    
    <br>
    <c:choose>
        <c:when test="${not empty bookList}">
            <table id="bookTable">
                <tr class="header">
                    <th>Image</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Price</th>
                    <th>Details</th>
                    <th>Quantity</th>
                    <th>Buy</th>
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
                            ${book.getTitle()}
                        </p>
                    </td>
                    <td>
                        <p>
                            ${book.getAuthor()}
                        </p>
                    </td>
                    <td>
                        <p>
                            ${book.getPrice()}
                        </p>
                    </td>
                    <td>
                        <p>
                            <a href="<c:url value="/book/book?id=${book.getBookId()}" />">Book Details</a>
                        </p>
                    </td>
                    <td>
                        <p>
                    <c:if test="${book.getQuantity() == 0}">
                        <input type="text" id="book_id-${book.getBookId()}-qty-${book.getQuantity()}" class="book-quantity" value="0" disabled="disabled" >
                    </c:if>
                    <c:if test="${book.getQuantity() >= 1}">
                        <input type="text" id="book_id-${book.getBookId()}-qty-${book.getQuantity()}" class="book-quantity" value="1" >
                    </c:if>
                            <span id="book_id-${book.getBookId()}-invalid-qty" ></span>
                        </p>
                    </td>
                    <td>
                        <p>
                    <c:if test="${book.getQuantity() == 0}">
                        <button type="button" id="add_book_${book.getBookId()}_to_cart_button" class="btn btn-disabled" disabled >Out of Stock</button>
                    </c:if>
                    <c:if test="${book.getQuantity() >= 1}">
                        <button type="button" id="add_book_${book.getBookId()}_to_cart_button" class="btn btn-primary btn-small" >Add to cart</button>
                    </c:if>
                        </p>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <br><p><b>OOPS! No books in the '${category.getCategoryName()}' category!</b></p>
        </c:otherwise>
    </c:choose>
            
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/category/categorylist" />'" >Category List</button>
            </li>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/book/booklist" />'" >Book List</button>
            </li>
        </ul>
    </div>
</div>