<%-- 
    Document   : error_500
    Created on : Dec 25, 2014, 11:44:18 PM
    Author     : vladimir
--%>

<%@ page isErrorPage="true" %>

<p id="pageTitle">Error 500</p>

<br><br>

<div>
    <p>The server was not able find the file you requested.</p>
    <p>To continue click the Back button.</p>
    <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Back</button>
</div>