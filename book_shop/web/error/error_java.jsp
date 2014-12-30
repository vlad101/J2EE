<%-- 
    Document   : error_java
    Created on : Jun 5, 2014, 8:23:26 PM
    Author     : Vladimir
--%>
<%@ page isErrorPage="true" %>

<p id="pageTitle">Error Java</p>
<br><br>

    <div>

        <h1>Java Error page!</h1>
        <p>Sorry, java has thrown an exception.</p>
        <p>To continue, click the Back button.</p>
        <div id="nav-buttons">
            <ul>
                <li>
                    <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
                </li>
            </ul>
        </div>
        <h2>Details</h2>
        <p>Type: ${pageContext.exception["class"]}</p>
        <p>Message: ${pageContext.exception["message"]}</p>
    </div>