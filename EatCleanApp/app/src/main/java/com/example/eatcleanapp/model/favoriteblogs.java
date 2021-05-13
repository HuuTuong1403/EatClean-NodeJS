package com.example.eatcleanapp.model;

public class favoriteblogs {
    private String 	IDBlog;
    private String IDUser;
    private String Description;

    public favoriteblogs(String IDBlog, String IDUser, String description) {
        this.IDBlog = IDBlog;
        this.IDUser = IDUser;
        Description = description;
    }

    public String getIDBlog() {
        return IDBlog;
    }

    public void setIDBlog(String IDBlog) {
        this.IDBlog = IDBlog;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
