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
                    <option value="UM">US Minor Outlying Islands</option>
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
        <input type="hidden" value="0" name="customer_admin_add">
    </form>
    <button type="button" class="btn btn-primary" id="add_customer_form_submit">Register</button>
</div>
<div id="nav-buttons">
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
        </li>
    </ul>
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/category/categorylist" />'" >Category List</button><br>
        </li>
    </ul>
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/book/booklist" />'" >Book List</button><br>
        </li>
    </ul>
</div>