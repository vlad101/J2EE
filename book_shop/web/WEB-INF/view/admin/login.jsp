<%-- 
    Document   : login
    Created on : Dec 27, 2014, 4:14:18 PM
    Author     : vladimir
--%>

<%-- 
    Document   : admin
    Created on : Jun 5, 2014, 7:23:13 PM
    Author     : Vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/admin.css" />" />

<!-- include jquery core files -->
<script type="text/javascript" src="<c:url value="/assets/js/admin.js" />" ></script>

<div id="adminCenterColumn">
    <h1>Admin Login Form</h1>
    <p>Please enter your username and password to continue.</p>
    <form action="security_check" method="get">
        <label class="pad_top">Username</label>
        <input type="text" name="username"><br>
        <label class="pad_top">Password</label>
        <input type="password" name="password"><br>
        <label>&nbsp;</label>
        <input type="submit" value="Login" class="margin_left"><br>
    </form>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>