// csrf token
var csrf;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
//        hide CRUD response
        $('#ajax_update_customer_response_error').hide();
        $('#ajax_update_customer_response_success').hide();
        
//        set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
        
    }
    
    setEventHandlers();
    
     /**
     * The event handler for submit button - update customer.
     * It triggers a ajax PUT call to api/v1/customer
     * It will submit a customer entry update to a Serendipity database 
     */
    var $update_customer_form = $('#update_customer_form');
    
    $(document.body).on('click', '#customer_update_button', function(e) {
        var customer_state = $('#state').text();
        $('#customer_state_update select').val(customer_state);
    });
    
    $('#update_customer_form_submit').click(function(e) {
        
        $($update_customer_form).submit(function(){
            e.preventDefault(); // cancel form submit
        }); // submit form
       
        var obj = $update_customer_form.serializeObject();
        updateCustomer(obj);
    });
    
    /**
     * Update customer info using ajax call and json response.
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
                            location.reload();
                        } else {
                            $('#ajax_update_customer_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                        }
                    },
                    complete: function(XMLHttpRequest) {
                        // reload inventory
                         console.log( XMLHttpRequest.getAllResponseHeaders() );
//                        location.reload();
                    },
                    dataType: "json"  // request json
        };

        return $.ajax(ajaxObj);
    }

});
