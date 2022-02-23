package com.comp3350.recip_e.database;

public interface recipeManager {
    //get the name of a recipe
    String getRecipeName(int recipeId);

    //get the ingredients of a recipe
    String getIngredients(int recipeId);

    //get directions of a recipe
    String getDirection(int recipeId);

    //add a new recipe to the fake database
    boolean addRecipe(int recipeId, String name, String ingre, String direction,int serving);

    //delete a current recipe from fake database
    boolean delRecipe(int recipeId);
}
