<%-- 
    Document   : book
    Created on : Oct 19, 2014, 7:25:50 PM
    Author     : Vladimir
--%>

    <!-- custom edit book css -->
    <link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/book.css" />" />

    <!-- custom edit book page js -->
    <script type="text/javascript" src="<c:url value="/assets/js/book.js" />" ></script>

    <p id="pageTitle">Book Details</p>

    <div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>    
    
    <div id="centerColumn">

        <br />
        <h1 id="page_title"></h1>
        <div id="book_id">${book.getBookId()}</div>

        <!--Redirect to the book error-->
        <div id="ajax_book_response_error" class="alert alert-danger"></div>
        <div id="ajax_add_book_response_error" class="alert alert-danger"></div>
        <div id="ajax_add_book_response_success" class="alert alert-success"></div>
        <br />
    </div>

        <!-- Get book details -->
    <div id="leftColumn">
        <p>
        <c:if test="${book.getQuantity() == 0}">
            <input type="text" id="book_id-${book.getBookId()}-qty-${book.getQuantity()}" class="book-quantity" value="0" disabled="disabled" >&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" id="add_book_${book.getBookId()}_to_cart_button" class="btn btn-disabled" disabled >Out of Stock</button>
        </c:if>
        <c:if test="${book.getQuantity() >= 1}">
            <input type="text" id="book_id-${book.getBookId()}-qty-${book.getQuantity()}" class="book-quantity" value="1" >&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" id="add_book_${book.getBookId()}_to_cart_button" class="btn btn-primary btn-small" >Add to cart</button>
        </c:if>
        <span id="invalid-qty" ></span>
        </p>
        <br><br>
        <div id="book_title" >
            <p>${book.getTitle()}</p>
        </div>
        <div id="book_author"></div>
        <div id="book_quantity"></div>
        <div id="book_category"></div>
        <div id="book_price"></div>
        <div id="book_description"></div>
        <div id="book_last_update"></div>
        <div id="book_image"></div>
    </div>
    
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/category/category?id=${book.getCategoryId()}" />'" >'${category.getCategoryName()}' Books</button>
            </li>    
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/category/categorylist" />'" >Category List</button>
            </li>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/book/booklist" />'" >Book List</button>
            </li>
        </ul>
    </div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Bootbox alerts plugin plugin -->
<script src="<c:url value="/assets/plugins/bootbox/bootbox.min.js" />"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<link href="<c:url value="/assets/bootstrap/css/bootstrap.min.css" />">

<!--FancyBox Plugin css-->
<link rel="stylesheet" href="<c:url value="/assets/plugins/fancybox/source/jquery.fancybox.css" />" type="text/css" media="screen" />

<!--FancyBox Plugin js-->
<script type="text/javascript" src="<c:url value="/assets/plugins/fancybox/source/jquery.fancybox.pack.js" />"></script>