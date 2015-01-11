<%-- 
    Document   : index
    Created on : May 31, 2014, 5:58:03 PM
    Author     : Vladimir
--%>

            <link type="text/css" rel="stylesheet" href="<c:url value="assets/css/index.css" />" />
            
            <p id="pageTitle">Home</p>
            
            <div id="indexLeftColumn">
                <br>
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
            </div>
            <div id="indexRightColumn">
                <c:choose>
                    <c:when test="${not empty categoryList}">
                        <c:forEach var="category" items="${categoryList}" >
                            <div class="categoryBox">
                                <a href="#">
                                    <span class="categoryLabelText">
                                        <a href="<c:url value='/category/category?id=${category.getCategoryId()}'/>" >${category.getCategoryName()}</a>
                                    </span>
                                </a>
                            </div>
                        </c:forEach>
                        <div class="categoryListBox">
                             <a href="#">
                                 <span class="categoryListLabelText">
                                    <a href="<c:url value='/category/categorylist'/>" >All Categories</a>
                                 </span>
                             </a>
                         </div>
                        <div class="bookListBox">
                             <a href="#">
                                 <span class="bookListLabelText">
                                    <a href="<c:url value='/book/booklist'/>" >All Books</a>
                                 </span>
                             </a>
                         </div>
                    </c:when>
                    <c:otherwise>
                        <br><p><b>OOPS! Empty category list!</b></p>
                    </c:otherwise>
                </c:choose>
            </div>