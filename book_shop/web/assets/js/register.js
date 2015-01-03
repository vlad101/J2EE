// csrf token
var csrf;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        // hide CRUD respnse
        $('#ajax_add_customer_response_success').hide();
        $('#ajax_add_customer_response_error').hide();
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
        
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
                            $('#ajax_add_customer_response_success').css({ 'width': '50%', 'margin': '0 auto', 'text-align':'center' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG );
                            $('#add_customer_form').hide();
                            $('#add_customer_form_submit').hide();
                        
//                      clear the text field, after customer is added
                            $('input#customer_first_name_add').val('');
                            $('input#customer_last_name_add').val('');
                            $('input#customer_email_add').val('');
                            $('input#customer_phone_add').val('');
                            $('input#customer_address_add').val('');
                            $('input#customer_city_add').val('');
                            $('input#customer_state_add').val('');
                            $('input#customer_zipcode_add').val('');
                            $('input#customer_cc_number_add').val('');
                        } else {
                            $('#ajax_add_customer_response_error').css({ 'width': '50%', 'margin': '0 auto', 'text-align':'center' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                        }
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);
    });
    
    $(document.body).on('click', '#customer_add_button', function() {
//        clear all text fields        
        $('#add-customer-modal input[name="customer_id_add"]').val('');
        $('#add-customer-modal input[name="customer_first_name_add"]').val('');
        $('#add-customer-modal input[name="customer_last_name_add"]').val('');
        $('#add-customer-modal input[name="customer_email_add"]').val('');
        $('#add-customer-modal input[name="customer_phone_add"]').val('');
        $('#add-customer-modal input[name="customer_address_add"]').val('');
        $('#add-customer-modal input[name="customer_city_add"]').val('');
        $('#add-customer-modal input[name="customer_state_add"]').val('');
        $('#add-customer-modal input[name="customer_zipcode_add"]').val('');
        $('#add-customer-modal input[name="customer_cc_number_add"]').val('');
    });
});