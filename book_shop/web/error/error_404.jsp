<%-- 
    Document   : error_404
    Created on : Jun 5, 2014, 8:23:26 PM
    Author     : Vladimir
--%>
<%@ page isErrorPage="true" %>

<p id="pageTitle">Error 404</p>
<br><br>

<div>
    <p>The server was not able find the page you requested.</p>
    <p>To continue click the Back button.</p>
    <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Back</button>
</div>