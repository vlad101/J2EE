<%-- 
    Document   : error_404
    Created on : Jun 5, 2014, 8:23:26 PM
    Author     : Vladimir
--%>
<%@ page isErrorPage="true" %>

<!-- custom edit error css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/error.css" />" />

<p id="pageTitle">Error 404</p>
<br><br>

<div>
    <p>The server was not able find the page you requested.</p>
    <p>To continue click the Back button.</p>
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>
        </ul>
    </div>
</div>