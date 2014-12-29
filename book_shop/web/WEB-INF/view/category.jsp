<%-- 
    Document   : category
    Created on : May 31, 2014, 8:33:31 PM
    Author     : Vladimir
--%>

<%@page import="com.vladimir.model.Image"%>
<%@page import="com.vladimir.dao.DAOImage"%>
<%@page import="com.vladimir.dao.DAOCategory"%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/category.css" />" />
<div id="categoryCenterColumn">
    
    <p id="pageTitle">${category.getCategoryName()}</p>
    
    <br>
    <c:choose>
        <c:when test="${not empty bookList}">
            <table id="bookTable">
                <c:forEach var="book" items="${bookList}" >
                    <tr>
                        <td class="lightBlue">
                            <img src="<c:url value="/assets/images/book/${defaultImageMap[book.getBookId()]}" />" height="70" width="50" alt="book image">
                        </td>
                        <td class="lightBlue">${book.getTitle()}</td>
                        <td class="lightBlue">${book.getAuthor()}</td>
                        <td class="lightBlue">${book.getPrice()}</td>
                        <td class="lightBlue"><a href="<c:url value="/book?id=${book.getBookId()}" />">Book Details</a></td>
                        <td class="lightBlue">[select quantity]</td>
                        <td class="lightBlue">
                            <form action="#" method="post">
                                <input type="submit" value="purchase button">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <br><p><b>OOPS! No books in the '${category.getCategoryName()}' category!</b></p>
        </c:otherwise>
    </c:choose>
    <br>
    <button type="button" id="back-button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Back</button>
    <br>
</div>