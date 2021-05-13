package com.example.eatcleanapp.model;

public class favoriterecipes {
    private String IDRecipes;
    private String IDUser;
    private String Description;

    public favoriterecipes(String IDRecipes, String IDUser, String description) {
        this.IDRecipes = IDRecipes;
        this.IDUser = IDUser;
        Description = description;
    }

    public String getIDRecipes() {
        return IDRecipes;
    }

    public void setIDRecipes(String IDRecipes) {
        this.IDRecipes = IDRecipes;
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
