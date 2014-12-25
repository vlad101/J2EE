var oTable;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        $('#customer_order-list').hide();
        
        // hide CRUD respnse
        $('#ajax_update_customer_order_response_success').hide();
        $('#ajax_update_customer_order_response_error').hide();
        
        $('#ajax_delete_customer_order_response_success').hide();
        $('#ajax_delete_customer_order_response_error').hide();
        
        // update customer table
        getCustomerOrder();
    }
    
    setEventHandlers();
    
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
                url: base_url + "/book_shop/api/v1/customerOrder/",
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
                url: base_url + "/book_shop/api/v1/customerOrder/",
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
                url: base_url + "/book_shop/api/v1/customerOrder/",
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    //console.log(jqXHR.responseText);
                    $('#ajax_delete_customer_order_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap! </strong> There are no customer orders!');
                    $('#preloader').hide();
                    $('#preloader-text').hide();
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
    $('#customer_order-list').hide();
    $('#preloader').show();
    $('#preloader-text').show();
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
            'confirmation_number':  customer_order_list[customer_order].confirmation_number,
            'customer_id':          customer_order_list[customer_order].customer_id,
            'customer_name':        customer_order_list[customer_order].customer_name,
            'book_list':            customer_order_list[customer_order].book_list,
            'updatebtncol':         '<button type="button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#update-customer_order-modal" ' + 'id="customer_order_update_button" value="'  + customer_order_list[customer_order].customer_order_id + '" >Update</button>',
            'deletebtncol':         '<button class="btn btn-danger" id="customer_order_delete_button" value="' + customer_order_list[customer_order].customer_order_id + '" type="button">Delete</button>'
        });
        
        doBuildDataTable(aaData);
    }
    $('#preloader').hide();
    $('#preloader-text').hide();
    $('#customer_order-list').show();
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
    
    if( data.book_list.length === 0 )
        return '<p>No books added!</p>';

    retval = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    for(var i = 0; i < data.book_list.length; i++) {
        retval +=   '<tr>'+
                        '<td><ul><li>' + data.book_list[i] +
                        '</li></ul></td>'+
                    '</tr>';
    }
    retval += '</table>';
    
    return retval;
}