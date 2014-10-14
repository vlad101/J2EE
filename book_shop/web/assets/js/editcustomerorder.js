var oTable;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        // hide CRUD respnse
        $('#ajax_add_customer_order_response_success').hide();
        $('#ajax_add_customer_order_response_error').hide();
        
        $('#ajax_update_customer_order_response_success').hide();
        $('#ajax_update_customer_order_response_error').hide();
        
        $('#ajax_delete_customer_order_response_success').hide();
        $('#ajax_delete_customer_order_response_error').hide();
        
        // update customer table
        getCustomerOrder();
    }
    
    setEventHandlers();
        
    /**
     * The event handler for submit button - add customer order.
     * It triggers a ajax POST call to api/v1/customer
     * It will submit a customer order entry to a Serendipity database 
     */
    var $add_customer_order_form = $('#add_customer_order_form');
    $('#add_customer_order_form_submit').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $add_customer_order_form.serializeObject();
        var ajaxObj = {};
        
        ajaxObj = {
                    type: "POST",
                    url: "http://localhost:8080/book_shop/api/v1/customerOrder/",
                    data: JSON.stringify(jsObj),
                    contentType: "application/json",
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                    },
                    success: function(data) {
                        if(data[0].HTTP_CODE == 200) {
                            $('#ajax_add_customer_order_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                            
//                      clear the text field, after customer order is added
                            $('#add-customer_order-modal').modal('hide');
                            $('input#customer_order_first_name_add').val('');
                            $('input#customer_order_last_name_add').val('');
                            $('input#customer_order_email_add').val('');
                            $('input#customer_order_phone_add').val('');
                            $('input#customer_order_address_add').val('');
                            $('input#customer_order_city_add').val('');
                            $('input#customer_order_state_add').val('');
                            $('input#customer_order_zipcode_add').val('');
                            $('input#customer_order_cc_number_add').val('');
                        } else {
                            $('#ajax_add_customer_order_response_error').css({ 'width': '100%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                        }
                    },
                    complete: function(XMLHttpRequest) {
                        //console.log(XMLHttpRequest.getAllResponseHeaders());
                        getCustomerOrder();
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);
    });

     /**
     * The event handler for submit button - update customer order.
     * It triggers a ajax PUT call to api/v1/customerOrder
     * It will submit a customer order entry update to a Serendipity database 
     */
    var $update_customer_order_form = $('#update_customer_order_form');
    
    $(document.body).on('click', '#customer_order_update_button', function(e) {
        var $this = $(this);
        var $tr = $this.closest('tr');
        var row = oTable.row( $tr );
               
        var customer_order_id = row.data().customer_order_id;
        var customer_order_name = row.data().customer_name;
        var customer_order_confirmation_number = row.data().confirmation_number;
        var customer_order_date_created = row.data().date_created;
        var customer_order_amount = row.data().amount;
                
        $('#update-customer_order-modal input[name="customer_order_id_update"]').val(customer_order_id);
        $('#update-customer_order-modal input[name="customer_order_name_update"]').val(customer_order_name);
        $('#update-customer_order-modal input[name="customer_order_confirmation_number_update"]').val(customer_order_confirmation_number);
        $('#update-customer_order-modal input[name="customer_order_date_created_update"]').val(customer_order_date_created);
        $('#update-customer_order-modal input[name="customer_order_amount_update"]').val(customer_order_amount);
    });
    
    $('#update_customer_order_form_submit').click(function(e) {
        
        $($update_customer_order_form).submit(function(){
            e.preventDefault(); // cancel form submit
        }); // submit form
       
        var obj = $update_customer_order_form.serializeObject();
        updateCustomerOrder(obj);
    });
    
    
     /**
     * The event handler for submit button - update customer order.
     * It triggers a ajax PUT call to api/v1/customerOrder
     * It will submit a category order entry update to a Serendipity database 
     */
    
    $(document.body).on('click', '#customer_order_delete_button', function(e) {
        var $this = $(this);
        var $tr = $this.closest('tr');
        var row = oTable.row( $tr );
        var customer_order_id = row.data().customer_order_id;
        
        var obj = {customer_order_id : customer_order_id};
        
        // get the name for the alert box
        var customer_order_confirmation_number = row.data().confirmation_number;
        
        bootbox.confirm("Are you sure you want to delete customer order " + customer_order_confirmation_number + "?", function(result) {
            if(result)
                deleteCustomerOrder(obj);
            else 
                return;
        });
    });
    
//    Initialize the table and child rows
    $('#customer_order-list-table tbody').on('click', 'td.details-control', function () {
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
    
    $(document.body).on('click', '#customer_order_add_button', function() {
//        clear all text fields        
        $('#add-customer_order-modal input[name="custome_order_id_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_first_name_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_last_name_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_email_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_phone_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_address_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_city_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_state_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_zipcode_add"]').val('');
        $('#add-customer_order-modal input[name="customer_order_cc_number_add"]').val('');
    });
});

/**
 * Update customer order from the backend using ajax call and json response.
 * 
 * @param {type} obj
 * @returns {jqXHR}
 */
function updateCustomerOrder(obj) {
    
    ajaxObj = {
        
                type: "PUT",
                url: "http://localhost:8080/book_shop/api/v1/customerOrder/",
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#update-customer_order-modal').modal('hide');
                        $('#ajax_update_customer_order_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_update_customer_order_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getCustomerOrder();
                },
                dataType: "json"  // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Update customer order from the backend using ajax call and json response.
 * 
 * @param {type} obj
 * @returns {jqXHR}
 */
function deleteCustomerOrder(obj) {
    
    ajaxObj = {
                type: "DELETE",
                url: "http://localhost:8080/book_shop/api/v1/customerOrder/",
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#ajax_delete_customer_order_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_delete_customer_order_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getCustomerOrder();
                },
                dataType: "json"  // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Get cuatomer order category names from the backend using ajax call and json response.
 */
function getCustomerOrder() {
    
    var d = new Date().getTime();
    
    ajaxObj = {
                type: "GET",
                url: "http://localhost:8080/book_shop/api/v1/customerOrder/",
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },
                success: function(data) {
//                    onBuildCategoryTable(data.categories);

//                  check for HTTP_CODE status from bac-kend
                    doGetCustomerOrderData(data);
                },
                complete: function(XMLHttpRequest) {
                    //console.log(XMLHttpRequest.getAllResponseHeaders());
                },
                dataType: "json" // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * This function gets customer order data from back-end.
 * 
 * @param {type} customer_order_list
 * @returns {undefined}
 */
function doGetCustomerOrderData(customer_order_list) {
    var aaData = [];
    var customer_order_array = [];
    for(var customer_order in customer_order_list) {
        customer_order_array.push(customer_order);
    }
    customer_order_array.sort();
    for(var i in customer_order_array) {
        customer_order = customer_order_array[i];
        
        aaData.push({
            'customer_order_id':    customer_order_list[customer_order].customer_order_id,
            'amount':               customer_order_list[customer_order].amount,
            'date_created':         customer_order_list[customer_order].date_created,
            'confirmation_number':   customer_order_list[customer_order].confirmation_number,
            'customer_id':          customer_order_list[customer_order].customer_id,
            'customer_name':        customer_order_list[customer_order].customer_name,
            'updatebtncol':         '<button type="button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#update-customer_order-modal" ' + 'id="customer_order_update_button" value="'  + customer_order_list[customer_order].customer_order_id + '" >Update</button>',
            'deletebtncol':         '<button class="btn btn-danger" id="customer_order_delete_button" value="' + customer_order_list[customer_order].customer_order_id + '" type="button">Delete</button>'
        });
        
        doBuildDataTable(aaData);
    }
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
        oTable = $('#customer_order-list-table').DataTable({
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
                { 'data': 'customer_name' },
                { 'data': 'confirmation_number' },
                { 'data': 'amount' },
                { 'data': 'date_created' },
                { 'data': 'updatebtncol' },
                { 'data': 'deletebtncol' }
            ]
        });
    }
}

function fnFormatDetails( data ) {
    // `data` is the original data object for the row
    
    var retval;
    
    retval +=   '<tr><td><strong>Category: </strong>' + data.category_name + '</td></tr>';
    
    retval = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
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