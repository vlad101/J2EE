<%-- 
    Document   : admin
    Created on : Jun 5, 2014, 7:23:13 PM
    Author     : Vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/admin.css" />" />

<script type="text/javascript" src="<c:url value="/assets/js/admin.js" />" ></script>

<div id="adminCenterColumn">
    
        <br>
        <h1>Hello Administrator</h1>
        
    <div class="button-list">
        <button type="button" class="btn btn-success btn-lg" id="edit-category-button" >Edit category!</button>
        <br>
        <br>
        <button type="button" class="btn btn-success btn-lg" id="edit-book-button" >Edit Book!</button>
        <br>
        <br>
        <button type="button" class="btn btn-success btn-lg" id="edit-customer-button" >Edit Customer!</button>
        <br>
        <br>
        <button type="button" class="btn btn-success btn-lg" id="edit-customer-order-button" >Edit Customer Order!</button>
        <br>
    </div>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>