package com.example.eatcleanapp.model;

public class comments {
    private String _id;
    private String IDRecipe;
    private String Comment;
    private String Username;
    private String Image;
    private String IDUser;


    public comments(String _id, String IDRecipe, String comment, String username, String image, String IDUser) {

        this._id = _id;
        this.IDRecipe = IDRecipe;
        Comment = comment;
        Username = username;
        Image = image;
        this.IDUser = IDUser;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIDRecipe() {
        return IDRecipe;
    }

    public void setIDRecipe(String IDRecipe) {
        this.IDRecipe = IDRecipe;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }
}
