<%-- 
    Document   : editcustomer
    Created on : Jun 6, 2014, 10:44:48 PM
    Author     : Vladimir
--%>

<!-- custom edit customer css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/editcustomer.css" />" />

<!-- Datatable css -->
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">

<!-- jQuery plugin -->
<script type="text/javascript" src="/book_shop/assets/jquery/js/jquery-1.10.2.js" ></script>

<!-- custom edit customer page js -->
<script type="text/javascript" src="<c:url value="/assets/js/polyfill.js" />" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/editcustomer.js" />" ></script>

<!-- Datatable js -->
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>

<div id="centerColumn">
                
    <br />
    <h1>Edit Customer</h1><br />
    
    <!-- Add customer -->
    <div id="add-customer">
        <form id="add_customer_form" name="post_customer_form" action="#">
            Add Customer  <input type="text" name="category_title" id="category_title" maxlength="50" value="">&nbsp;
                            <input type="button" name="submit_add_customer" id="submit_add_customer" value="Submit">
        </form>
    </div>
    <br />
    
    <!-- adding new customer -->
    <div id="ajax_add_customer_response_success" class="alert alert-success"></div>
    <div id="ajax_add_customer_response_error" class="alert alert-danger"></div>
    
    <!-- Update customer -->
    <div id="ajax_update_customer_response_success" class="alert alert-success"></div>
    <div id="ajax_update_customer_response_error" class="alert alert-danger"></div>
    
    <!-- Delete customer -->
    <div id="ajax_delete_customer_response_success" class="alert alert-success"></div>
    <div id="ajax_delete_customer_response_error" class="alert alert-danger"></div>
    <br />
    
    <!-- jQuery datatables -->
    <div id="customer-list">
        <table id="customer-list-table" class="display">
            <thead>
                <tr>
                    <th>Expand</th>
                    <th>Customer Name</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
                <tr>
                    <th>Expand</th>
                    <th>Customer Name</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </tfoot>
        </table>
    </div>
       

    <!-- Update Customer Modal -->
    <div class="modal fade" id="update-customer-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modalLabel">Customer Edit</h4>
          </div>
          <div class="modal-body">
              <h5>Edit customer:</h5><br/>
            <form id="update_customer_form" method="post" action="#">
                Category   <input type="text" value="" name="category_name">
                         <input type="hidden" value="" name="category_id">
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="update_customer_form_submit">Save changes</button>
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