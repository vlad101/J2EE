// datatable book list
var oTable;

$( document ).ready(function() {

//    set page event handlers
    function setEventHandlers() {
        
        $('#ajax_book_response_error').hide();
        $('#book_id').hide();
        
        // get book info
        getBook();
    }
    
    setEventHandlers();
    
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
                url: "http://localhost:8080/book_shop/api/v1/book/" + $("#book_id").text(),
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
    $('#page_title').html('Edit ' + book_list[book].title + ' by ' + book_list[book].author);
    
    if (book_list[book].hasOwnProperty("image_path")) {
        var images = '';
        for(var i in book_list[book].image_path) {
            images = images + '<img src="/book_shop/assets/images/book/' + book_list[book].image_path[i] + '" width="150" height="260" alt="Book cover">&nbsp&nbsp';
        }
        $('#book_image').html(images);
    } else {
        $('#book_image').html('<img src="/book_shop/assets/images/book/no_image.jpg" width="150" height="260" alt="Book cover">');
    }
    
    $('#book_title').html('<p><strong>Title</strong>: ' + book_list[book].title + '</p>');
    $('#book_author').html('<p><strong>Author</strong>: ' + book_list[book].author + '</p>');
    $('#book_quantity').html('<p><strong>Quantity</strong>: ' + book_list[book].quantity + '</p>');
    $('#book_category').html('<p><strong>Category</strong>: ' + book_list[book].category_name + '</p>');
    $('#book_price').html('<p><strong>Price</strong>: $' + book_list[book].price + '</p>');
    $('#book_description').html('<p><strong>Description</strong>: ' + book_list[book].description + '</p>');
    $('#book_last_update').html('<p><strong>Last Update</strong>: ' + book_list[book].last_update + '</p>');
};