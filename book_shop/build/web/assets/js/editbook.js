// datatable book list
var oTable;

// CSRF token
var csrf;

// Encoding images to base64 string
var encodedImage;

// typeahead
var categories = [];

//default image list
var defaultImageList = [];

$( document ).ready(function() {
    
//    set page event handlers
    function setEventHandlers() {
    
        $('#book-list').hide();
        
        // hide CRUD respnse
        $('#ajax_add_book_response_success').hide();
        $('#ajax_add_book_response_error').hide();
        
        $('#ajax_update_book_response_success').hide();
        $('#ajax_update_book_response_error').hide();
        
        $('#ajax_delete_book_response_success').hide();
        $('#ajax_delete_book_response_error').hide();
        
        $('#ajax_redirect_book_response_error').hide();
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
        
        // update book table
        getBook();
    }
    
    setEventHandlers();
    
    $('.fancybox').fancybox({
        "fitToView": false,
        maxWidth    : 500,
        minHeight   : 500
    });
        
    /**
     * The event handler for submit button - add book.
     * It triggers a ajax POST call to api/v1/book
     * It will submit a book entry to a Serendipity database 
     */
    var $add_book_form = $('#add_book_form');
    $('#add_book_form_submit').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $add_book_form.serializeObject();
        jsObj["book_image_add"] = encodedImage;
        encodedImage = '';
        //console.info(jsObj);
        var ajaxObj = {};
        
        ajaxObj = {
                    type: "POST",
                    url: base_url + "/book_shop/api/v1/book" + csrf,
                    data: JSON.stringify(jsObj),
                    contentType: "application/json",
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                    },
                    success: function(data) {
                        if(data[0].HTTP_CODE == 200) {
                            $('#ajax_add_book_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                            
//                      clear the text field, after book is added
                            $('#add-book-modal').modal('hide');
                            $('input#book_title_add').val('');
                            $('input#book_author_add').val('');
                            $('input#book_quantity_add').val('');
                            $('input#book_price_add').val('');
                            $('input#book_description_add').val('');
                            $('input#book_category_add').val('');
                            $('input#book_image_add').val('');
                        } else {
                            $('#ajax_add_book_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(10000).fadeOut();
                        }
                    },
                    complete: function(XMLHttpRequest) {
                        //console.log(XMLHttpRequest.getAllResponseHeaders());
                        getBook();
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);
    });
     
     /**
     * The event handler for submit button - update book.
     * It triggers a ajax PUT call to api/v1/book
     * It will submit a book entry update to a Serendipity database 
     */
    var $update_book_form = $('#update_book_form');
    
    $(document.body).on('click', '#book_update_button', function(e) {
        var $this = $(this);
        var book_id = $this.val();
        var $tr = $this.closest('tr');
        var book_title = $tr.find('.container_book_title_update').text();
        var book_author = $tr.find('.container_book_author_update').text();
        var book_quantity = $tr.find('.container_book_quantity_update').text();
        var book_category_name = $tr.find('.container_book_category_name_update').text();
        var book_price = $tr.find('.container_book_price_update').text();
        var book_description = $tr.find('.container_book_description_update').text();
        var book_last_update = $tr.find('.container_book_last_update').text();
        var book_image = $tr.find('.container_book_image_list_update > .fancybox-images').html();
        
//        fill in data for modal book update
        $('#update-book-modal input[name="book_id_update"]').val(book_id);
        $('#update-book-modal input[name="book_title_update"]').val(book_title);
        $('#update-book-modal input[name="book_author_update"]').val(book_author);
        $('#update-book-modal input[name="book_quantity_update"]').val(book_quantity);
        $('#update-book-modal input[name="book_category_name_update"]').val(book_category_name);
        $('#update-book-modal input[name="book_price_update"]').val(book_price);
        $('#update-book-modal input[name="book_description_update"]').val(book_description);
        $('#update-book-modal input[name="book_last_update"]').val(book_last_update);
        $('#book_image_update').val('');
        
        // create radio buttons from the images
        $('#book_image, #book_image_selection').empty();
        $('#book_image').append(book_image);
        var image_update = '';
        
        var i = 0;
        $('#book_image').find('a').each(function() {
            if($(this).attr('href').indexOf('no_image.jpg') === -1) {
                //  keep image path that follows afer the substring /book_shop/assets/images/book/
                var image_path_delete = $(this).attr('href').substring(30);
                image_update = image_update + '<input type="checkbox" name="' + image_path_delete + '" >';
            }
            image_update = image_update + '<label id="image-block" class="image-block" ><input id="' + $(this).attr('href').substring(30).replace(/\..*/,'') + '" type="radio" name="fb" value="small" style="display:none" />';
            image_update = image_update + '<img src="' + $(this).attr('href') + '"  width="100" height="100" alt="Book cover" ></label><br />';
            i++;
        });
            
        $('#book_image_selection').append(image_update).show();
        
//        add the message if the book has images
        if($('#book_image_selection').html().indexOf('no_image.jpg') === -1) {
            $('#book_image_selection').append("<br />Check image to delete.");
            if(i > 1)
                $('#book_image_selection').append("<br />Select image for default.");
        }
        
//        if there is no image, make it default
        jQuery("#no_image").attr('checked', 'checked');

//        select all default images
        for(var default_image in defaultImageList) {
            var image_delete = ("#" + defaultImageList[default_image]).replace(/\..*/,'');
            jQuery(image_delete).attr('checked', 'checked');
        }
    });
    
    $(document.body).on('click', '#book_add_button', function() {
//        clear all text fields
        $('#add-book-modal input[name="book_id_add"]').val('');
        $('#add-book-modal input[name="book_title_add"]').val('');
        $('#add-book-modal input[name="book_author_add"]').val('');
        $('#add-book-modal input[name="book_quantity_add"]').val('');
        $('#add-book-modal input[name="book_category_name_add"]').val('');
        $('#add-book-modal input[name="book_price_add"]').val('');
        $('#add-book-modal input[name="book_description_add"]').val('');
        $('#book_image_add').val('');
    });
    
    $('#update_book_form_submit').click(function(e) {
                
        $($update_book_form).submit(function(){
            e.preventDefault(); // cancel form submit
        }); // submit form
       
//       add image update
        var obj = $update_book_form.serializeObject();
        obj["book_image_update"] = encodedImage;
        encodedImage = '';
//        console.info(obj);
        
//       get selected images to delete
        var selected_delete_image = [];
        $('#book_image_selection input:checkbox').each(function() {
            if($(this).is(":checked")) {
                selected_delete_image.push($(this).attr('name'));
            }
        });
        
        // add list deleted images
        obj["book_image_list_delete_update"] = String(selected_delete_image);
        
//        get selected images to delete
        var selected_default_image = '';
        $('#book_image_selection input:radio').each(function() {
            if($(this).is(":checked")) {
                selected_default_image = String($(this).attr('id'));
            }
        });
//       if there a default image, update it
        if(selected_default_image.indexOf('no_image') === -1) {
            obj["book_image_default_update"] = selected_default_image;
        }
        
        updateBook(obj);
    });
    
    
     /**
     * The event handler for submit button - update book.
     * It triggers a ajax PUT call to api/v1/book
     * It will submit a book entry update to a Serendipity database 
     */
    
    $(document.body).on('click', '#book_delete_button', function(e) {
        var $this = $(this);
        var book_id = $this.val();
        var obj = {book_id : book_id};
        
        // get the name for the alert box
        var $tr = $this.closest('tr');
        var book_title = $tr.find('.container_book_title_update').text();
        bootbox.confirm("Are you sure you want to delete " + '"' + book_title + '"'  + " book?", function(result) {
            if(result)
                deleteBook(obj);
            else 
                return;
        });
    });
    
//    Initialize the table and child rows
    $('#book-list-table tbody').on('click', 'td.details-control', function () {
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
    
//    hide the first column of the table
//    $('#book-list-table tr *:nth-child(4)').css('display','none');
//    $('#book-list-table tbody tr td.hide_qty_column').css('display','none');
    
//    typeahead
    
    $('#book_category_name_add, #book_category_name_update').typeahead({
      hint: true,
      highlight: true,
      minLength: 1
    },
    {
      name: 'categories',
      displayKey: 'value',
      source: substringMatcher(categories),
      templates: {
        empty: [
            '<div class="empty-message">',
            'Unable to find any categories that match the current query. <strong>A new category will be created.</strong>',
             '</div>'
        ].join('\n')
      }
    });
});

/**
 * Update book names from the backend using ajax call and json response.
 * 
 * @param {type} obj
 * @returns {jqXHR}
 */
function updateBook(obj) {
    
    ajaxObj = {
        
                type: "PUT",
                url: base_url + "/book_shop/api/v1/book"  + csrf,
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#update-book-modal').modal('hide');
                        $('#ajax_update_book_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_update_book_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getBook();
                },
                dataType: "json"  // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Update book names from the backend using ajax call and json response.
 * 
 * @param {type} obj
 * @returns {jqXHR}
 */
function deleteBook(obj) {
    
    ajaxObj = {
                type: "DELETE",
                url: base_url + "/book_shop/api/v1/book/",
                data: JSON.stringify(obj),
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },                    
                success: function(data) {
                    if(data[0].HTTP_CODE == 200) {
                        $('#ajax_delete_book_response_success').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Well Done!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    } else {
                        $('#ajax_delete_book_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                    }
                },
                complete: function(XMLHttpRequest) {
                    // reload inventory
                    // console.log( XMLHttpRequest.getAllResponseHeaders() );
                    getBook();
                },
                dataType: "json"  // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * Get book names from the backend using ajax call and json response.
 */
function getBook() {
    
    categories = [];
    getCategoriesTypeAhead();
    
    var d = new Date().getTime();
    
    ajaxObj = {
                type: "GET",
                url: base_url + "/book_shop/api/v1/book" + csrf,
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },
                success: function(data) {
//                    onBuildBookTable(data.books);

//                  check for HTTP_CODE status from bac-kend
                    doGetBookData(data);
                },
                complete: function(XMLHttpRequest) {
                    //console.log(XMLHttpRequest.getAllResponseHeaders());
                },
                dataType: "json" // request json
    };
    
    return $.ajax(ajaxObj);
}

/**
 * This function gets book data from back-end.
 * 
 * @param {type} book_list
 * @returns {undefined}
 */
function doGetBookData(book_list) {
    $('#preloader').show();
    $('#preloader-text').show();
    $('#book-list').hide();
    defaultImageList = [];
    var aaData = [];
    var book_array = [];
    for(var book in book_list) {
        book_array.push(book);
    }
    book_array.sort();
    for(var i in book_array) {
        book = book_array[i];
        
        var images = '<div class="fancybox-images">';
        var image_source = '';
        if (book_list[book].hasOwnProperty("image_path")) {
            for(var i in book_list[book].image_path) {
                
//                if image path contains "1_", add it to a default image list and remove a flag
                if(book_list[book].image_path[i].indexOf('1_') !== -1) {
                    defaultImageList.push(book_list[book].image_path[i].substring(2));
                    image_source = "/book_shop/assets/images/book/" + book_list[book].image_path[i].substring(2);
                } else {
                    image_source = "/book_shop/assets/images/book/" + book_list[book].image_path[i];
                }
                    
                images = images + '<a class="fancybox" href="' + image_source + '" ><img src=' + image_source + ' width="150" height="150" alt="Book cover"></a>&nbsp&nbsp';
            }
        } else {
            image_source = "/book_shop/assets/images/book/no_image.jpg";
            images = images + '<a class="fancybox" href="' + image_source + '" ><img src=' + image_source + ' width="150" height="150" alt="Book cover"></a>';
        }
        images = images + '</div>';
        
        aaData.push({
            'book_id':          book_list[book].book_id,
            'title':            '<div class="container_book_title_update" ><a style="cursor:pointer;" onclick="return viewBook(' + book_list[book].book_id + ');">' + book_list[book].title + '</a></div>',
            'author':           '<div class="container_book_author_update" >' + book_list[book].author + '</div>',
            'qty':              '<div class="container_book_quantity_update" >' + book_list[book].quantity + '</div>',
            'category_name':    '<div class="container_book_category_name_update" >' + book_list[book].category_name + '</div>',
            'price':            '<div class="container_book_price_update" >' + book_list[book].price + '</div>',
            'description':      '<div class="container_book_description_update" >' + book_list[book].description + '</div>',
            'last_update':      '<div class="container_book_last_update" >' +  book_list[book].last_update + '</div>',
            'image':            '<div class="container_book_image_list_update" >' +  images + '</div>',
            'updatebtncol':     '<button type="button" class="btn btn-primary btn-small" data-toggle="modal" data-target="#update-book-modal" ' + 'id="book_update_button" value="'  + book_list[book].book_id + '" >Update</button>',
            'deletebtncol':     '<button class="btn btn-danger" id="book_delete_button" value="' + book_list[book].book_id + '" type="button">Delete</button>'
        });

        doBuildDataTable(aaData);
    }
    $('#preloader').hide();
    $('#preloader-text').hide();
    $('#book-list').show();
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
        oTable = $('#book-list-table').DataTable({
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
                { 'data': 'title' },
                { 'data': 'author' },
                { 
                    'data': 'qty', 
                    'class': 'hide_qty_column' 
                },
                { 
                    'data': 'category_name', 
                    'class': 'hide_category_name_column' 
                },
                { 'data': 'price'},
                { 
                    'data': 'description',
                    'class': 'hide_description_column' 
                },
                {
                    'data': 'last_update',
                    'class': 'hide_last_update_column' 
                },
                {
                    'data': 'image',
                    'class': 'hide_image_column'
                },
                { 'data': 'updatebtncol' },
                { 'data': 'deletebtncol' }
            ]
        });
    }
}

function fnFormatDetails( data ) {
    // data is the original data object for the row
    
    var retval;
    retval = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    retval +=   '<tr><td><strong>Category: </strong>' + data.category_name + '</td></tr>';
    retval +=   '<tr><td><strong>Description: </strong>' + data.description + '</td></tr>';
    retval +=   '<tr><td><strong>Last Update: </strong>' + data.last_update + '</td></tr>';
    retval +=   '<tr><td><strong>Image: </strong>' + data.image + '</td></tr>';
    retval += '</table>';
    return retval;
}

// typeahead substring match
function getCategoriesTypeAhead() {
    
    var d = new Date().getTime();
    ajaxObj = {
                type: "GET",
                url: base_url + "/book_shop/api/v1/category" + csrf,
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },
                success: function(category_list) {
                    var category_array = [];
                    for(var category in category_list) {
                        category_array.push(category);
                    }
                    
                    for(var i in category_array) {
                        category = category_array[i];
                        categories.push(category_list[category].category_name);
                    }
                },
                dataType: "json" // request json
    };
    
    return $.ajax(ajaxObj);
}

var substringMatcher = function(strs) {
  return function findMatches(q, cb) {
    var matches, substringRegex;
 
    // an array that will be populated with substring matches
    matches = [];
 
    // regex used to determine if a string contains the substring `q`
    substrRegex = new RegExp(q, 'i');
 
    // iterate through the pool of strings and for any string that
    // contains the substring `q`, add it to the `matches` array
    $.each(strs, function(i, str) {
      if (substrRegex.test(str)) {
        // the typeahead jQuery plugin expects suggestions to a
        // JavaScript object, refer to typeahead docs for more info
        matches.push({ value: str });
      }
    });
 
    cb(matches);
  };
};

function viewBook(book_id) {
    
    var d = new Date().getTime();
    
    ajaxObj = {
                type: "GET",
                url: base_url + "/book_shop/api/v1/book/" + book_id + csrf,
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                },
                success: function(data) {
                        if(data.HTTP_CODE == 200) {
                            window.location.href = "/book_shop/book?id=" + book_id;
                        } else {
                            $('#ajax_redirect_book_response_error').css({ 'width': '50%', 'margin': '0 auto' }).show().html( '<strong>Oh snap!</strong> ' + data[0].MSG ).delay(5000).fadeOut();
                        }
                },
                complete: function(XMLHttpRequest) {
                    //console.log(XMLHttpRequest.getAllResponseHeaders());
                },
                dataType: "json" // request json
    };
    
    return $.ajax(ajaxObj);
}

function loadImageFileAsURL(imageId){

    var filesSelected = document.getElementById(imageId).files;
    if (filesSelected.length > 0){
        var fileToLoad = filesSelected[0];

        var fileReader = new FileReader();

        fileReader.onload = function(fileLoadedEvent) {
            var srcData = fileLoadedEvent.target.result; // <--- data: base64
            encodedImage = srcData;
        };

        fileReader.readAsDataURL(fileToLoad);
    }
}