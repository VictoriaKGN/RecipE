package com.comp3350.recip_e.database;

import com.comp3350.recip_e.objects.Recipe;

public interface recipeManager {
    //get the whole recipe information.
    Recipe getRecipe(int recipeId,boolean withPic);

    //get the name of a recipe
    String getRecipeName(int recipeId);

    //get the ingredients of a recipe
    String getIngredients(int recipeId);

    //get directions/introductions of a recipe.
    String getDirection(int recipeId);

    //get the serving size of a recipe
    String getServing(int recipeId);

    //get the prep time of a recipe
    String getPrepTime(int recipeId);

    //get the cook time of a recipe
    String getCookTime(int recipeId);

    //add a new recipe to the fake database( local json file), will return a Recipe Object.
    Recipe addRecipe(Recipe recipe);

    //delete a current recipe from fake database.
    boolean delRecipe(int recipeId);

    //return the path of the local picture depends on the Id, for Iter-1 is one pic each recipe.
    String getCoverPic(int recipeId);
}
