<%-- 
    Document   : user
    Created on : May 26, 2014, 4:43:48 PM
    Author     : Vladimir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" href="assets/jquery/css/ui-lightness/jquery-ui-1.10.4.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="assets/jquery/js/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="assets/jquery/js/jquery-ui-1.10.4.custom.min.js"></script>
        <title>Edit User</title>
    </head>
    <body>
        <script>  

            $(function() {  
                $('input[name=dob]').datepicker();  
            });  

        </script>  
        
        <form method="POST" action='UserController' name="frmAddUser">  
            <input type="hidden" readonly="readonly" name="userid" value="<c:out value="${user.userId}" />" />
            First Name <br> <input type="text" name="firstname" value="<c:out value="${user.firstName}" />" /><br><br>
            Last Name <br> <input type="text" name="lastname" value="<c:out value="${user.lastName}" />" /><br><br>
            DOB <br> <input type="text" name="dob" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${user.dob}" />" /><br><br>
            Email <br> <input type="text" name="email" value="<c:out value="${user.email}" />" /><br><br>
            <input type="submit" value="Submit" />  
        </form>  
    </body>
</html>
