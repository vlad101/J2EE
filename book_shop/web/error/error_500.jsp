<%-- 
    Document   : error_500
    Created on : Dec 25, 2014, 11:44:18 PM
    Author     : vladimir
--%>

<%@ page isErrorPage="true" %>

<div>

    <h1>Internal Server Error page!</h1>
    <p>The server was not able find the file you requested.</p>
    <p>To continue click the Back button.</p>
    <button type="button" class="btn btn-success btn-lg" onclick="location='/book_shop/index.jsp'" >Back</button>
</div>