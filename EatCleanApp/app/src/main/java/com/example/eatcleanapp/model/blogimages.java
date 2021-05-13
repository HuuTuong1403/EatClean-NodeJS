package com.example.eatcleanapp.model;

public class blogimages {
    private String IDBlogImages;
    private String BlogImages;
    private String IDBlog;

    public blogimages(String IDBlogImages, String blogImages, String IDBlog) {
        this.IDBlogImages = IDBlogImages;
        BlogImages = blogImages;
        this.IDBlog = IDBlog;
    }

    public String getIDBlogImages() {
        return IDBlogImages;
    }

    public void setIDBlogImages(String IDBlogImages) {
        this.IDBlogImages = IDBlogImages;
    }

    public String getBlogImages() {
        return BlogImages;
    }

    public void setBlogImages(String blogImages) {
        BlogImages = blogImages;
    }

    public String getIDBlog() {
        return IDBlog;
    }

    public void setIDBlog(String IDBlog) {
        this.IDBlog = IDBlog;
    }
}
