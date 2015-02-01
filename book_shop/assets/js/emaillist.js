// csrf token
var csrf;

$( document ).ready(function() {
    
//    set page event handlers
    function setEventHandlers() {
        
        // hide CRUD respnse
        
        $('#ajax_add_email_response_success').hide();
        $('#ajax_add_email_response_error').hide();
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
    }
    
    setEventHandlers();
    
    /**
     * The event handler for submit button - add email.
     * It triggers a ajax POST call to api/v1/email
     * It will submit a customer email entry to a Serendipity database 
     */
    var $add_email_form = $('#add_email_form');
    $('#add_email_form_submit').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $add_email_form.serializeObject();
            
        var ajaxObj = {};
        
        ajaxObj = {
                    type: "POST",
                    url: base_url + "/book_shop/api/v1/email" + csrf,
                    data: JSON.stringify(jsObj),
                    contentType: "application/json",
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                    },
                    success: function(data) {
                        if(data[0].HTTP_CODE == 200) {
                            $('#ajax_add_email_response_error').hide();
                            $('#intro').hide();
                            $('#ajax_add_email_response_success').css({ 'width': '50%', 'margin': '0 auto', 'text-align':'center' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG );
                            $('#email-add').css('visibility', 'visible');
                            $('#first-name').text($('input#email_first_name_add_form').val());
                            $('#last-name').text($('input#email_last_name_add_form').val());
                            $('#email').text($('input#email_add_form').val());
                            $('#add_email_form').hide();
                            $('#add_email_form_submit').hide();
                        
//                      clear the text field, after customer is added
                            $('input#email_first_name_add').val('');
                            $('input#email_last_name_add').val('');
                            $('input#email_add').val('');
                        } else {
                            $('#ajax_add_email_response_error').css({ 'width': '60%', 'margin': '0 auto', 'text-align':'center' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                        }
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);
    });
    
});