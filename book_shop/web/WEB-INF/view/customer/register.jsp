<%-- 
    Document   : register
    Created on : Jan 2, 2015, 6:49:51 PM
    Author     : vladimir
--%>

    <!-- custom edit registration page css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/register.css" />" />

    <!-- custom edit registration page js -->
    <script type="text/javascript" src="<c:url value="/assets/js/register.js" />" ></script>
    
<p id="pageTitle">Registration</p>

<div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>

<br>

<div id="centerColumn">
                
    <br><br>
    
<!-- Add Customer Modal -->
            
<!-- error and success message -->
<div id="ajax_add_customer_response_error" class="alert alert-danger"></div>

<!-- adding new customer -->
<div id="ajax_add_customer_response_success" class="alert alert-success"></div>

    <form id="add_customer_form" method="post" action="#">
        <div id="customer_first_name_add">
            <label>First Name</label><br>
            <input type="text" value="" name="customer_first_name_add">
        </div><br>
        <div id="customer_last_name_add">
            <label>Last Name</label><br>
            <input type="text" value="" name="customer_last_name_add">
        </div><br>
        <div id="customer_email_add">
            <label>Email</label><br>
            <input type="text" value="" name="customer_email_add">
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
        <div id="customer_phone_add">
            <label>Phone</label><br>
            <input type="text" value="" name="customer_phone_add">
        </div><br>
        <div id="customer_cc_number_add">
            <label>Credit Card Number</label><br>
            <input type="text" value="" name="customer_cc_number_add">
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
    <button type="button" class="btn btn-primary" id="add_customer_form_submit">Save changes</button>
</div>
<div id="nav-buttons">
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
        </li>
    </ul>
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/categorylist" />'" >Category List</button><br>
        </li>
    </ul>
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/booklist" />'" >Book List</button><br>
        </li>
    </ul>
</div>