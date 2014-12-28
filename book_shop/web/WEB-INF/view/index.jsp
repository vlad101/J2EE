<%-- 
    Document   : index
    Created on : May 31, 2014, 5:58:03 PM
    Author     : Vladimir
--%>

            <link type="text/css" rel="stylesheet" href="<c:url value="assets/css/index.css" />" />

            <div id="indexLeftColumn">
                <h3>Welcome to Serendipity Book Store!</h3>
                <br><p>
                    Thanks for visiting. Make yourself at home. Feel free to browse 
                    through our book catalog. We think our catalog contains some great books,
                    and we hope you like it as much as we do.
                </p>
                <p>
                    If you find a book that you like, we hope that you'll use this site 
                    to order it. Most of the books we carry aren't available anywhere else!
                </p><br>
                <div id="leftColumn-menu-items">
                    <a class="current" href="<c:url value='/'/>" >Home</a><br>
                    <a href="<c:url value='/admin'/>" >Browse Catalog</a><br>
                    <a href="<c:url value='/email'/>" >Join Email List</a><br>
                    <a href="<c:url value='/customer_service'/>" >Customer Service</a>
                </div>
            </div>
            <div id="indexRightColumn">
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
                        <div class="categoryListBox">
                             <a href="#">
                                 <span class="categoryListLabelText">
                                     More
                                 </span>
                             </a>
                         </div>
                    </c:when>
                    <c:otherwise>
                        <br><p><b>OOPS! Empty category list!</b></p>
                    </c:otherwise>
                </c:choose>
            </div>