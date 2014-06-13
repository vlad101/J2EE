$( document ).ready(function() {
    
    /**
     * The event handler for submit button.
     * It triggers a ajax POST call to api/v1/category
     * It will submit a category entry to a Serendipity database 
     */
    var $post_category_form = $('#post_category_form');
    
    $('#submit_category').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $post_category_form.serializeObject(), ajaxObj = {};
        
        ajaxObj = {
            type: "POST",
            url: "http://localhost:8080/book_shop/api/v1/category/",
            data: JSON.stringify(jsObj),
            contentType: "application/json",
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
            },
            success: function(data) {
                if(data[0].HTTP_CODE == 200) {
                    $('#ajax_response').text( data[0].MSG );
                }
            },
            complete: function(XMLHttpRequest) {
                //console.log(XMLHttpRequest.getAllResponseHeaders());
            },
            dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);        
    });
});