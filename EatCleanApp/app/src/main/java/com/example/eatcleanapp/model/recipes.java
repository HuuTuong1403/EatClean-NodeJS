package com.example.eatcleanapp.model;

import java.io.Serializable;

public class recipes implements Serializable {
    private String IDRecipes;
    private String RecipesTitle;
    private String RecipesAuthor;
    private String RecipesContent;
    private String NutritionalIngredients;
    private String Ingredients;
    private String 	Steps;
    private String Time;
    private String Status;
    private String Image;


    public recipes(String IDRecipes, String recipesTitle, String recipesAuthor,
                   String recipesContent, String nutritionalIngredients, String ingredients,
                   String steps, String time, String status, String image) {
        this.IDRecipes = IDRecipes;
        RecipesTitle = recipesTitle;
        RecipesAuthor = recipesAuthor;
        RecipesContent = recipesContent;
        NutritionalIngredients = nutritionalIngredients;
        Ingredients = ingredients;
        Steps = steps;
        Time = time;
        Status = status;
        Image = image;
    }

    public String getIDRecipes() {
        return IDRecipes;
    }

    public void setIDRecipes(String IDRecipes) {
        this.IDRecipes = IDRecipes;
    }

    public String getRecipesTitle() {
        return RecipesTitle;
    }

    public void setRecipesTitle(String recipesTitle) {
        RecipesTitle = recipesTitle;
    }

    public String getRecipesAuthor() {
        return RecipesAuthor;
    }

    public void setRecipesAuthor(String recipesAuthor) {
        RecipesAuthor = recipesAuthor;
    }

    public String getRecipesContent() {
        return RecipesContent;
    }

    public void setRecipesContent(String recipesContent) {
        RecipesContent = recipesContent;
    }

    public String getNutritionalIngredients() {
        return NutritionalIngredients;
    }

    public void setNutritionalIngredients(String nutritionalIngredients) {
        NutritionalIngredients = nutritionalIngredients;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String steps) {
        Steps = steps;
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
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
