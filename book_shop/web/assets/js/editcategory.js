$( document ).ready(function() {
    
    /**
     * The event handler for submit button - add category.
     * It triggers a ajax POST call to api/v1/category
     * It will submit a category entry to a Serendipity database 
     */
    var $post_category_form = $('#post_category_form');
    
    $('#submit_add_category').click(function(e){
        
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
                            console.log("Added category!");
                            $('#ajax_add_category_response').text( data[0].MSG );
                        } else {
                            console.log("Error adding category!");
                            $('ajax_add_category_response').text( data[0].MSG );
                        }
                    },
                    complete: function(XMLHttpRequest) {
                        //console.log(XMLHttpRequest.getAllResponseHeaders());
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);        
    });
    
    /**
     * The event handler for submit button - update category.
     * It triggers a ajax PUT call to api/v1/category
     * It will submit a category entry update to a Serendipity database 
     */
    
    var $put_category = $('#put_category_form'), $set_category_title = $('set_category_title');
    
    getCategory();
    
});

function getCategory() {
    
    var d = new Date().getTime();
    
    ajaxObj = {
                type: "GET",
                url: "http://localhost:8080/book_shop/api/v1/category/",
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText());
                },
                success: function(data) {
                    var html_string = "";
                    
                    $.each(data, function(id, name){
                        //console.log(val1);
                        html_string = html_string + templateGetInventory(name);
                    });
                    
                    $('#get_category').html("<table border='1'>" + html_string + "</table>");
                },
                complete: function(XMLHttpRequest) {
                    //console.log(XMLHttpRequest.getAllResponseHeaders());
                },
                dataType: "json" // request json
    };
    
    return $.ajax(ajaxObj);
}

function templateGetInventory(category) {
    
    return '<tr>' +
                        '<td class="container_category_title">' + category.category_name + '</td>' +
                        '<td class="container_category_title_button"> <button class="category_update_button" value=" ' + 
                                                    category.category_id + ' " type="button">Update</button> </td>' +
            '</tr>';
    
}

function updateCategory(obj, category_name, code) {
    
    ajaxObj = {
        
                //type: "PUT",
                //url:
        
    };
    
    return $.ajax(ajaxObj);
}