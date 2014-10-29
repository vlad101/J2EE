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
    var aaData = [];
    var book_array = [];
    for(var book in book_list) {
        book_array.push(book);
    }
    
    book_array.sort();

    book = book_array[0];

    alert(book_list[book].book_id);
    alert(book_list[book].title);
    alert(book_list[book].author);
    alert(book_list[book].quantity);
    alert(book_list[book].category_name);
    alert(book_list[book].price);    
    alert(book_list[book].description);
    alert(book_list[book].last_update);  

    aaData.push({
        'book_id':          book_list[book].book_id,
        'title':            '<div class="container_book_title_update" ><a href="#" onclick="return viewBook(' + book_list[book].book_id + ');">' + book_list[book].title + '</a></div>',
        'author':           '<div class="container_book_author_update" >' + book_list[book].author + '</div>',
        'qty':              '<div class="container_book_quantity_update" >' + book_list[book].quantity + '</div>',
        'category_name':    '<div class="container_book_category_name_update" >' + book_list[book].category_name + '</div>',
        'price':            '<div class="container_book_price_update" >' + book_list[book].price + '</div>',
        'description':      '<div class="container_book_description_update" >' + book_list[book].description + '</div>',
        'last_update':      '<div class="container_book_last_update" >' +  book_list[book].last_update + '</div>'
    });
};