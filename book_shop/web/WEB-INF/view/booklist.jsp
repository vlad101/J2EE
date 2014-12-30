<%-- 
    Document   : booklist
    Created on : Dec 29, 2014, 4:58:25 PM
    Author     : vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/booklist.css" />" />

<p id="pageTitle">Book List</p>

<br><br>

<div id="centerColumn">
    <c:choose>
        <c:when test="${not empty bookList}">
            <c:forEach var="book" items="${bookList}" >
                <div class="bookBox">
                    <a href="#">
                        <span class="categoryLabelText">
                            <a href="<c:url value='/book?id=${book.getBookId()}'/>" >${book.getTitle()}</a>
                        </span>
                    </a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <br><p><b>OOPS! Empty book list!</b></p>
        </c:otherwise>
    </c:choose>
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>  
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/categorylist" />'" >Category List</button>
            </li>
        </ul>
    </div>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>