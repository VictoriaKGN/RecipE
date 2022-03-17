package com.comp3350.recip_e.objects;

public class Ingredient {

    private int recipeID;
    private String ingredientData; //ingredient and amount

    public Ingredient(String newIngred) {
        this.recipeID = 0; //updated from associated Recipe object
        this.ingredientData = newIngred;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setIngredientData(String newIngred) {
        this.ingredientData = newIngred;
    }

    public String getIngredientData() {
        return ingredientData;
    }

    public boolean equals(Ingredient ingred) {
        return (this.recipeID == ingred.getRecipeID() &&
                this.ingredientData.equals(ingred.getIngredientData()));
    }

}
