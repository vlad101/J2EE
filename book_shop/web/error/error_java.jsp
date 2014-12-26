<%-- 
    Document   : error_java
    Created on : Jun 5, 2014, 8:23:26 PM
    Author     : Vladimir
--%>
<%@ page isErrorPage="true" %>

    <div>

        <h1>Java Error page!</h1>
        <p>Sorry, java has thrown an exception.</p>
        <p>To continue, click the Back button.</p>
        <button type="button" class="btn btn-success btn-lg" onclick="location='/book_shop/index.jsp'" >Back</button>

        <h2>Details</h2>
        <p>Type: {pageContext.exception["class"]}</p>
        <p>Message: {pageContext.exception["message"]}</p>
    </div>