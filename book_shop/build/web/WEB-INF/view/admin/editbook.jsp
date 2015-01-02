<%-- 
    Document   : editbook
    Created on : Jun 6, 2014, 10:04:20 PM
    Author     : Vladimir
--%>

<!-- custom edit book css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/editbook.css" />" />

<!-- Datatable css -->
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">

<!-- custom edit book page js -->
<script type="text/javascript" src="<c:url value="/assets/js/editbook.js" />" ></script>

<!-- Datatable js -->
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>

<p id="pageTitle">Edit Book</p>

<div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>

<div id="centerColumn">
                
    <br><br>  
    <!-- Add book -->
    <button type="button" id="book_add_button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#add-book-modal" >Add Book</button>
    <br/>
    
    <!-- adding new book success -->
    <div id="ajax_add_book_response_success" class="alert alert-success"></div>
    
    <!-- Update book success -->
    <div id="ajax_update_book_response_success" class="alert alert-success"></div>
    <div id="imgTest"></div>
    
    <!-- Delete book -->
    <div id="ajax_delete_book_response_success" class="alert alert-success"></div>
    <div id="ajax_delete_book_response_error" class="alert alert-danger"></div>
    
    <!--Redirect to the book error-->
    <div id="ajax_redirect_book_response_error" class="alert alert-danger"></div>
    <br />
    
    <!-- jQuery datatables -->
    <div id="preloader"><img src="<c:url value="/assets/images/loader/loader.gif" />" alt="Get book preloader" ></div>
    <div id="preloader-text">Loading...</div>
    <div id="book-list" >
        <table id="book-list-table" class="display" >
            <thead>
                <tr>
                    <th>Expand</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Quantity</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Description</th>
                    <th>Last Update</th>
                    <th>Image</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
                <tr>
                    <th>Expand</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Quantity</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Description</th>
                    <th>Last Update</th>
                    <th>Image</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </tfoot>
        </table>
    </div>
       

    <!-- Update Book Modal -->
    <div class="modal fade" id="update-book-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modalLabel">Book Edit Info</h4>
          </div>
          <div class="modal-body">
              
            <!-- update book error -->
            <div id="ajax_update_book_response_error" class="alert alert-danger"></div>
              
            <form id="update_book_form" method="post" action="#">
                <div id="book_title_update">
                    <label>Title</label><br>
                    <input type="text" value="" name="book_title_update">
                </div><br>
                <div id="book_author_update">
                    <label>Author</label><br>
                    <input type="text" value="" name="book_author_update">
                </div><br>
                <div id="book_quantity_update">
                    <label>Quantity</label><br>
                    <input type="text" value="" name="book_quantity_update">
                </div><br>
                <div id="book_category_name_modal_update">
                    <label>Category</label><br>
                    <input type="text" value="" name="book_category_name_update" id="book_category_name_update" >
                </div><br>
                <div id="book_price_update">
                    <label>Price</label><br>
                    <input type="text" value="" name="book_price_update">
                </div><br>
                <div id="book_description_update">
                    <label>Description</label><br>
                    <input type="text" value="" name="book_description_update">
                </div><br>
                <div id="book_last_update_update">
                    <label>Last Update</label><br>
                    <input type="text" value="" name="book_last_update" readonly>
                </div>
                 <div id="book_image_list_update">
                    <label><br />Select Image Thumbnail</label>
                    <div id="book_image" style="display:none"></div>
                    <div id="book_image_selection" style="display:none"></div>
                </div>
                <br>
                <input style="margin-left: 30%;margin-right: 50%;" id="book_image_update" type="file" onchange="loadImageFileAsURL('book_image_update');">
                <br>
                <input type="hidden" value="" name="book_id_update">
                         
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="update_book_form_submit">Save changes</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Add Book Modal -->
    <div class="modal fade" id="add-book-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modalLabel">Add Book</h4>
          </div>
          <div class="modal-body">
              
            <!-- add new book -->
            <div id="ajax_add_book_response_error" class="alert alert-danger"></div>
              
            <form id="add_book_form" method="post" action="#" enctype="multipart/form-data">
                <div id="book_title_add">
                    <label>Title</label><br>
                    <input type="text" value="" name="book_title_add">
                </div><br>
                <div id="book_author_add">
                    <label>Author</label><br>
                    <input type="text" value="" name="book_author_add">
                </div><br>
                <div id="book_quantity_add">
                    <label>Quantity</label><br>
                    <input type="text" value="" name="book_quantity_add">
                </div><br>
                <div id="book_category_name_modal_add">
                    <label>Category</label><br>
                    <input type="text" value="" name="book_category_name_add" id="book_category_name_add" >
                </div><br>
                <div id="book_price_add">
                    <label>Price</label><br>
                    <input type="text" value="" name="book_price_add">
                </div><br>
                <div id="book_description_add">
                    <label>Description</label><br>
                    <input type="text" value="" name="book_description_add">
                </div>
                <br>
                    <input style="margin-left: 34%;margin-right: 50%;" id="book_image_add" type="file" onchange="loadImageFileAsURL('book_image_add');">
                <br>
                    <input type="hidden" value="" name="book_id_add">
                         
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="add_book_form_submit">Save changes</button>
          </div>
        </div>
      </div>
    </div>
</div>
<div id="nav-buttons">
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/admin" />'" >Admin Home</button><br>
        </li>
    </ul>
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >User Home</button><br>
        </li>
    </ul>
</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Bootbox alerts plugin plugin -->
<script src="<c:url value="/assets/plugins/bootbox/bootbox.min.js" />"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<link href="<c:url value="/assets/bootstrap/css/bootstrap.min.css" />">

<!-- Typeahead js -->
<script type="text/javascript" language="javascript" src="<c:url value="/assets/plugins/typeahead/typeahead.js" />" ></script>

<!--FancyBox Plugin css-->
<link rel="stylesheet" href="<c:url value="/assets/plugins/fancybox/source/jquery.fancybox.css" />" type="text/css" media="screen" />

<!--FancyBox Plugin js-->
<script type="text/javascript" src="<c:url value="/assets/plugins/fancybox/source/jquery.fancybox.pack.js" />">  </script>