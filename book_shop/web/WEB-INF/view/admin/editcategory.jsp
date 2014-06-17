<%-- 
    Document   : editcategory
    Created on : Jun 6, 2014, 8:56:43 PM
    Author     : Vladimir
--%>

<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/editcategory.css" />" />

<!-- Datatable css -->
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">

<script type="text/javascript" src="/book_shop/assets/jquery/js/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/polyfill.js" />" ></script>
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
    <div id="ajax_add_category_response"></div>
    <br />
    
    <!-- jQuery datatables -->
    <div id="category-list">
        <table id="category-list-table" class="display">
            <thead>
                <tr>
                    <th>Category</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>

            <tfoot>
                <tr>
                    <th>Category</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </tfoot>
        </table>
    </div>
    
    <!-- Update category -->
     <br />
    <form id="update_category_form" name="update_category_form" action="#">
        <table border="1">
            <tr>
                <td>Category title</td>
                <td><input type="text" id="set_category_name" name="category_name" ></td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" id="set_category_id" name="category_id">
                </td>
                <td><input type="submit" id="submit_update_category" ></td>
            </tr>
        </table>
    </form>
    <div id="ajax_update_category_response"></div>
    
    <!-- Delete category -->
    <div id="ajax_delete_category_response"></div>                
</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>