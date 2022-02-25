package com.comp3350.recip_e.database;

public interface recipeManager {
    //get the name of a recipe
    String getRecipeName(int recipeId);

    //get the ingredients of a recipe
    String getIngredients(int recipeId);

    //get directions/introductions of a recipe.
    String getDirection(int recipeId);

    //get the serving size of a recipe
    String getServing(int recipeId);

    //add a new recipe to the fake database( local json file)
    boolean addRecipe(int recipeId, String name, String ingre, String direction,int serving,String cover);

    //delete a current recipe from fake database.
    boolean delRecipe(int recipeId);

    //return the path of the local picture depends on the Id, for Iter-1 is one pic each recipe.
    String getCoverPic(int recipeId);
}
