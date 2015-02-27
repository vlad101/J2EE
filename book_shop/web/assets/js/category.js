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
           $('table tbody tr td span#book_id-' + bookId + '-invalid-qty').html("<br>Only " + qty + " in stock").css({'color' : 'red'});
           return;
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
        bookQty = $(this).closest("tr").find('td:eq(5)').find('.book-quantity').val();
        
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

function addToCart(content) {
    if(content.add == true) {
        alert('Very Good');
    } else {
        alert(content.error);
    }
}