package com.comp3350.recip_e.tests.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.comp3350.recip_e.logic.RecipeManager;
import com.comp3350.recip_e.objects.Recipe;

import java.util.ArrayList;
import java.util.List;
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
        Recipe recipe = new Recipe("name", new ArrayList<>(), new ArrayList<>(), 4, 30, 30, "default/path", "user@email.com");
        int lastRecipeID = 3;

        Recipe checkRecipe = recipeManager.addRecipe(recipe);
        assertEquals(checkRecipe.getID(), lastRecipeID);
        recipeManager.deleteRecipe(lastRecipeID);
        assertNull(recipeManager.getRecipe(lastRecipeID));

        System.out.println("Finished testAddDeleteRecipe");
    }
}
