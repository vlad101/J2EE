// csrf token
var csrf;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        $('#ajax_book_response_error').hide();
        $('#ajax_add_book_response_success').hide();
        $('#ajax_add_book_response_error').hide();
        
        // store and hide book_id
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
    
//    add book to cart
    $('[id^=book_id-]').change(function(){
        
//        get book quantity
        var stringQty = $(this).attr('id');
        var qty = parseInt(stringQty.split('qty-')[1]);
        
//        get book id
        var bookId = parseInt(stringQty.split('book_id-')[1].split('-qty-')[0]);
        
//        check if number is valid
        if( !/^[1-9]\d*$/g.test($(this).val()) ) {
            $(this).val(qty);
            return;
        }
        
//        check if quantity is valid
        if($(this).val() > qty) {
           $(this).val(qty);
           $('#invalid-qty').html("<br><br>Only " + qty + " in stock").css({'color' : 'red'});
        } else {
            $(this).val($(this).val());
        }
    });

    $('[id^=add_book_]').click(function(e){
        
        var customerId = -1;
        var bookId = -1;
        var bookQty = -1;
        
//        get client id
        customerId = $('#customer-id').text().trim();
        
//        get book id
        bookId = $(this).attr('id').replace(/\D/g,'');
        
//        get book qty
        bookQty = $('.book-quantity').val();
        
        if(customerId == -1) {
            window.location.href = "/book_shop/login/login";
            return;
        }
        
        if(bookId != -1 && bookQty != -1) {
            
            var ajaxObj = {
                        type: "POST",
                        url: base_url + "/book_shop/cart/addToCart" + csrf,
                        data: JSON.stringify({'customerId':customerId,'bookId':bookId,'quantity':bookQty}),
                        contentType: "application/json",
                        error: function(jqXHR, textStatus, errorThrown) {
                            console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                        },
                        success: addToCart,
                        dataType: "json" // request json
            };
        
            $.ajax(ajaxObj);
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

function addToCart(content) {
    if(content.add == true) {
        $('#ajax_add_book_response_success').css({ 'width': '60%', 'margin': '0 auto' }).show().html( '<strong>Success! </strong>' );
    } else {
        $('#ajax_add_book_response_error').css({ 'width': '60%', 'margin': '0 auto' }).show().html( '<strong>Oh snap! ' + content.error +'!' + ' Try again! </strong>' );
    }
}