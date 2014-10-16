$( document ).ready(function() {
    
//    set page event handlers
    function setEventHandlers() {
        $('#edit-category-button').click(function(){
            window.location.href = '/book_shop/admin/editCategory';
            return false;
        });

        $('#edit-book-button').click(function(){
            window.location.href = '/book_shop/admin/editBook';
            return false;
        });

        $('#edit-customer-button').click(function(){
            window.location.href = '/book_shop/admin/editCustomer';
            return false;
        });

        $('#edit-customer-order-button').click(function(){
            window.location.href = '/book_shop/admin/editCustomerOrder';
            return false;
        });
    }
    
    setEventHandlers();
});