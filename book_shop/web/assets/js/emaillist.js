$( document ).ready(function() {
    
//    set page event handlers
    function setEventHandlers() {
        
        // hide CRUD respnse
        
        $('#validate_form_success').hide();
        $('#validate_form_error').hide();
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
    }
    
    setEventHandlers();
    
});