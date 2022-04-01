package com.comp3350.recip_e.database;

import com.comp3350.recip_e.objects.Recipe;

import java.util.ArrayList;

public interface iRecipeManager {
    //get the whole recipe information.
    Recipe getRecipe(int recipeId);

    //add a new recipe to the fake database( local json file), will return a Recipe Object.
    Recipe addRecipe(Recipe recipe);

    void updateRecipe(Recipe recipe);

    //delete a current recipe from fake database.
    boolean delRecipe(String user, int recipeId);

    //return the path of the local picture depends on the Id, for Iter-1 is one pic each recipe.
    //String getCoverPic(int recipeId);

    ArrayList<Recipe> getUserRecipes(String user);

    ArrayList<Recipe> searchRecipeByName(String user, String searchKey);

    ArrayList<Recipe> searchRecipeByIngredient(String user, String searchKey);
}
