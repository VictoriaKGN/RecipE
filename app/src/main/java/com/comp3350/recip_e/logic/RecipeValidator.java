package com.comp3350.recip_e.logic;

import com.comp3350.recip_e.logic.exceptions.InvalidRecipeException;
import com.comp3350.recip_e.objects.Recipe;

public class RecipeValidator {
    /**
     * Validates a Recipe
     *
     * @param recipe Recipe to validate
     * @throws InvalidRecipeException
     */
    public static void validate(Recipe recipe) throws InvalidRecipeException {
        if (recipe == null) {
            throw new InvalidRecipeException("recipe is null.");
        } else if (recipe.getName() == null || recipe.getName().equals("")) {
            throw new InvalidRecipeException("name is blank.");
        } else if (recipe.getServings() <= 0) {
            throw new InvalidRecipeException("servings must be positive and non-zero.");
        } else if (recipe.getPrepTime() < 0) {
            throw new InvalidRecipeException("prep time must be positive.");
        } else if (recipe.getCookTime() < 0) {
            throw new InvalidRecipeException("cook time must be positive.");
        }
    }
}
