package com.example.eatcleanapp.model;

import java.io.Serializable;

public class recipes implements Serializable {
    private String _id;
    private String RecipesTitle;
    private String RecipesAuthor;
    private String RecipesContent;
    private String NutritionalIngredients;
    private String Ingredients;
    private String 	Steps;
    private String IDAuthor;
    private String Status;
    private String Time;
    private String ImageMain;

    public recipes(String _id, String recipesTitle, String recipesAuthor, String recipesContent, String nutritionalIngredients, String ingredients,
                   String steps, String IDAuthor, String status, String time, String imageMain) {
        this._id = _id;
        RecipesTitle = recipesTitle;
        RecipesAuthor = recipesAuthor;
        RecipesContent = recipesContent;
        NutritionalIngredients = nutritionalIngredients;
        Ingredients = ingredients;
        Steps = steps;
        this.IDAuthor = IDAuthor;
        Status = status;
        Time = time;
        ImageMain = imageMain;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getIDAuthor() {
        return IDAuthor;
    }

    public void setIDAuthor(String IDAuthor) {
        this.IDAuthor = IDAuthor;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImageMain() {
        return ImageMain;
    }

    public void setImageMain(String imageMain) {
        ImageMain = imageMain;
    }
}
