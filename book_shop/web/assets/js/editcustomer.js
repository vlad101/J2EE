var oTable;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        // hide CRUD respnse
        $('#ajax_add_customer_response_success').hide();
        $('#ajax_add_customer_response_error').hide();
        
        $('#ajax_update_customer_response_success').hide();
        $('#ajax_update_customer_response_error').hide();
        
        $('#ajax_delete_customer_response_success').hide();
        $('#ajax_delete_customer_response_error').hide();
        
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
    $('#submit_add_customer').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $add_customer_form.serializeObject();
        var ajaxObj = {};
        
        ajaxObj = {
                    type: "POST",
                    url: "http://localhost:8080/book_shop/api/v1/customer/",
                    data: JSON.stringify(jsObj),
                    contentType: "application/json",
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                    },
                    success: function(data) {
                        if(data[0].HTTP_CODE == 200) {
                            $('#ajax_add_customer_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                            $('input#customer_title').val('');  // clear the text field, after customer is added
                        } else {
                            $('#ajax_add_customer_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
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
    var $update_category_form = $('#update_category_form');
    
    $(document.body).on('click', '#category_update_button', function(e) {
        var $this = $(this);
        var category_id = $this.val();
        var $tr = $this.closest('tr');
        var category_name = $tr.find('.container_category_name').text();
        
        $('input[name="category_id"]').val(category_id);
        $('input[name="category_name"]').val(category_name);
    });
    
    $('#update_category_form_submit').click(function(e) {
        
        $($update_category_form).submit(function(){
            e.preventDefault(); // cancel form submit
        }); // submit form
       
        var obj = $update_category_form.serializeObject();
        updateCategory(obj);

        $('#update-category-modal').modal('hide');
    });
    
    
     /**
     * The event handler for submit button - update category.
     * It triggers a ajax PUT call to api/v1/category
     * It will submit a category entry update to a Serendipity database 
     */
    
    $(document.body).on('click', '#category_delete_button', function(e) {
        var $this = $(this);
        var category_id = $this.val();
        var obj = {category_id : category_id};
        
        // get the name for the alert box
        var $tr = $this.closest('tr');
        var category_name = $tr.find('.container_category_name').text();
        bootbox.confirm("Are you sure you want to delete " + '"' + category_name + '"'  + " category?", function(result) {
            if(result)
                deleteCategory(obj);
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
});

/**
 * Update customer names from the backend using ajax call and json response.
 * 
 * @param {type} obj
 * @returns {jqXHR}
 */
function updateCategory(obj) {
    
    ajaxObj = {
        
                type: "PUT",
                url: "http://localhost:8080/book_shop/api/v1/category/",
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#ajax_update_category_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_update_category_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
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
function deleteCategory(obj) {
    
    ajaxObj = {
                type: "DELETE",
                url: "http://localhost:8080/book_shop/api/v1/category/",
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#ajax_delete_category_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_delete_category_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
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
 * Get category names from the backend using ajax call and json response.
 */
function getCustomer() {
    
    var d = new Date().getTime();
    
    ajaxObj = {
                type: "GET",
                url: "http://localhost:8080/book_shop/api/v1/customer/",
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
            'first_name':    '<div class="container_first_name" >' + customer_list[customer].first_name + '</div>',
            'last_name':    '<div class="container_last_name" >' + customer_list[customer].last_name + '</div>',
            'email':    '<div class="container_last_name" >' + customer_list[customer].email + '</div>',
            'phone':    '<div class="container_last_name" >' + customer_list[customer].phone + '</div>',
            'address':    '<div class="container_last_name" >' + customer_list[customer].address + '</div>',
            'city':    '<div class="container_last_name" >' + customer_list[customer].city + '</div>',
            'state':    '<div class="container_last_name" >' + customer_list[customer].state + '</div>',
            'zipcode':    '<div class="container_last_name" >' + customer_list[customer].zipcode + '</div>',
            'cc_number':    '<div class="container_last_name" >' + customer_list[customer].cc_number + '</div>',
            'updatebtncol':     '<button type="button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#update-customer-modal" ' + 'id="customer_update_button" value="'  + customer_list[customer].customer_id + '" >Update</button>',
            'deletebtncol':     '<button class="btn btn-danger" id="customer_delete_button" value="' + customer_list[customer].customer_id + '" type="button">Delete</button>'
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

function fnFormatDetails( data ) {
    // `data` is the original data object for the row
    
    var retval;
    retval = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    retval += '<tr><td align="left" width="20%"><em>Credit Card:   </em></td><td align="left">' + data.cc_number + '</td></tr>';
    retval += '<tr><td align="left" width="20%"><em>Email:   </em></td><td align="left">' + data.email + '</td></tr>';
    retval += '<tr><td align="left" width="20%"><em>Phone:   </em></td><td align="left">' + data.phone + '</td></tr>';
    retval += '<tr><td align="left" width="20%"><em>Address:   </em></td><td align="left">' + data.address + '</td></tr>';
    retval += '<tr><td align="left" width="20%"><em>City:   </em></td><td align="left">' + data.city + '</td></tr>';
    retval += '<tr><td align="left" width="20%"><em>State:   </em></td><td align="left">' + data.state + '</td></tr>';
    retval += '<tr><td align="left" width="20%"><em>Zipcode:   </em></td><td align="left">' + data.zipcode + '</td></tr>';
    retval += '</table>';
    return retval;
}