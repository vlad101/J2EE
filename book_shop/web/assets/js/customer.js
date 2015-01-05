// csrf token
var csrf;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
        
    }
    
    setEventHandlers();
});
