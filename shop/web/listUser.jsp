<%-- 
    Document   : listUser
    Created on : May 26, 2014, 4:13:59 PM
    Author     : Vladimir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show All Users</title>
    </head>
    <body>
        <table border=1>
            <thead>
                <th>User Id</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>DOB</th>
                <th colspan=2>Action</th>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.userId}" /></td>
                        <td><c:out value="${user.firstName}" /></td>
                        <td><c:out value="${user.lastName}" /></td>
                        <td><c:out value="${user.email}" /></td>
                        <td><fmt:formatDate pattern="yyyy-MMM-dd" value="${user.dob}" /></td>
                        <td><a href="UserController?action=edit&userid=<c:out value="${user.userId}" />">Update</a></td>
                        <td><a href="UserController?action=delete&userid=<c:out value="${user.userId}" />">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <p><a href="UserController?action=insert">Add User</p>
        
    </body>
</html>