package com.comp3350.recip_e.tests.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.comp3350.recip_e.logic.exceptions.InvalidRecipeException;
import com.comp3350.recip_e.logic.RecipeManager;
import com.comp3350.recip_e.objects.Recipe;

import java.util.Objects;

public class RecipeManagerTest {
    private RecipeManager recipeManager;

    @Before
    public void setup() {
        recipeManager = new RecipeManager();
    }

    @Test
    public void testGetFirstRecipe() {
        String name = recipeManager.getFirstRecipe().getName();
        assertEquals("test recipe 1", name);
    }

    @Test
    public void testGetRecipe() {
        // TODO Needs hardcoded test data
        System.out.println("\nStarting testGetRecipe\n");
        Recipe recipe = recipeManager.getRecipe(0);

        assertNotNull(recipe);
        assertEquals(recipe.getID(), 0);

        System.out.println("Finished testGetRecipe");
    }

    @Test
    public void testAddDeleteRecipe() {
        System.out.println("\nStarting testAddDeleteRecipe\n");
        Recipe recipe = new Recipe("name", "ingredients", "instructions", 4, 30, 30);
        int lastRecipeID = 3;

        recipeManager.addRecipe(recipe);
        assertEquals(recipeManager.getRecipe(lastRecipeID).getID(), lastRecipeID);
        recipeManager.deleteRecipe(lastRecipeID);
        assertNull(recipeManager.getRecipe(lastRecipeID));

        System.out.println("Finished testAddDeleteRecipe");
    }

    @Test
    public void testValidateRecipe() {
        System.out.println("\nStarting testValidateRecipe\n");
        InvalidRecipeException e;
        Recipe recipe1 = new Recipe("", "ingredients", "instructions", 1, 1, 1);
        Recipe recipe2 = new Recipe("name", "ingredients", "instructions", 0, 1, 1);
        Recipe recipe3 = new Recipe("name", "ingredients", "instructions", 1, -1, 1);
        Recipe recipe4 = new Recipe("name", "ingredients", "instructions", 1, 1, -1);

        try {
            // Test for null object
            e = assertThrows(InvalidRecipeException.class, () -> recipeManager.addRecipe(null));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("recipe is null."));

            // Test for blank name
            e = assertThrows(InvalidRecipeException.class, () -> recipeManager.addRecipe(recipe1));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("name is blank."));

            // Test for non-positive servings
            e = assertThrows(InvalidRecipeException.class, () -> recipeManager.addRecipe(recipe2));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("servings must be positive and non-zero."));

            // Test for negative prep time
            e = assertThrows(InvalidRecipeException.class, () -> recipeManager.addRecipe(recipe3));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("prep time must be positive."));

            // Test for negative cook time
            e = assertThrows(InvalidRecipeException.class, () -> recipeManager.addRecipe(recipe4));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("cook time must be positive."));
        } catch (NullPointerException npe) {
            fail(npe.getMessage());
        }
    }
}
