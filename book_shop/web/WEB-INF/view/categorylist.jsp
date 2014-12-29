<%-- 
    Document   : categorylist
    Created on : Dec 28, 2014, 2:07:42 PM
    Author     : vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/categorylist.css" />" />

<p id="pageTitle">Category List</p>

<br>

<div id="centerColumn">
    <br><br>
    <button type="button" class="btn btn-success btn-small" onclick="location='/book_shop/index'" >Back</button>
    <br><br>
    <c:choose>
        <c:when test="${not empty categoryList}">
            <c:forEach var="category" items="${categoryList}" >
                <div class="categoryBox">
                    <a href="#">
                        <span class="categoryLabelText">
                            ${category.getCategoryName()}
                        </span>
                    </a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <br><p><b>OOPS! Empty category list!</b></p>
        </c:otherwise>
    </c:choose>
    <br>
    <button type="button" class="btn btn-success btn-small" onclick="location='/book_shop/index'" >Back</button>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>