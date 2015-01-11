// csrf token
var csrf;

$( document ).ready(function() {
    
//    set page event handlers
    function setEventHandlers() {
        
        // hide CRUD respnse
        
        $('#ajax_search_book_title_response_error').hide();
        $('#ajax_search_book_author_response_error').hide();
        $('#ajax_search_category_name_response_error').hide();
        
        // set csrf token value
        csrf = "?csrfPreventionSalt="+ $('#csrf').text();
    }
    
    setEventHandlers();
    
    $('#search-title-button').click(function(e){
        $('#centerColumn .button-list').hide();
        $('#search_book_title_form').css({'display':'inline'});
        $('#search_book_title_form_submit').css({'display':'inline'});
        $('#search-home-button').css({'display':'inline'});
    });
    
    $('#search-author-button').click(function(e){
        $('#centerColumn .button-list').hide();
        $('#search_book_author_form').css({'display':'inline'});
        $('#search_book_author_form_submit').css({'display':'inline'});
        $('#search-home-button').css({'display':'inline'});
    });
    
    $('#search-home-button').click(function(e){
        // hide search button
        $('#search-home-button').css({'display':'none'});
        $('#centerColumn .button-list').show();
        
//        title search
        $('input#book_title_search_form').val('');
        $('#search_book_title_form').css({'display':'none'});
        $('#search_book_title_form_submit').css({'display':'none'});
        $('#book-title-search').css({'display':'none'});
        $('#search-title-result').val('');
        
//        author search
        $('input#book_author_search_form').val('');
        $('#search_book_author_form').css({'display':'none'});
        $('#search_book_author_form_submit').css({'display':'none'});
        $('#book-author-search').css({'display':'none'});
        $('#search-author-result').val('');
    });
    
    /**
     * The event handler for submit button - search book title.
     * It triggers a ajax POST call to api/v1/book
     * It will submit a search book title entry to a Serendipity database 
     */
    var $search_book_title_form = $('#search_book_title_form');
    $('#search_book_title_form_submit').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $search_book_title_form.serializeObject();
        
        var book_title = $('input#book_title_search_form').val();
        if(book_title.trim() == "") {
            return;
        }
        
        var ajaxObj = {};
        ajaxObj = {
                    type: "GET",
                    url: base_url + "/book_shop/api/v1/book/search/title/" + $('input#book_title_search_form').val() + csrf,
                    data: JSON.stringify(jsObj),
                    contentType: "application/json",
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                    },
                    success: function(data) {
                        if(data.HTTP_CODE == 200) {
                            $('#ajax_search_book_title_response_error').hide();
                            $('#ajax_search_book_author_response_error').hide();
                            $('#ajax_search_category_name_response_error').hide();
                            $('#book-title-search').css('display', 'inline');
                            $('#book-name').text($('input#book_title_search_form').val());
                            $('#search_book_title_form').hide();
                            $('#search_book_title_form_submit').hide();
                            doGetSearchByTitleResults(data);
                        
//                      clear the text field, after customer is added
                            $('input#book_title_search').val('');
                        } else {
                            $('#ajax_search_book_title_response_error').css({ 'visibility':'visible', 'width': '60%', 'margin': '0 auto', 'text-align':'center' }).html( '<strong>Oh snap!</strong> ' + data[0].MSG ).show().delay(10000).fadeOut();
                        }
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);
    });
    
    /**
     * The event handler for submit button - search book author.
     * It triggers a ajax POST call to api/v1/book
     * It will submit a search book title entry to a Serendipity database 
     */
    var $search_book_author_form = $('#search_book_author_form');
    $('#search_book_author_form_submit').click(function(e){
        
        e.preventDefault(); // cancel form submit
        
        var jsObj = $search_book_author_form.serializeObject();
        
        var book_author = $('input#book_author_search_form').val();
        if(book_author.trim() == "") {
            return;
        }
        
        var ajaxObj = {};
        ajaxObj = {
                    type: "GET",
                    url: base_url + "/book_shop/api/v1/book/search/author/" + $('input#book_author_search_form').val() + csrf,
                    data: JSON.stringify(jsObj),
                    contentType: "application/json",
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
                    },
                    success: function(data) {
                        if(data.HTTP_CODE == 200) {
                            $('#ajax_search_book_author_response_error').hide();
                            $('#ajax_search_book_author_response_error').hide();
                            $('#ajax_search_category_name_response_error').hide();
                            $('#book-author-search').css('display', 'inline');
                            $('#book-author').text($('input#book_author_search_form').val());
                            $('#search_book_author_form').hide();
                            $('#search_book_author_form_submit').hide();
                            doGetSearchByAuthorResults(data);
                        
//                      clear the text field, after customer is added
                            $('input#book_author_search').val('');
                        } else {
                            $('#ajax_search_book_author_response_error').css({ 'visibility':'visible', 'width': '60%', 'margin': '0 auto', 'text-align':'center' }).html( '<strong>Oh snap!</strong> ' + data[0].MSG ).show().delay(10000).fadeOut();
                        }
                    },
                    dataType: "json" // request json
        };
        
        $.ajax(ajaxObj);
    });
    
});


function doGetSearchByTitleResults(result_list) {
    var data_table = '';
    var table_cells = '';
    var result_array = [];
    for(var result in result_list) {
        result_array.push(result);
    }
    result_array.sort();
    var index = 0;
    for(var i in result_array) {
        result = result_array[i];
        if(result_list[result].book_id !== undefined) {
            table_cells = table_cells + '<tr>';
            table_cells = table_cells + '<td class="lightBlue">';
            table_cells = table_cells + '<img src="/book_shop/assets/images/book/' + result_list[result].image_path + '" height="70" width="50" alt="book image">';
            table_cells = table_cells + '</td>';
            table_cells = table_cells + '<td class="lightBlue">' + result_list[result].title + '</td>';
            table_cells = table_cells + '<td class="lightBlue">' + result_list[result].author + '</td>';
            table_cells = table_cells + '<td class="lightBlue">' + result_list[result].price + '</td>';
            table_cells = table_cells + '<td class="lightBlue"><a href="/book_shop/book/book?id=' + result_list[result].book_id + '">Book Details</a></td>';
            table_cells = table_cells + '</tr>';
            index++;
        }
    }
    if(index == 0) {
        $('#search-title-result').html("<b>No '" + $('input#book_title_search_form').val() +"' book title found!</b>");
    } else {
        data_table = '<table id="bookTable">';
        data_table = data_table + table_cells;
        data_table = data_table + '</table>';
        $('#search-title-result').html(data_table).css({'display':'inline'});
    }
    
}


function doGetSearchByAuthorResults(result_list) {
    var data_table = '';
    var table_cells = '';
    var result_array = [];
    for(var result in result_list) {
        result_array.push(result);
    }
    result_array.sort();
    var index = 0;
    for(var i in result_array) {
        result = result_array[i];
        if(result_list[result].book_id !== undefined) {
            table_cells = table_cells + '<tr>';
            table_cells = table_cells + '<td class="lightBlue">';
            table_cells = table_cells + '<img src="/book_shop/assets/images/book/' + result_list[result].image_path + '" height="70" width="50" alt="book image">';
            table_cells = table_cells + '</td>';
            table_cells = table_cells + '<td class="lightBlue">' + result_list[result].title + '</td>';
            table_cells = table_cells + '<td class="lightBlue">' + result_list[result].author + '</td>';
            table_cells = table_cells + '<td class="lightBlue">' + result_list[result].price + '</td>';
            table_cells = table_cells + '<td class="lightBlue"><a href="/book_shop/book/book?id=' + result_list[result].book_id + '">Book Details</a></td>';
            table_cells = table_cells + '</tr>';
            index++;
        }
    }
    if(index == 0) {
        $('#search-author-result').html("<b>No book by '" + $('input#book_author_search_form').val() +"' found!</b>");
    } else {
        data_table = '<table id="bookTable">';
        data_table = data_table + table_cells;
        data_table = data_table + '</table>';
        $('#search-author-result').html(data_table).css({'display':'inline'});
    }
}