<%-- 
    Document   : response
    Created on : Apr 18, 2014, 3:15:28 PM
    Author     : Vladimir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:useBean id="mybean" scope="session" class="org.mypackage.hello.NameHandler" />
        <jsp:setProperty name="mybean" property="firstName" />
        <jsp:setProperty name="mybean" property="lastName" />
        <h1>Hello, <jsp:getProperty name="mybean" property="firstName" /> <jsp:getProperty name="mybean" property="lastName" />!</h1>
    </body>
</html>
