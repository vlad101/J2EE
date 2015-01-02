$( document ).ready(function() {
    
//    set page event handlers
    function setEventHandlers() {
        var url;
        
        $("#edit-category-button, #edit-book-button, #edit-customer-button, #edit-customer-order-button").click(function(){
            if(this.id === "edit-category-button") {
                url = '/book_shop/admin/editCategory';
            } else if(this.id === "edit-book-button") {
                url  = '/book_shop/admin/editBook';
            } else if(this.id === "edit-customer-button") {
                url  = '/book_shop/admin/editCustomer';
            } else if(this.id === "edit-customer-order-button") {
                url = '/book_shop/admin/editCustomerOrder';
            }
            window.location.href = url;
            return false;
        });
    }
    
    setEventHandlers();
});