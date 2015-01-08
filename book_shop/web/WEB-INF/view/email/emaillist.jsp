<%-- 
    Document   : emaillist
    Created on : Dec 30, 2014, 12:31:38 AM
    Author     : vladimir
--%>


<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/emaillist.css" />" />

<!-- include jquery core files -->
<script type="text/javascript" src="<c:url value="/assets/js/emaillist.js" />" ></script>

<p id="pageTitle">Email List</p>

<div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>

<div id="centerColumn">
    
<br><br>
<p>Join our email list.</p>
<p>If you do, we'll send you announcements about new releases and special offers.</p>
<br>

<div id="validate_form_success" class="alert alert-success"></div>
<div id="validate_form_error" class="alert alert-danger"></div>

</div>
<div id="nav-buttons">
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
        </li>
    </ul>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>