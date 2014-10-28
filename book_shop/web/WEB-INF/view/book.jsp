<%-- 
    Document   : book
    Created on : Oct 19, 2014, 7:25:50 PM
    Author     : Vladimir
--%>

<!-- custom edit book css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/editbook.css" />" />

<!-- jQuery plugin -->
<script type="text/javascript" src="<c:url value="/assets/jquery/js/jquery-1.10.2.js" />" ></script>

<!-- custom edit book page js -->
<script type="text/javascript" src="<c:url value="/assets/js/polyfill.js" />" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/book.js" />" ></script>

<div id="centerColumn">
                
    <br />
    <h1>Book Details!</h1>
    <div id="book_id"><%= request.getAttribute("id") %></div>
    
    <!--Redirect to the book error-->
    <div id="ajax_book_response_error" class="alert alert-danger"></div>
    <br />
    
    <!-- jQuery datatables -->
    <div id="book-list">
        <table id="book-list-table" class="display">
            <thead>
                <tr>
                    <th>Expand</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Quantity</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Description</th>
                    <th>Last Update</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
                <tr>
                    <th>Expand</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Quantity</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Description</th>
                    <th>Last Update</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </tfoot>
        </table>
    </div>
</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Bootbox alerts plugin plugin -->
<script src="<c:url value="/assets/plugins/bootbox/bootbox.min.js" />"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<link href="<c:url value="/assets/bootstrap/css/bootstrap.min.css" />">