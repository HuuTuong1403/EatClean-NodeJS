package com.example.eatcleanapp.model;

import java.io.Serializable;

public class blogs implements Serializable {
    private String _id;
    private String BlogTitle;
    private String 	BlogAuthor;
    private String 	BlogContent;
    private String IDAuthor;
    private String ImageMain;
    private String Status;
    private String createdAt;

    public blogs(String _id, String blogTitle, String blogAuthor, String blogContent,
                 String IDAuthor, String imageMain, String status, String createdAt) {
        this._id = _id;
        BlogTitle = blogTitle;
        BlogAuthor = blogAuthor;
        BlogContent = blogContent;
        this.IDAuthor = IDAuthor;
        ImageMain = imageMain;
        Status = status;
        this.createdAt = createdAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBlogTitle() {
        return BlogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        BlogTitle = blogTitle;
    }

    public String getBlogAuthor() {
        return BlogAuthor;
    }

    public void setBlogAuthor(String blogAuthor) {
        BlogAuthor = blogAuthor;
    }

    public String getBlogContent() {
        return BlogContent;
    }

    public void setBlogContent(String blogContent) {
        BlogContent = blogContent;
    }

    public String getIDAuthor() {
        return IDAuthor;
    }

    public void setIDAuthor(String IDAuthor) {
        this.IDAuthor = IDAuthor;
    }

    public String getImageMain() {
        return ImageMain;
    }

    public void setImageMain(String imageMain) {
        ImageMain = imageMain;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
