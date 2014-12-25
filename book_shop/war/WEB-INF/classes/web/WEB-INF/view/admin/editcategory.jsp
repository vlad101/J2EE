<%-- 
    Document   : editcategory
    Created on : Jun 6, 2014, 8:56:43 PM
    Author     : Vladimir
--%>

<!-- custom edit category css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/editcategory.css" />" />

<!-- Datatable css -->
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">

<!-- jQuery plugin -->
<script type="text/javascript" src="/book_shop/assets/jquery/js/jquery-1.10.2.js" ></script>

<!-- custom edit category page js -->
<script type="text/javascript" src="<c:url value="/assets/js/editcategory.js" />" ></script>

<!-- Datatable js -->
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>

<div id="centerColumn">
                
    <br />
    <h1>Edit Category</h1><br />
    
    <!-- Add category -->
    <div id="add-category">
        <form id="add_category_form" name="post_category_form" action="#">
            Add Category  <input type="text" name="category_title" id="category_title" maxlength="50" value="">&nbsp;
                            <input type="button" name="submit_add_category" id="submit_add_category" value="Submit">
        </form>
    </div>
    <br />
    
    <!-- adding new category -->
    <div id="ajax_add_category_response_success" class="alert alert-success"></div>
    <div id="ajax_add_category_response_error" class="alert alert-danger"></div>
    
    <!-- Update category -->
    <div id="ajax_update_category_response_success" class="alert alert-success"></div>
    <div id="ajax_update_category_response_error" class="alert alert-danger"></div>
    
    <!-- Delete category -->
    <div id="ajax_delete_category_response_success" class="alert alert-success"></div>
    <div id="ajax_delete_category_response_error" class="alert alert-danger"></div>
    <br />
    
    <!-- jQuery datatables -->
    <div id="preloader"><img src="/book_shop/assets/images/loader/loader.gif" alt="Get customer preloader" ></div>
    <div id="preloader-text">Loading...</div>
    <div id="category-list">
        <table id="category-list-table" class="display">
            <thead>
                <tr>
                    <th>Expand</th>
                    <th>Category</th>
                    <th>Book Qty</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
                <tr>
                    <th>Expand</th>
                    <th>Category</th>
                    <th>Book Qty</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </tfoot>
        </table>
    </div>
       

    <!-- Update Category Modal -->
    <div class="modal fade" id="update-category-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modalLabel">Category Edit</h4>
          </div>
          <div class="modal-body">
              <h5>Edit category name:</h5><br/>
            <form id="update_category_form" method="post" action="#">
                Category   <input type="text" value="" name="category_name">
                         <input type="hidden" value="" name="category_id">
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="update_category_form_submit">Save changes</button>
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