<%-- 
    Document   : search
    Created on : Dec 30, 2014, 12:31:22 AM
    Author     : vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/search.css" />" />

<!-- include jquery core files -->
<script type="text/javascript" src="<c:url value="/assets/js/search.js" />" ></script>

<p id="pageTitle">Search</p>

<div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>

<!-- error search messages -->
<div id="ajax_search_book_title_response_error" style="visibility: hidden;" class="alert alert-danger"></div>
<div id="ajax_search_book_author_response_error" style="visibility: hidden;" class="alert alert-danger"></div>
<div id="ajax_search_category_name_response_error" style="visibility: hidden;" class="alert alert-danger"></div>

<div id="centerColumn">

    <!-- book title search form -->
    <br><br>
    <div class="button-list">
        <button type="button" class="btn btn-primary btn-small" id="search-title-button" >Search By Title</button><br><br>
        <button type="button" class="btn btn-primary btn-small" id="search-author-button" >Search By Author</button><br><br>
        <button type="button" class="btn btn-primary btn-small" id="search-category-button" >Search By Category</button><br><br>
    </div>
    
    <form id="search_book_title_form" action="#" style="display:none;" >
        <div id="book_title_search">
            <label>Enter Book Title</label><br>
            <input type="text" value="" name="book_title_search" id="book_title_search_form">
        </div>
    </form>
    <br>
    <button type="button" class="btn btn-primary" id="search_book_title_form_submit" style="display: none;" >Search</button>
    
    <div id="book-title-search" style="display:none;" >
        <br>Search results for '<span id="book-name"></span>'.
        <br><br>
        <div id="search-title-result"></div>
    </div>
    
</div>
<br>
<div id="nav-buttons">
    <ul>
        <li>
            <button type="button" id="search-home-button" class="btn btn-success btn-small" style="display: none;" >Search</button><br>
        </li>
    </ul>
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
        </li>
    </ul>
</div>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>