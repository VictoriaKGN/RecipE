package comp3350.recip_e.logic;

import comp3350.recip_e.application.Services;

import com.comp3350.recip_e.database.recipeManager;

import comp3350.recip_e.objects.Recipe;

public class RecipeManager {
    private recipeManager database;

    public RecipeManager() {
        database = Services.getRecipePersistence();
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
        return database.getRecipe(id, true);
    }

    /**
     * Adds a recipe to the database
     *
     * @param recipe The recipe to add
     */
    public void addRecipe(Recipe recipe) {
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
}
