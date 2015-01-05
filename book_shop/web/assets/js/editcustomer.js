// datatable customer list
var oTable;

// csrf token
var csrf;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        $('#customer-list').hide();
        
        // hide CRUD respnse
        $('#ajax_add_customer_response_success').hide();
        $('#ajax_add_customer_response_error').hide();
        
        $('#ajax_update_customer_response_success').hide();
        $('#ajax_update_customer_response_error').hide();
        
        $('#ajax_delete_customer_response_success').hide();
        $('#ajax_delete_customer_response_error').hide();
        
        $('#ajax_redirect_customer_response_error').hide();
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
        
        // update customer table
        getCustomer();
    }
    
    setEventHandlers();
        
    /**
     * The event handler for submit button - add customer.
     * It triggers a ajax POST call to api/v1/customer
     * It will submit a customer entry to a Serendipity database 
     */
    var $add_customer_form = $('#add_customer_form');
    $('#add_customer_form_submit').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $add_customer_form.serializeObject();
        var ajaxObj = {};
        
        ajaxObj = {
                    type: "POST",
                    url: base_url + "/book_shop/api/v1/customer" + csrf,
                    data: JSON.stringify(jsObj),
                    contentType: "application/json",
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                    },
                    success: function(data) {
                        if(data[0].HTTP_CODE == 200) {
                            $('#ajax_add_customer_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                            
//                      clear the text field, after customer is added
                            $('#add-customer-modal').modal('hide');
                            $('input#customer_admin_add').attr('checked', false);
                            $('input#customer_first_name_add').val('');
                            $('input#customer_last_name_add').val('');
                            $('input#customer_username_add').val('');
                            $('input#customer_password1_add').val('');
                            $('input#customer_password2_add').val('');
                            $('input#customer_email_add').val('');
                            $('input#customer_phone_add').val('');
                            $('input#customer_address_add').val('');
                            $('input#customer_city_add').val('');
//                            $('input#customer_state_add').val('');
                            $('#customer_state_add option[selected="selected"').removeAttr('selected');
                            $('#customer_state_add option:first').attr('selected','selected');
                            $('input#customer_zipcode_add').val('');
                            $('input#customer_cc_number_add').val('');
                        } else {
                            $('#ajax_add_customer_response_error').css({ 'width': '100%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                        }
                    },
                    complete: function(XMLHttpRequest) {
                        //console.log(XMLHttpRequest.getAllResponseHeaders());
                        getCustomer();
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);
    });

     /**
     * The event handler for submit button - update customer.
     * It triggers a ajax PUT call to api/v1/customer
     * It will submit a customer entry update to a Serendipity database 
     */
    var $update_customer_form = $('#update_customer_form');
    
    $(document.body).on('click', '#customer_update_button', function(e) {
        var $this = $(this);
        var $tr = $this.closest('tr');
        var row = oTable.row( $tr );
        
        var customer_id = row.data().customer_id;
        var customer_first_name = row.data().first_name.replace(/(<([^>]+)>)/ig,"");
        var customer_last_name = row.data().last_name.replace(/(<([^>]+)>)/ig,"");
        var customer_username = row.data().username;
        var customer_password = row.data().password;
        var customer_admin = row.data().admin;
        var customer_email = row.data().email;
        var customer_phone = row.data().phone;
        var customer_address = row.data().address;
        var customer_city = row.data().city;
        var customer_state = row.data().state;
        var customer_zipcode = row.data().zipcode;
        var customer_cc_number = row.data().cc_number;
        
        $('#update-customer-modal input[name="customer_id_update"]').val(customer_id);
        $('#update-customer-modal input[name="customer_first_name_update"]').val(customer_first_name);
        $('#update-customer-modal input[name="customer_last_name_update"]').val(customer_last_name);
        $('#update-customer-modal input[name="customer_username_update"]').val(customer_username);
        $('#update-customer-modal input[name="customer_password1_update"]').val(customer_password);
        $('#update-customer-modal input[name="customer_password2_update"]').val(customer_password);
        // admin checkbox status - 1 is admin, 0 is public user
        if(customer_admin == 1) {
            $('#update-customer-modal input[name="customer_admin_update"]').attr('checked', true);
        } else {
            $('#update-customer-modal input[name="customer_admin_update"]').attr('checked', false);
        }
        $('#update-customer-modal input[name="customer_email_update"]').val(customer_email);
        $('#update-customer-modal input[name="customer_phone_update"]').val(customer_phone);
        $('#update-customer-modal input[name="customer_address_update"]').val(customer_address);
        $('#update-customer-modal input[name="customer_city_update"]').val(customer_city);
        $('#customer_state_update select').val(customer_state);
        $('#update-customer-modal input[name="customer_zipcode_update"]').val(customer_zipcode);
        $('#update-customer-modal input[name="customer_cc_number_update"]').val(customer_cc_number);
    });
    
    $('#update_customer_form_submit').click(function(e) {
        
        $($update_customer_form).submit(function(){
            e.preventDefault(); // cancel form submit
        }); // submit form
       
        var obj = $update_customer_form.serializeObject();
        updateCustomer(obj);
    });
    
    
     /**
     * The event handler for submit button - update customer.
     * It triggers a ajax PUT call to api/v1/customer
     * It will submit a customer entry update to a Serendipity database 
     */
    
    $(document.body).on('click', '#customer_delete_button', function(e) {
        var $this = $(this);
        var $tr = $this.closest('tr');
        var row = oTable.row( $tr );
        var customer_id = row.data().customer_id;
        
        var obj = {customer_id : customer_id};
        
        // get the name for the alert box
        var customer_name = row.data().first_name;
        customer_name += " " + row.data().last_name;
        bootbox.confirm("Are you sure you want to delete customer " + customer_name, function(result) {
            if(result)
                deleteCustomer(obj);
            else 
                return;
        });
    });
    
//    Initialize the table and child rows
    $('#customer-list-table tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = oTable.row( tr );
 
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( fnFormatDetails( row.data() ) ).show();
            tr.addClass('shown');
        }
    } );
    
    $(document.body).on('click', '#customer_add_button', function() {
//        clear all text fields
        $('#add-customer-modal input[name="customer_admin_add"]').attr('checked', false);
        $('#add-customer-modal input[name="customer_id_add"]').val('');
        $('#add-customer-modal input[name="customer_first_name_add"]').val('');
        $('#add-customer-modal input[name="customer_last_name_add"]').val('');
        $('#add-customer-modal input[name="customer_username_add"]').val('');
        $('#add-customer-modal input[name="customer_password1_add"]').val('');
        $('#add-customer-modal input[name="customer_password2_add"]').val('');
        $('#add-customer-modal input[name="customer_email_add"]').val('');
        $('#add-customer-modal input[name="customer_phone_add"]').val('');
        $('#add-customer-modal input[name="customer_address_add"]').val('');
        $('#add-customer-modal input[name="customer_city_add"]').val('');
//        $('#add-customer-modal input[name="customer_state_add"]').val('');
//        $('#add-customer-modal input[name="customer_state_add"] option:selected').prop('selected',false);
        $('#customer_state_add option[selected="selected"').removeAttr('selected');
        $('#customer_state_add option:first').attr('selected','selected');
        $('#add-customer-modal input[name="customer_zipcode_add"]').val('');
        $('#add-customer-modal input[name="customer_cc_number_add"]').val('');
    });
});

/**
 * Update customer names from the backend using ajax call and json response.
 * 
 * @param {type} obj
 * @returns {jqXHR}
 */
function updateCustomer(obj) {
    
    ajaxObj = {
        
                type: "PUT",
                url: base_url + "/book_shop/api/v1/customer"  + csrf,
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#update-customer-modal').modal('hide');
                        $('#ajax_update_customer_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_update_customer_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getCustomer();
                },
                dataType: "json"  // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Update category names from the backend using ajax call and json response.
 * 
 * @param {type} obj
 * @returns {jqXHR}
 */
function deleteCustomer(obj) {
    
    ajaxObj = {
                type: "DELETE",
                url: base_url + "/book_shop/api/v1/customer" + csrf,
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#ajax_delete_customer_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_delete_customer_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getCustomer();
                },
                dataType: "json"  // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Get customer names from the backend using ajax call and json response.
 */
function getCustomer() {
    
    var d = new Date().getTime();
    
    ajaxObj = {
                type: "GET",
                url: base_url + "/book_shop/api/v1/customer" + csrf,
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },
                success: function(data) {
//                    onBuildCategoryTable(data.categories);

//                  check for HTTP_CODE status from bac-kend
                    doGetCustomerData(data);
                },
                complete: function(XMLHttpRequest) {
                    //console.log(XMLHttpRequest.getAllResponseHeaders());
                },
                dataType: "json" // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * This function gets customer data from back-end.
 * 
 * @param {type} customer_list
 * @returns {undefined}
 */
function doGetCustomerData(customer_list) {
    $('#preloader').hide();
    $('#preloader-text').hide();
    $('#customer-list').show();
    var aaData = [];
    var customer_array = [];
    for(var customer in customer_list) {
        customer_array.push(customer);
    }
    customer_array.sort();
    for(var i in customer_array) {
        customer = customer_array[i];
        
        aaData.push({
            'customer_id':      customer_list[customer].customer_id,
            'first_name':       '<a style="cursor:pointer;" onClick="viewCustomer(' + customer_list[customer].customer_id + ');">' + customer_list[customer].first_name + '</a>',
            'last_name':        '<a style="cursor:pointer;" onClick="viewCustomer(' + customer_list[customer].customer_id + ');">' + customer_list[customer].last_name + '</a>',
            'username':         customer_list[customer].username,
            'password':         customer_list[customer].password,
            'admin':            customer_list[customer].admin,
            'email':            customer_list[customer].email,
            'phone':            customer_list[customer].phone,
            'address':          customer_list[customer].address,
            'city':             customer_list[customer].city,
            'state':            customer_list[customer].state,
            'zipcode':          customer_list[customer].zipcode,
            'cc_number':        customer_list[customer].cc_number,
            'updatebtncol':     '<button type="button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#update-customer-modal" ' + 'id="customer_update_button" value="'  + customer_list[customer].customer_id + '" >Update</button>',
            'deletebtncol':     '<button class="btn btn-danger" id="customer_delete_button" value="' + customer_list[customer].customer_id + '" type="button">Delete</button>'
        });
        
        doBuildDataTable(aaData);
    }
    $('#preloader').hide();
    $('#preloader-text').hide();
    $('#customer-list').show();
}

/**
 * This function build the top level table
 * 
 * @param {type} aaData
 * @returns {undefined}
 */
function doBuildDataTable(aaData) {
    
//    This table loads data by Ajax. The latest data that has been loaded is 
//    shown below. This data will update automatically as any additional data is loaded
    if($.fn.dataTable) {
        oTable = $('#customer-list-table').DataTable({
            'order': [[ 0, "asc" ]],
            'destroy': true, // reloads the table after update
            'data': aaData,
            'aLengthMenu': [[10, 25, 50, -1], [10, 25, 50, 'All']],
            'columns': [
                {
                    'data': null,
                    'class': 'details-control',
                    'defaultContent': ''
                },
                { 'data': 'first_name' },
                { 'data': 'last_name' },
                { 'data': 'updatebtncol' },
                { 'data': 'deletebtncol' }
            ]
        });
    }
}

/*
 * 
 * @param data
 * @returns {String}
 */
function fnFormatDetails( data ) {
    // `data` is the original data object for the row
    
    var retval;
    retval = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    retval += '<tr><td><strong>Admin: </strong>' + (data.admin == 1 ? "Admin" : "Public") + '</td></tr>';
    retval += '<tr><td><strong>Username: </strong>' + data.username + '</td></tr>';
    retval += '<tr><td><strong>Password: </strong>' + data.password + '</td></tr>';
    retval += '<tr><td><strong>Credit Card: </strong>' + data.cc_number + '</td></tr>';
    retval += '<tr><td><strong>Email: </strong>' + data.email + '</td></tr>';
    retval += '<tr><td><strong>Phone: </strong>' + data.phone + '</td></tr>';
    retval += '<tr><td><strong>Address: </strong>' + data.address + '</td></tr>';
    retval += '<tr><td><strong>City: </strong>' + data.city + '</td></tr>';
    retval += '<tr><td><strong>State: </strong>' + data.state + '</td></tr>';
    retval += '<tr><td><strong>Zipcode: </strong>' + data.zipcode + '</td></tr>';
    retval += '</table>';
    return retval;
}

/**
 * Get customer info from the backend using ajax call and json response.
 *
 * @param customer_id
 */

function viewCustomer(customer_id) {
    
    if(customer_id === parseInt(customer_id, 10)) {
        window.location.href = "/book_shop/customer/customer?id=" + customer_id;
    } else {
        $('#ajax_redirect_customer_response_error').css({'width': '50%', 'margin': '0 auto'}).show().html('<strong>Oh snap! Invalid customer id!</strong> ').delay(5000).fadeOut();
    }
}