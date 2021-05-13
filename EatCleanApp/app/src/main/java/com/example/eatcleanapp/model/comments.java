package com.example.eatcleanapp.model;

public class comments {
    private String IDUser;
    private String IDRecipes;
    private int Starts;
    private String 	Comment;

    public comments(String IDUser, String IDRecipes, int starts, String comment) {
        this.IDUser = IDUser;
        this.IDRecipes = IDRecipes;
        Starts = starts;
        Comment = comment;
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

    public int getStarts() {
        return Starts;
    }

    public void setStarts(int starts) {
        Starts = starts;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
