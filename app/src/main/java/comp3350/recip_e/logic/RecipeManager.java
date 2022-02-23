package comp3350.recip_e.logic;

import java.util.List;

import comp3350.recip_e.objects.Recipe;

public class RecipeManager {
    // TODO Store a pointer to the database interface

    public RecipeManager() {
        // TODO Save database pointer
    }

    /**
     * Gets a list of all recipes in the database
     *
     * @return A list recipes in the database
     */
    public List<Recipe> getAllRecipes() {
        // TODO Call database function to get all recipes
        return null;
    }

    /**
     * Gets a single recipe from the database
     *
     * @param id ID of the recipe to get
     * @return The recipe with the given ID
     */
    public Recipe getRecipe(int id) {
        // TODO Call database function to get a recipe by ID
        return null;
    }

    /**
     * Adds a recipe to the database
     *
     * @param recipe The recipe to add
     */
    public void addRecipe(Recipe recipe) {
        // TODO Call database function to add a recipe
    }

    /**
     * Deletes a recipe from the database
     *
     * @param id The ID of the recipe to delete
     */
    public void deleteRecipe(int id) {
        // TODO Call database function to delete the recipe with given ID
    }
}
