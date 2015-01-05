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

<div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>

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
    
    <!-- Redirect customer -->
    <div id="ajax_redirect_customer_response_error" class="alert alert-danger"></div>
    
    <br>
    
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
                <div id="customer_admin_update">
                    <label>Check box if Admin</label><br>
                <!--<input type="hidden" value="0" name="customer_admin_update"> -->
                    <!--<input type="checkbox" value="" name="customer_admin_update">-->
                    <input type="hidden" value="0" name="customer_admin_update">
                    <input type="checkbox" value="1" name="customer_admin_update">
                </div><br>
                <div id="customer_first_name_update">
                    <label>First Name</label><br>
                    <input type="text" value="" name="customer_first_name_update">
                </div><br>
                <div id="customer_last_name_update">
                    <label>Last Name</label><br>
                    <input type="text" value="" name="customer_last_name_update">
                </div><br>
                <div id="customer_username_update">
                    <label>Username</label><br>
                    <input type="text" value="" name="customer_username_update">
                </div><br>
                <div id="customer_password1_add">
                    <label>Password</label><br>
                    <input type="text" value="" name="customer_password1_update">
                </div><br>
                <div id="customer_password2_add">
                    <label>Re-enter Password</label><br>
                    <input type="text" value="" name="customer_password2_update">
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
                    <!--<input type="text" value="" name="customer_state_update">-->
                    <select name="customer_state_update">
                        <option value="AL">Alabama</option>
                        <option value="AK">Alaska</option>
                        <option value="AZ">Arizona</option>
                        <option value="AR">Arkansas</option>
                        <option value="CA">California</option>
                        <option value="CO">Colorado</option>
                        <option value="CT">Connecticut</option>
                        <option value="DE">Delaware</option>
                        <option value="DC">District Of Columbia</option>
                        <option value="FL">Florida</option>
                        <option value="GA">Georgia</option>
                        <option value="HI">Hawaii</option>
                        <option value="ID">Idaho</option>
                        <option value="IL">Illinois</option>
                        <option value="IN">Indiana</option>
                        <option value="IA">Iowa</option>
                        <option value="KS">Kansas</option>
                        <option value="KY">Kentucky</option>
                        <option value="LA">Louisiana</option>
                        <option value="ME">Maine</option>
                        <option value="MD">Maryland</option>
                        <option value="MA">Massachusetts</option>
                        <option value="MI">Michigan</option>
                        <option value="MN">Minnesota</option>
                        <option value="MS">Mississippi</option>
                        <option value="MO">Missouri</option>
                        <option value="MT">Montana</option>
                        <option value="NE">Nebraska</option>
                        <option value="NV">Nevada</option>
                        <option value="NH">New Hampshire</option>
                        <option value="NJ">New Jersey</option>
                        <option value="NM">New Mexico</option>
                        <option value="NY">New York</option>
                        <option value="NC">North Carolina</option>
                        <option value="ND">North Dakota</option>
                        <option value="OH">Ohio</option>
                        <option value="OK">Oklahoma</option>
                        <option value="OR">Oregon</option>
                        <option value="PA">Pennsylvania</option>
                        <option value="RI">Rhode Island</option>
                        <option value="SC">South Carolina</option>
                        <option value="SD">South Dakota</option>
                        <option value="TN">Tennessee</option>
                        <option value="TX">Texas</option>
                        <option value="UT">Utah</option>
                        <option value="VT">Vermont</option>
                        <option value="VA">Virginia</option>
                        <option value="WA">Washington</option>
                        <option value="WV">West Virginia</option>
                        <option value="WI">Wisconsin</option>
                        <option value="WY">Wyoming</option>
                        <option value="AS">American Samoa</option>
                        <option value="GU">Guam</option>
                        <option value="MP">Northern Mariana Islands</option>
                        <option value="PR">Puerto Rico</option>
                        <option value="UM">US Minor Outlying Islands</option>
                        <option value="VI">Virgin Islands</option>
                        <option value="AA">Armed Forces Americas</option>
                        <option value="AP">Armed Forces Pacific</option>
                        <option value="AE">Armed Forces Others</option>	
                    </select>
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
                    <label>Check box if Admin</label><br>
                    <input type="hidden" value="0" name="customer_admin_add">
                    <input type="checkbox" value="1" name="customer_admin_add">
                </div><br>
                <div id="customer_first_name_add">
                    <label>First Name</label><br>
                    <input type="text" value="" name="customer_first_name_add">
                </div><br>
                <div id="customer_last_name_add">
                    <label>Last Name</label><br>
                    <input type="text" value="" name="customer_last_name_add">
                </div><br>
                <div id="customer_username_add">
                    <label>Username</label><br>
                    <input type="text" value="" name="customer_username_add">
                </div><br>
                <div id="customer_password1_add">
                    <label>Password</label><br>
                    <input type="text" value="" name="customer_password1_add">
                </div><br>
                <div id="customer_password2_add">
                    <label>Re-enter Password</label><br>
                    <input type="text" value="" name="customer_password2_add">
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
                    <!--<input type="text" value="" name="customer_state_add">-->
                    <select name="customer_state_add">
                        <option value="AL">Alabama</option>
                        <option value="AK">Alaska</option>
                        <option value="AZ">Arizona</option>
                        <option value="AR">Arkansas</option>
                        <option value="CA">California</option>
                        <option value="CO">Colorado</option>
                        <option value="CT">Connecticut</option>
                        <option value="DE">Delaware</option>
                        <option value="DC">District Of Columbia</option>
                        <option value="FL">Florida</option>
                        <option value="GA">Georgia</option>
                        <option value="HI">Hawaii</option>
                        <option value="ID">Idaho</option>
                        <option value="IL">Illinois</option>
                        <option value="IN">Indiana</option>
                        <option value="IA">Iowa</option>
                        <option value="KS">Kansas</option>
                        <option value="KY">Kentucky</option>
                        <option value="LA">Louisiana</option>
                        <option value="ME">Maine</option>
                        <option value="MD">Maryland</option>
                        <option value="MA">Massachusetts</option>
                        <option value="MI">Michigan</option>
                        <option value="MN">Minnesota</option>
                        <option value="MS">Mississippi</option>
                        <option value="MO">Missouri</option>
                        <option value="MT">Montana</option>
                        <option value="NE">Nebraska</option>
                        <option value="NV">Nevada</option>
                        <option value="NH">New Hampshire</option>
                        <option value="NJ">New Jersey</option>
                        <option value="NM">New Mexico</option>
                        <option value="NY">New York</option>
                        <option value="NC">North Carolina</option>
                        <option value="ND">North Dakota</option>
                        <option value="OH">Ohio</option>
                        <option value="OK">Oklahoma</option>
                        <option value="OR">Oregon</option>
                        <option value="PA">Pennsylvania</option>
                        <option value="RI">Rhode Island</option>
                        <option value="SC">South Carolina</option>
                        <option value="SD">South Dakota</option>
                        <option value="TN">Tennessee</option>
                        <option value="TX">Texas</option>
                        <option value="UT">Utah</option>
                        <option value="VT">Vermont</option>
                        <option value="VA">Virginia</option>
                        <option value="WA">Washington</option>
                        <option value="WV">West Virginia</option>
                        <option value="WI">Wisconsin</option>
                        <option value="WY">Wyoming</option>
                        <option value="AS">American Samoa</option>
                        <option value="GU">Guam</option>
                        <option value="MP">Northern Mariana Islands</option>
                        <option value="PR">Puerto Rico</option>
                        <option value="UM">United States Minor Outlying Islands</option>
                        <option value="VI">Virgin Islands</option>
                        <option value="AA">Armed Forces Americas</option>
                        <option value="AP">Armed Forces Pacific</option>
                        <option value="AE">Armed Forces Others</option>	
                    </select>
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