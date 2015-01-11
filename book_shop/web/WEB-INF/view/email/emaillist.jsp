<%-- 
    Document   : emaillist
    Created on : Dec 30, 2014, 12:31:38 AM
    Author     : vladimir
--%>


<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/emaillist.css" />" />

<!-- include jquery core files -->
<script type="text/javascript" src="<c:url value="/assets/js/emaillist.js" />" ></script>

<p id="pageTitle">Email List</p>

<div id="csrf" style="visibility: hidden;">${csrfPreventionSalt}</div>

 <div id="centerColumn">
    
    <br><br>
    <p id="intro">Join our email list to receive announcements about new releases and special offers.</p>
    <br>

    <!-- error and success message -->
    <div id="ajax_add_email_response_error" class="alert alert-danger"></div>

    <!-- adding new email -->
    <div id="ajax_add_email_response_success" class="alert alert-success"></div>

    <form id="add_email_form" action="#">
        <div id="email_first_name_add">
            <label>First Name</label><br>
            <input type="text" value="" name="email_first_name_add" id="email_first_name_add_form">
        </div><br>
        <div id="email_last_name_add">
            <label>Last Name</label><br>
            <input type="text" value="" name="email_last_name_add" id="email_last_name_add_form">
        </div><br>
        <div id="email_add">
            <label>Email</label><br>
            <input type="text" value="" name="email_add" id="email_add_form">
        </div><br>
    </form>
    <button type="button" class="btn btn-primary" id="add_email_form_submit">Save changes</button>
    
    <div id="email-add" style="visibility: hidden;" >
        <br>Dear&nbsp;<span id="first-name"></span>&nbsp;<span id="last-name"></span>
        &nbsp;thanks for joining our email list!<br>
        Confirmation email was sent to your email (<span id="email"></span>).
    </div>
</div>
<div id="nav-buttons">
    <ul>
        <li>
            <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
        </li>
    </ul>
</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>