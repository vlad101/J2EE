<%-- 
    Document   : editcustomer
    Created on : Jun 6, 2014, 10:44:48 PM
    Author     : Vladimir
--%>

<!-- custom edit customer css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/editcustomer.css" />" />

<!-- Datatable css -->
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">

<!-- custom edit customer page js -->
<script type="text/javascript" src="<c:url value="/assets/js/editcustomer.js" />" ></script>

<!-- Datatable js -->
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>

<p id="pageTitle">Edit Customer</p>
<div id="centerColumn">
                
    <br><br>
    
    <!-- Add customer -->
    <button type="button" id="customer_add_button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#add-customer-modal" >Add Customer</button>
    <br/>
    
    <!-- adding new customer -->
    <div id="ajax_add_customer_response_success" class="alert alert-success"></div>
    
    <!-- Update customer -->
    <div id="ajax_update_customer_response_success" class="alert alert-success"></div>
    
    <!-- Delete customer -->
    <div id="ajax_delete_customer_response_success" class="alert alert-success"></div>
    <div id="ajax_delete_customer_response_error" class="alert alert-danger"></div>
    <br />
    
    <!-- jQuery datatables -->
    <div id="preloader"><img src="/book_shop/assets/images/loader/loader.gif" alt="Get customer preloader" ></div>
    <div id="preloader-text">Loading...</div>
    <div id="customer-list">
        <table id="customer-list-table" class="display">
            <thead>
                <tr>
                    <th>Expand</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
                <tr>
                    <th>Expand</th>
                    <th>First Name</th>
                    <th>Last Name</th>
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
            <h4 class="modal-title" id="modalLabel">Customer Edit Info</h4>
          </div>
          <div class="modal-body">
              
            <!-- update customer error -->
            <div id="ajax_update_customer_response_error" class="alert alert-danger"></div><br />
              
            <form id="update_customer_form" method="post" action="#">
                <div id="customer_first_name_update">
                    <label>First Name</label><br>
                    <input type="text" value="" name="customer_first_name_update">
                </div><br>
                <div id="customer_last_name_update">
                    <label>Last Name</label><br>
                    <input type="text" value="" name="customer_last_name_update">
                </div><br>
                <div id="customer_cc_number_update">
                    <label>Credit Card Number</label><br>
                    <input type="text" value="" name="customer_cc_number_update">
                </div><br>
                <div id="customer_email_update">
                    <label>Email</label><br>
                    <input type="text" value="" name="customer_email_update">
                </div><br>
                <div id="customer_phone_update">
                    <label>Phone</label><br>
                    <input type="text" value="" name="customer_phone_update">
                </div><br>
                <div id="customer_address_update">
                    <label>Address</label><br>
                    <input type="text" value="" name="customer_address_update">
                </div><br>
                <div id="customer_city_update">
                    <label>City</label><br>
                    <input type="text" value="" name="customer_city_update">
                </div><br>
                <div id="customer_state_update">
                    <label>State</label><br>
                    <input type="text" value="" name="customer_state_update">
                </div><br>
                <div id="customer_zipcode_update">
                    <label>Zip Code</label><br>
                    <input type="text" value="" name="customer_zipcode_update">
                </div>
                    <input type="hidden" value="" name="customer_id_update">
                         
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="update_customer_form_submit">Save changes</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Add Customer Modal -->
    <div class="modal fade" id="add-customer-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modalLabel">Add Customer</h4>
          </div>
          <div class="modal-body">
              
            <!-- add new customer -->
            <div id="ajax_add_customer_response_error" class="alert alert-danger"></div>
              
            <form id="add_customer_form" method="post" action="#">
                <div id="customer_first_name_add">
                    <label>First Name</label><br>
                    <input type="text" value="" name="customer_first_name_add">
                </div><br>
                <div id="customer_last_name_add">
                    <label>Last Name</label><br>
                    <input type="text" value="" name="customer_last_name_add">
                </div><br>
                <div id="customer_cc_number_add">
                    <label>Credit Card Number</label><br>
                    <input type="text" value="" name="customer_cc_number_add">
                </div><br>
                <div id="customer_email_add">
                    <label>Email</label><br>
                    <input type="text" value="" name="customer_email_add">
                </div><br>
                <div id="customer_phone_add">
                    <label>Phone</label><br>
                    <input type="text" value="" name="customer_phone_add">
                </div><br>
                <div id="customer_address_add">
                    <label>Address</label><br>
                    <input type="text" value="" name="customer_address_add">
                </div><br>
                <div id="customer_city_add">
                    <label>City</label><br>
                    <input type="text" value="" name="customer_city_add">
                </div><br>
                <div id="customer_state_add">
                    <label>State</label><br>
                    <input type="text" value="" name="customer_state_add">
                </div><br>
                <div id="customer_zipcode_add">
                    <label>Zip Code</label><br>
                    <input type="text" value="" name="customer_zipcode_add">
                </div><br>
                    <input type="hidden" value="" name="customer_id_add">
                         
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="add_customer_form_submit">Save changes</button>
          </div>
        </div>
      </div>
    </div>    
    <br>
    <button type="button" id="back-button" class="btn btn-success btn-small" onclick="location='<c:url value="/admin" />'" >Back</button>
    <br>
</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Bootbox alerts plugin plugin -->
<script src="<c:url value="/assets/plugins/bootbox/bootbox.min.js" />"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<link href="<c:url value="/assets/bootstrap/css/bootstrap.min.css" />">