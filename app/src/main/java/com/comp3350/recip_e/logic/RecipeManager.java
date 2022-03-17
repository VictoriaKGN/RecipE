package com.comp3350.recip_e.logic;

import com.comp3350.recip_e.application.Services;

import com.comp3350.recip_e.database.recipeManager;

import com.comp3350.recip_e.objects.Recipe;
import com.comp3350.recip_e.objects.Ingredient;
import com.comp3350.recip_e.objects.Instruction;

import java.util.List;

public class RecipeManager {
    private recipeManager database;

    public RecipeManager() {
        database = Services.getRecipePersistence();
    }

    /**
     * Get the first recipe from the database
     *
     * @return The first recipe in the database
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
        return database.getRecipe(id, true);
    }

    /**
     * Adds a recipe to the database
     *
     * @param recipe The recipe to add
     */
    public void addRecipe(Recipe recipe) throws InvalidRecipeException {
        RecipeValidator.validate(recipe);
        database.addRecipe(recipe);
    }

    /**
     * Deletes a recipe from the database
     *
     * @param id The id of the recipe to delete
     */
    public void deleteRecipe(int id) {
        database.delRecipe(id);
    }

    /**
     * Search for recipes in the database by name
     *
     * @param searchKey  String used to perform a partial match against a recipe's name
     * @return  A list of all recipes that contain searchKey in the name
     */
    public List<Recipe> searchRecipeByName(String searchKey) {
        // TODO Call database search by name function
        return null;
    }

    /**
     * Search for recipes in the database by ingredient
     *
     * @param searchKey    String used to perform a partial match against a recipe's ingredients
     * @return  A list of all recipes that have an ingredient that contains searchKey
     */
    public List<Recipe> searchRecipeByIngredient(String searchKey) {
        // TODO Call database search by ingredient function
        return null;
    }
}
