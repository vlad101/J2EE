<%-- 
    Document   : editcategory
    Created on : Jun 6, 2014, 8:56:43 PM
    Author     : Vladimir
--%>

<%@ page errorPage="/error.jsp" %>

<script type="text/javascript" src="/book_shop/assets/jquery/js/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/polyfill.js" />" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/editcategory.js" />" ></script>

<div id="centerColumn">
                
    <br />
    <br />
    <h1>Edit Category</h1>
    <br />
    <br />
    
    <form id="post_category_form" name="post_category_form" action="#">
        Category title  <input type="text" name="category_title" id="category_title" maxlength="50" value="">&nbsp;
                        <input type="button" name="submit_category" id="submit_category" value="Submit">
    </form>
    
    <br />
    <div id="ajax_response"></div>
                
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>