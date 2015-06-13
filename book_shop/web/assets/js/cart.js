// CSRF token
var csrf;

$( document ).ready(function() {
    
    function setEventHandlers() {
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
    }
    
    setEventHandlers();
    
    $('[id^=book_id]').change(function(){
 
//        get book quantity
        var stringQty = $(this).attr('id');
        var qty = parseInt(stringQty.split('qty-')[1]);
        
//        check if number is valid
        if( !/^[0-9]\d*$/g.test($(this).val()) ) {
            $(this).val(qty);
            return;
        }
    });
    
    $('[id^=update_cart_],[id^=delete_cart_]').click(function(e){
        
        var customerId = -1;
        var bookId = -1;
        var bookQty = -1;
        
//        get client id
        customerId = $('#customer-id').text().trim();
        
//        get book id
        bookId = $(this).attr('id').replace(/\D/g,'');
        
//        get book qty
        bookQty = $(this).closest("tr").find('td:eq(3)').find('.book-quantity').val();
        
//        if delete button is pressed, set book qty to 0
        if($(this).attr('id').indexOf('delete_cart_')  !== -1) {
            bookQty = 0;
        }
        
        if(customerId === -1) {
            window.location.href = "/book_shop/login/login";
            return;
        }
        
        if(bookId !== -1 && bookQty !== -1) {
            var ajaxObj = {
                        type: "POST",
                        url: base_url + "/book_shop/cart/updateCart" + csrf,
                        data: JSON.stringify({'customerId':customerId,'bookId':bookId,'quantity':bookQty}),
                        contentType: "application/json",
                        error: function(jqXHR, textStatus, errorThrown) {
                            console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                        },
                        success: updateCart,
                        dataType: "json" // request json
            };
        
            $.ajax(ajaxObj);
        }
    });
});

function updateCart(content) {
//    if(content.update == true && content.hasOwnProperty("quantity")) {
    if(content.update == true) {
            window.location.href = '/book_shop/cart/cart';
    } else {
        alert(content.error);
    }
}