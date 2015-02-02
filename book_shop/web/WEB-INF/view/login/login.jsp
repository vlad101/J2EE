<%-- 
    Document   : login
    Created on : Dec 27, 2014, 4:14:18 PM
    Author     : vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/admin.css" />" />

<!-- include jquery core files -->
<script type="text/javascript" src="<c:url value="/assets/js/admin.js" />" ></script>

<p id="pageTitle">User Login</p>

<br><br>

<div id="adminCenterColumn">
    <p>Please enter your username and password to continue.</p>
        <form action="/book_shop/login/userlogin" method="post">

            Username: <input type="text" name="username"><br>
            Password: <input type="password" name="password"><br>
            <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
            <input type="submit" value="Login">
        </form>
    <br><br>
    <c:if test="${not empty error}">
        <p>${error}</p><br>
    </c:if>
    <p>Not Registered? <a href="<c:url value='/customer/register'/>" >Register</a></p>
    
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>