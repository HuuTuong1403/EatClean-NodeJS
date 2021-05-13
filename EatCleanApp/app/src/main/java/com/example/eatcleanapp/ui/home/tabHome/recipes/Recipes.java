package com.example.eatcleanapp.ui.home.tabHome.recipes;

public class Recipes {
    private int recipesImage;
    private String recipesName;

    public Recipes(int recipesImage, String recipesName) {
        this.recipesImage = recipesImage;
        this.recipesName = recipesName;
    }

    public int getRecipesImage() {
        return recipesImage;
    }

    public void setRecipesImage(int recipesImage) {
        this.recipesImage = recipesImage;
    }

    public String getRecipesName() {
        return recipesName;
    }

    public void setRecipesName(String recipesName) {
        this.recipesName = recipesName;
    }


}
