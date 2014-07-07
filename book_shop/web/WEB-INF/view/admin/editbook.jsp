<%-- 
    Document   : editbook
    Created on : Jun 6, 2014, 10:04:20 PM
    Author     : Vladimir
--%>

<!-- custom edit book css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/editbook.css" />" />

<!-- Datatable css -->
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">

<!-- jQuery plugin -->
<script type="text/javascript" src="<c:url value="/assets/jquery/js/jquery-1.10.2.js" />" ></script>

<!-- custom edit book page js -->
<script type="text/javascript" src="<c:url value="/assets/js/polyfill.js" />" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/editbook.js" />" ></script>

<!-- Datatable js -->
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>

<div id="centerColumn">
                
    <br />
    <h1>Edit Book</h1><br />
    
    <!-- Add book -->
    <div id="add-book">
        <form id="add_book_form" name="post_book_form" action="#">
            Add Book  <input type="text" name="book_title" id="book_title" maxlength="50" value="">&nbsp;
                            <input type="button" name="submit_add_book" id="submit_add_book" value="Submit">
        </form>
    </div>
    <br />
    
    <!-- adding new book -->
    <div id="ajax_add_book_response_success" class="alert alert-success"></div>
    <div id="ajax_add_book_response_error" class="alert alert-danger"></div>
    
    <!-- Update book -->
    <div id="ajax_update_book_response_success" class="alert alert-success"></div>
    <div id="ajax_update_book_response_error" class="alert alert-danger"></div>
    
    <!-- Delete book -->
    <div id="ajax_delete_book_response_success" class="alert alert-success"></div>
    <div id="ajax_delete_book_response_error" class="alert alert-danger"></div>
    <br />
    
    <!-- jQuery datatables -->
    <div id="book-list">
        <table id="book-list-table" class="display">
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
            <form id="update_book_form" method="post" action="#">
                <div id="book_title">
                    <label>Title</label>
                    <input type="text" value="" name="book_title">
                </div><br>
                <div id="book_author">
                    <label>Author</label>
                    <input type="text" value="" name="book_author">
                </div><br>
                <div id="book_quantity">
                    <label>Quantity</label>
                    <input type="text" value="" name="book_quantity">
                </div><br>
                <div id="book_category_name_search">
                    <label>Category</label>
                    <input type="text" value="" name="book_category_name" id="book_category_name" >
                </div><br>
                <div id="book_price">
                    <label>Price</label>
                    <input type="text" value="" name="book_price">
                </div><br>
                <div id="book_description">
                    <label>Description</label>
                    <input type="text" value="" name="book_description">
                </div><br>
                <div id="book_last_update">
                    <label>Last Update</label>
                    <input type="text" value="" name="book_last_update" readonly>
                </div>
                    <input type="hidden" value="" name="book_id">
                         
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="update_book_form_submit">Save changes</button>
          </div>
        </div>
      </div>
    </div>


</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Bootbox alerts plugin plugin -->
<script src="<c:url value="/assets/plugins/bootbox/bootbox.min.js" />"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<link href="<c:url value="/assets/bootstrap/css/bootstrap.min.css" />">

<!-- Typeahead js -->
<script type="text/javascript" language="javascript" src="<c:url value="/assets/plugins/typeahead/typeahead.js" />" ></script>