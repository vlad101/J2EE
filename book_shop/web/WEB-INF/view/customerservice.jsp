<%-- 
    Document   : customerservice
    Created on : Dec 30, 2014, 12:32:00 AM
    Author     : vladimir
--%>


<link type="text/css" rel="stylesheet" href="<c:url value="/assets/css/customerservice.css" />" />

<!-- include jquery core files -->
<!--<script type="text/javascript" src="<c:url value="/assets/js/admin.js" />" ></script>-->

<p id="pageTitle">Customer Service</p>

<div id="centerColumn">
    <br><br>
    <p>
        We want to back our book collection up with the best possible service. 
        Whether you have a question about what books we offer, need a printed 
        catalog, or need help with an order, just let us know. We'll do everything 
        we can to make it easy and enjoyable for you to do business with us.
    </p>
    <p>
        If you have questions or comments, please contact us in whatever way is 
        most convenient for you. We look forward to hearing from you!
    </p>
    <p><b>Phone:&nbsp;</b>1-800-221-5528</p>
    <p><b>FAX:&nbsp;</b>1-559-440-0963</p>
    <p><b>Email:&nbsp;</b><a href="mailto:${custServEmail}">${custServEmail}</a></p>
    <div id="nav-buttons">
        <ul>
            <li>
                <button type="button" class="btn btn-success btn-small" onclick="location='<c:url value="/index" />'" >Home</button><br>
            </li>
        </ul>
    </div>
</div>
            
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>