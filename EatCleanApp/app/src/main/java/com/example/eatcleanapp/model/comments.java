package com.example.eatcleanapp.model;

public class comments {
    private String IDUser;
    private String IDRecipes;
    private String Comment;
    private String Username;
    private String Image;
    private String IDComment;

    public comments(String IDUser, String IDRecipes, String comment, String username, String image, String IDComment) {
        this.IDUser = IDUser;
        this.IDRecipes = IDRecipes;
        Comment = comment;
        Username = username;
        Image = image;
        this.IDComment = IDComment;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIDComment() {
        return IDComment;
    }

    public void setIDComment(String IDComment) {
        this.IDComment = IDComment;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }

    public String getIDRecipes() {
        return IDRecipes;
    }

    public void setIDRecipes(String IDRecipes) {
        this.IDRecipes = IDRecipes;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
