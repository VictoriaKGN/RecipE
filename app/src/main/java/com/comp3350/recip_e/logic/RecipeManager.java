package com.comp3350.recip_e.logic;

import com.comp3350.recip_e.application.Services;

import com.comp3350.recip_e.database.iRecipeManager;

import com.comp3350.recip_e.objects.Recipe;

import java.util.ArrayList;

public class RecipeManager {
    private iRecipeManager database;

    public RecipeManager() {
        database = Services.getRecipePersistence();
    }

    public RecipeManager(final iRecipeManager persistence) {
        this.database = persistence;
    }

    /**
     * Get the first recipe from the database
     * @return  The first recipe in the database
     */
    public Recipe getFirstRecipe() {
        return getRecipe(0);
    }

    /**
     * Gets a single recipe from the database
     *
     * @param id id of the recipe to get
     * @return The recipe with the given id
     */
    public Recipe getRecipe(int id) {
        return database.getRecipe(id);
    }

    /**
     * Adds a recipe to the database
     *
     * @param recipe The recipe to add
     */
    public Recipe addRecipe(Recipe recipe) {
        //RecipeValidator.validate(recipe);
        return database.addRecipe(recipe);
    }

    /**
     * Deletes a recipe from the database
     *
     * @param id The id of the recipe to delete
     */
    public void deleteRecipe(int id) {
        database.delRecipe(id);
    }

    public ArrayList<Recipe> getUserRecipes(String user) {
        return database.getUserRecipes(user);
    }
}
