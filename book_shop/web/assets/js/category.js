$( document ).ready(function() {
    
    $('[id^=book_id]').change(function(){
        
//        get book quantity
        var stringQty = $(this).attr('id');
        var qty = parseInt(stringQty.split('qty-')[1]);
        
//        get book id
        var bookId = parseInt(stringQty.split('book_id-')[1].split('-qty-')[0]);
        
//        check if number is valid
        if( !/^[1-9]\d*$/g.test($(this).val()) ) {
//            $('table tbody tr td span#book_id-' + bookId + '-invalid-qty').html("<br>Invalid value " + "'" + $(this).val() + "'").css({'color' : 'red'}).delay(5000).fadeOut(400);
            $(this).val(qty);
            return;
        }
        
//        check if quantity is valid
        if($(this).val() > qty) {
           $(this).val(qty);
           $('table tbody tr td span#book_id-' + bookId + '-invalid-qty').html("<br>Only " + qty + " in stock").css({'color' : 'red'});
        } else {
            $(this).val($(this).val());
        }
    });
});