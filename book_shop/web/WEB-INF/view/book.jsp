<%-- 
    Document   : book
    Created on : Oct 19, 2014, 7:25:50 PM
    Author     : Vladimir
--%>

<!-- custom edit book css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/book.css" />" />

<!-- jQuery plugin -->
<script type="text/javascript" src="<c:url value="/assets/jquery/js/jquery-1.10.2.js" />" ></script>

<!-- custom edit book page js -->
<script type="text/javascript" src="<c:url value="/assets/js/book.js" />" ></script>

<div id="centerColumn">
                
    <br />
    <h1 id="page_title"></h1>
    <div id="book_id"><%= request.getAttribute("id") %></div>
    
    <!--Redirect to the book error-->
    <div id="ajax_book_response_error" class="alert alert-danger"></div>
    <br />
</div>

    <!-- Get book details -->
<div id="leftColumn">
    <div id="book_title" ></div>
    <div id="book_author"></div>
    <div id="book_quantity"></div>
    <div id="book_category"></div>
    <div id="book_price"></div>
    <div id="book_description"></div>
    <div id="book_last_update"></div>
    <div id="book_image"></div>
</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Bootbox alerts plugin plugin -->
<script src="<c:url value="/assets/plugins/bootbox/bootbox.min.js" />"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<link href="<c:url value="/assets/bootstrap/css/bootstrap.min.css" />">

<!--FancyBox Plugin css-->
<link rel="stylesheet" href="/book_shop/assets/plugins/fancybox/source/jquery.fancybox.css" type="text/css" media="screen" />

<!--FancyBox Plugin js-->
<script type="text/javascript" src="/book_shop/assets/plugins/fancybox/source/jquery.fancybox.pack.js"></script>