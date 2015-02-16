// csrf token
var csrf;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        $('#ajax_book_response_error').hide();
        $('#book_id').hide();
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
        
        // get book info
        getBook();
    }
    
    setEventHandlers();
    
    $('.fancybox').fancybox({
        "fitToView": false,
        maxWidth    : 500,
        minHeight   : 500
    });

    $(".fancybox-effects-a").fancybox({
        
        helpers: {
                title : {
                    type : 'outside'
                },
                overlay : {
                    speedOut : 0
                }
        }
    });
});

/**
 * Get book names from the backend using ajax call and json response.
 */
function getBook() {
        
    var d = new Date().getTime();
    
    if($("#book_id").text() === "" || $("#book_id").text() % 1 !== 0) {
        $('#ajax_book_response_error').css({ 'width': '60%', 'margin': '0 auto' }).show().html( '<strong>Oh snap! Invalid book ID! Try again! </strong>' );
        return;
    }
    
    ajaxObj = {
                type: "GET",
                url: base_url + "/book_shop/api/v1/book/" + $("#book_id").text() + csrf,
                data: "ts="+d,
                contentType: "application/json",
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR.responseText);
                    $('#ajax_book_response_error').css({ 'width': '60%', 'margin': '0 auto' }).show().html( '<strong>Oh snap! Invalid book ID! Try again! </strong>' );
                },
                success: function(data) {
//                  check for HTTP_CODE status from back-end
                    if(data.HTTP_CODE == 200) { 
                        doGetBookData(data);
                    } else { 
                        $('#ajax_book_response_error').css({ 'width': '60%', 'margin': '0 auto' }).show().html( '<strong>Oh snap! Invalid book ID! Try again! </strong>' + '</br>' + data[0].MSG );
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
 * This function gets book data from back-end.
 * 
 * @param {type} book_list
 * @returns {undefined}
 */
function doGetBookData(book_list) {
    var book_array = [];
    for(var book in book_list) {
        book_array.push(book);
    }
    
    book_array.sort();

    book = book_array[0];
    
//  get and display book data details
    //alert(book_list[book].book_id);
    $('#page_title').html(book_list[book].title + ' by ' + book_list[book].author);
    
    if (book_list[book].hasOwnProperty("image_path")) {
        var images = '';
        var image_source = '';
        for(var i in book_list[book].image_path) {
//                if image path contains "1_", add it to a default image list and remove a flag
            if(book_list[book].image_path[i].indexOf('1_') !== -1) {
                image_source = "/book_shop/assets/images/book/" + book_list[book].image_path[i].substring(2);
            } else {
                image_source = "/book_shop/assets/images/book/" + book_list[book].image_path[i];
            }
            images = images + '<a class="fancybox" href="' + image_source + '" data-fancybox-group="gallery" ><img src=' + image_source + ' width="140" height="200" alt="Book cover"></a>&nbsp&nbsp';
        }
        $('#book_image').html(images);
    } else {
        image_source = "/book_shop/assets/images/book/no_image.jpg";
        $('#book_image').html('<a class="fancybox" href="' + image_source + '" data-fancybox-group="gallery" ><img src=' + image_source + ' width="140" height="200" alt="Book cover"></a>');
    }
    
    $('#book_title').html('<p><strong>Title</strong>: ' + book_list[book].title + '</p>');
    $('#book_author').html('<p><strong>Author</strong>: ' + book_list[book].author + '</p>');
    $('#book_quantity').html('<p><strong>Quantity</strong>: ' + book_list[book].quantity + '</p>');
    $('#book_category').html('<p><strong>Category</strong>: ' + book_list[book].category_name + '</p>');
    $('#book_price').html('<p><strong>Price</strong>: $' + book_list[book].price + '</p>');
    $('#book_description').html('<p><strong>Description</strong>: ' + book_list[book].description + '</p>');
    $('#book_last_update').html('<p><strong>Last Update</strong>: ' + book_list[book].last_update + '</p>');
};