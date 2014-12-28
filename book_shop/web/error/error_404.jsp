<%-- 
    Document   : error_404
    Created on : Jun 5, 2014, 8:23:26 PM
    Author     : Vladimir
--%>
<%@ page isErrorPage="true" %>

<div>

    <h1>404 Error page!</h1>
    <p>The server was not able find the file you requested.</p>
    <p>To continue click the Back button.</p>
    <button type="button" class="btn btn-success btn-lg" onclick="location='/book_shop/index'" >Back</button>
</div>