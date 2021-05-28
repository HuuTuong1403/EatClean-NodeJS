package com.example.eatcleanapp.model;

public class blogs {
    private String IDBlog;
    private String BlogTitle;
    private String 	BlogAuthor;
    private String 	BlogContent;
    private String Time;
    private String Status;

    public blogs(String IDBlog, String blogTitle, String blogAuthor,
                 String blogContent, String time, String status) {
        this.IDBlog = IDBlog;
        BlogTitle = blogTitle;
        BlogAuthor = blogAuthor;
        BlogContent = blogContent;
        Time = time;
        Status = status;
    }

    public String getIDBlog() {
        return IDBlog;
    }

    public void setIDBlog(String IDBlog) {
        this.IDBlog = IDBlog;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
