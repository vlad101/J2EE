package com.serendipity.model;

/**
 *
 * @author Vladimir
 */

public class Image {
    
    private int imageId;
    private String path;
    private int imageIsDefault;
    private int bookId;

    public Image(int imageId, String path, int bookId) {
        this.imageId = imageId;
        this.path = path;
        this.bookId = bookId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public int getImageIsDefault() {
        return imageIsDefault;
    }

    public void setImageIsDefault(int imageIsDefault) {
        this.imageIsDefault = imageIsDefault;
    }

    @Override
    public String toString() {
        return "Image{" + "imageId=" + imageId
                + ", path=" + path + ", bookId=" + bookId + ", imageIsDefault=" + imageIsDefault + '}';
    }
}