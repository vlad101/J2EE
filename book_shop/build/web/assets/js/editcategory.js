$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        // hide CRUD respnse
        $('#ajax_add_category_response_success').hide();
        $('#ajax_add_category_response_error').hide();
        
        $('#ajax_update_category_response_success').hide();
        $('#ajax_update_category_response_error').hide();
        
        $('#ajax_delete_category_response_success').hide();
        $('#ajax_delete_category_response_error').hide();
        
        // update category table
        getCategory();
    }
    
    setEventHandlers();
        
    /**
     * The event handler for submit button - add category.
     * It triggers a ajax POST call to api/v1/category
     * It will submit a category entry to a Serendipity database 
     */
    var $add_category_form = $('#add_category_form');
    $('#submit_add_category').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $add_category_form.serializeObject();
        var ajaxObj = {};
        
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
                            $('#ajax_add_category_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                        } else {
                            $('#ajax_add_category_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                        }
                    },
                    complete: function(XMLHttpRequest) {
                        //console.log(XMLHttpRequest.getAllResponseHeaders());
                        getCategory();
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
});

/**
 * Update category names from the backend using ajax call and json response.
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
                        $('#ajax_update_category_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                    } else {
                        $('#ajax_update_category_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getCategory();
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
                        $('#ajax_delete_category_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                    } else {
                        $('#ajax_delete_category_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getCategory();
                },
                dataType: "json"  // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Get category names from the backend using ajax call and json response.
 */
function getCategory() {
    
    var d = new Date().getTime();
    
    ajaxObj = {
                type: "GET",
                url: "http://localhost:8080/book_shop/api/v1/category/",
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },
                success: function(data) {
                    
                    if(data.categories.length > 0) {
                        onBuildCategoryTable(data.categories);
                    } else {
//                        alert("NO!");
                    }
                },
                complete: function(XMLHttpRequest) {
                    //console.log(XMLHttpRequest.getAllResponseHeaders());
                },
                dataType: "json" // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Format category names into the table.
 * 
 * @param {type} aoCategories
 * @returns {String}
 */
function onBuildCategoryTable(aoCategories) {
    
    for(var i in aoCategories) {
//        update button
//        button trigger modal
        aoCategories[i].updatebtncol = '<button type="button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#update-category-modal" ' + 
                                        'id="category_update_button" value="' 
                                        + aoCategories[i].category_id + '" >Update</button>';
//        delete button
        aoCategories[i].deletebtncol = '<button class="btn btn-danger" id="category_delete_button" value="' 
                                        + aoCategories[i].category_id + '" type="button">Delete</button>';
//        add category name for updating category
        aoCategories[i].category_name = '<div class="container_category_name" >' 
                                        + aoCategories[i].category_name + '</div>';
    }
    
    $('#category-list-table').dataTable({
        'order': [[ 0, "asc" ]],
        'destroy': true, // reloads the table after update
        'data': aoCategories,
        'aLengthMenu': [[10, 25, 50, -1], [10, 25, 50, 'All']],
        'iDsisplayLength': 25,
        'columns': [
            { 'data': 'category_name' },
            { 'data': 'updatebtncol' },
            { 'data': 'deletebtncol' }
        ]
    });
}