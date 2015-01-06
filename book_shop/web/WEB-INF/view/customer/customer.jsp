<%-- 
    Document   : customer
    Created on : Jan 4, 2015, 7:24:50 PM
    Author     : vladimir
--%>

    <!-- custom edit book css -->
    <link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/customer.css" />" />

    <!-- custom edit book page js -->
    <script type="text/javascript" src="<c:url value="/assets/js/customer.js" />" ></script>

    <p id="pageTitle">Customer Details</p>

    <div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>
    <div id="state" style="visibility: hidden;">${customer.getState()}</div>
    
    <div id="centerColumn">
        <!-- Update customer -->
        <br>
        <button type="button" id="customer_update_button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#update-customer-modal" >Update Customer</button>
        <br><br>
        
    <!-- Update Customer Modal -->
    <div class="modal fade" id="update-customer-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="modalLabel">Customer Update Info</h4>
            </div>
            <div class="modal-body">

              <!-- update customer error -->
              <div id="ajax_update_customer_response_error" class="alert alert-danger"></div><br />

              <form id="update_customer_form" method="post" action="#">
                  <div id="customer_first_name_update">
                      <label>First Name</label><br>
                      <input type="text" value="${customer.getFirstName()}" name="customer_first_name_update">
                  </div><br>
                  <div id="customer_last_name_update">
                      <label>Last Name</label><br>
                      <input type="text" value="${customer.getLastName()}" name="customer_last_name_update">
                  </div><br>
                  <div id="customer_username_update">
                      <label>Username</label><br>
                      <input type="text" value="${user.getUsername()}" name="customer_username_update">
                  </div><br>
                  <div id="customer_password1_add">
                      <label>Password</label><br>
                      <input type="text" value="${user.getPassword()}" name="customer_password1_update">
                  </div><br>
                  <div id="customer_password2_add">
                      <label>Re-enter Password</label><br>
                      <input type="text" value="${user.getPassword()}" name="customer_password2_update">
                  </div><br>
                  <div id="customer_cc_number_update">
                      <label>Credit Card Number</label><br>
                      <input type="text" value="${customer.getCcNumber()}" name="customer_cc_number_update">
                  </div><br>
                  <div id="customer_email_update">
                      <label>Email</label><br>
                      <input type="text" value="${customer.getEmail()}" name="customer_email_update">
                  </div><br>
                  <div id="customer_phone_update">
                      <label>Phone</label><br>
                      <input type="text" value="${customer.getPhone()}" name="customer_phone_update">
                  </div><br>
                  <div id="customer_address_update">
                      <label>Address</label><br>
                      <input type="text" value="${customer.getAddress()}" name="customer_address_update">
                  </div><br>
                  <div id="customer_city_update">
                      <label>City</label><br>
                      <input type="text" value="${customer.getCity()}" name="customer_city_update">
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
                      <input type="text" value="${customer.getZipCode()}" name="customer_zipcode_update">
                  </div>
                      <input type="hidden" value="${customer.getCustomerId()}" name="customer_id_update">
                      <input type="hidden" value="0" name="customer_admin_update">

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

        <!-- Get customer details -->
    <div id="leftColumn">
        <div id="first_name">
            <p><strong>First Name</strong>: ${customer.getFirstName()}</p>
        </div>
        <div id="customer_last_name">
            <p><strong>Last Name</strong>: ${customer.getLastName()}</p>
        </div>
        <div id="customer_username">
            <p><strong>Username</strong>: ${user.getUsername()}</p>
        </div>
        <div id="customer_password">
            <p><strong>Password</strong>: ${user.getPassword()}</p>
        </div>
        <div id="customer_cc_number">
            <p><strong>Credit Card Number</strong>: ${customer.getCcNumber()}</p>
        </div>
        <div id="customer_email">
            <p><strong>Email</strong>: ${customer.getEmail()}</p>
        </div>
        <div id="customer_phone">
            <p><strong>Phone</strong>: ${customer.getPhone()}</p>
        </div>
        <div id="customer_address">
            <p><strong>Address</strong>: ${customer.getAddress()}</p>
        </div>
        <div id="customer_city">
            <p><strong>City</strong>: ${customer.getCity()}</p>
        </div>
        <div id="customer_state">
            <p><strong>State</strong>: ${customer.getState()}</p>
            
        </div>
        <div id="customer_zipcode">
            <p><strong>Zip Code</strong>: ${customer.getZipCode()}</p>
        </div>
    </div>
    
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>  
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/category/categorylist" />'" >Category List</button>
            </li>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/book/booklist" />'" >Book List</button>
            </li>
        </ul>
    </div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>

<!-- Bootbox alerts plugin plugin -->
<script src="<c:url value="/assets/plugins/bootbox/bootbox.min.js" />"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<link href="<c:url value="/assets/bootstrap/css/bootstrap.min.css" />">

<!--FancyBox Plugin css-->
<link rel="stylesheet" href="<c:url value="/assets/plugins/fancybox/source/jquery.fancybox.css" />" type="text/css" media="screen" />

<!--FancyBox Plugin js-->
<script type="text/javascript" src="<c:url value="/assets/plugins/fancybox/source/jquery.fancybox.pack.js" />"></script>