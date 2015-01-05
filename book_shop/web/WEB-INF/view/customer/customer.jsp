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
    
    <div id="centerColumn">
    </div>

        <!-- Get book details -->
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
    
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>  
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/categorylist" />'" >Category List</button>
            </li>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/booklist" />'" >Book List</button>
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