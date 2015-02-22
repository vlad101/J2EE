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
    <c:choose>
        <c:when test="${not empty categoryList}">
            <table id="categoryTable">
                <tr class="header">
                    <th>Category Book List</th>
                </tr>
                <c:forEach var="category" items="${categoryList}" varStatus="loopStatus">
                    <tr class="${loopStatus.index % 2 == 0 ? 'lightBlue' : 'white'}">
                        <td>
                            <div class="categoryBox">
                                <span class="categoryLabelText">
                                        <p><a href="<c:url value='/category/category?id=${category.getCategoryId()}'/>" >${category.getCategoryName()}</a></p>
                                </span>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <br><p><b>OOPS! Empty category list!</b></p>
        </c:otherwise>
    </c:choose>
     <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>  
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/book/booklist" />'" >Book List</button>
            </li>
        </ul>
    </div>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>