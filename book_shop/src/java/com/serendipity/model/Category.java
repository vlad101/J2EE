package com.serendipity.model;

/**
 *
 * @author Vladimir
 */
public class Category {
    
    private int categoryId;
    private String name;
    
    public Category(int categoryId, String categoryName){
    
        this.categoryId = categoryId;
        this.name = categoryName;
        
    }
    
    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }
    
    public int getCategoryId(){
        return categoryId;
    }

    public void setCategoryName(String categoryName){
        this.name = categoryName;
    }
    
    public String getCategoryName(){
        return name;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", name=" + name + '}';
    }
}