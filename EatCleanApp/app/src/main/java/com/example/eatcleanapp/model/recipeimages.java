package com.example.eatcleanapp.model;

public class recipeimages {
    private String 	IDRecipesImages;
    private String RecipesImages;
    private String IDRecipes;

    public recipeimages(String IDRecipesImages, String recipesImages, String IDRecipes) {
        this.IDRecipesImages = IDRecipesImages;
        RecipesImages = recipesImages;
        this.IDRecipes = IDRecipes;
    }

    public String getIDRecipesImages() {
        return IDRecipesImages;
    }

    public void setIDRecipesImages(String IDRecipesImages) {
        this.IDRecipesImages = IDRecipesImages;
    }

    public String getRecipesImages() {
        return RecipesImages;
    }

    public void setRecipesImages(String recipesImages) {
        RecipesImages = recipesImages;
    }

    public String getIDRecipes() {
        return IDRecipes;
    }

    public void setIDRecipes(String IDRecipes) {
        this.IDRecipes = IDRecipes;
    }
}
