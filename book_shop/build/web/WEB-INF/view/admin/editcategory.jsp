<%-- 
    Document   : editcategory
    Created on : Jun 6, 2014, 8:56:43 PM
    Author     : Vladimir
--%>

<script type="text/javascript" src="/book_shop/assets/jquery/js/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/polyfill.js" />" ></script>
<script type="text/javascript" src="<c:url value="/assets/js/editcategory.js" />" ></script>

<div id="centerColumn">
                
    <br />
    <h1>Edit Category</h1><br />
    
    <!-- Get category -->
    <div id="get_category"></div>
    
    <br /><hr />
    
    <!-- Update category -->
    <form id="put_category_form" name="put_category_form" action="#">
        <table border="1">
            <tr>
                <td>Category title</td>
                <td><input type="text" id="set_category_name" name="category_name" ></td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" id="set_category_id" name="category_id">
                </td>
                <td><input type="submit" id="submit_update_category" ></td>
            </tr>
        </table>
    </form>
    <br />
    <div id="ajax_update_category_response"></div>
    
    <!-- Delete category -->
    
    <div id="ajax_delete_category_response"></div>
    
    <!-- Add category -->
    <form id="post_category_form" name="post_category_form" action="#">
        Add Category  <input type="text" name="category_title" id="category_title" maxlength="50" value="">&nbsp;
                        <input type="button" name="submit_add_category" id="submit_add_category" value="Submit">
    </form>
    <br />
    <div id="ajax_add_category_response"></div>
    
    
                
</div>
            
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>