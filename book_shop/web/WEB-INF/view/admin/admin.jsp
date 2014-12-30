<%-- 
    Document   : admin
    Created on : Jun 5, 2014, 7:23:13 PM
    Author     : Vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/admin.css" />" />

<!-- include jquery core files -->
<script type="text/javascript" src="<c:url value="/assets/js/admin.js" />" ></script>

<p id="pageTitle">Administration</p>

<div id="adminCenterColumn">
    
    <br>  
    <div class="button-list">
        <button type="button" class="btn btn-primary btn-small" id="edit-category-button" >Edit category</button><br><br>
        <button type="button" class="btn btn-primary btn-small" id="edit-book-button" >Edit Book</button><br><br>
        <button type="button" class="btn btn-primary btn-small" id="edit-customer-button" >Edit Customer</button><br><br>
        <button type="button" class="btn btn-primary btn-small" id="edit-customer-order-button" >Edit Customer Order</button>
    </div>
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >User Home</button><br>
            </li>
        </ul>
    </div>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>