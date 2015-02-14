<%-- 
    Document   : login
    Created on : Dec 27, 2014, 4:14:18 PM
    Author     : vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/login.css" />" />

<!-- include jquery core files -->
<script type="text/javascript" src="<c:url value="/assets/js/login.js" />" ></script>

<p id="pageTitle">User Login</p>

<br><br>

<div id="adminCenterColumn">
    <p>Please enter your username and password to continue.</p><br>
    <c:if test="${not empty error}">
        <span id="login-error-message">${error}</span><br><br>
    </c:if>
        <form action="/book_shop/login/userlogin" method="post">

            Username: <input type="text" name="username"><br><br>
            Password: <input type="password" name="password"><br><br>
            <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/><br>
            <input type="submit" value="Login">
        </form>
    <br>

    <p>Not Registered? <a href="<c:url value='/customer/register'/>" >Register</a></p>
    
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>