package com.comp3350.recip_e.tests.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.comp3350.recip_e.database.data.RecipeStub;
import com.comp3350.recip_e.logic.RecipeManager;
import com.comp3350.recip_e.objects.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeManagerTest {
    private RecipeManager recipeManager;
    private RecipeStub database;

    @Before
    public void setup() {
        database = new RecipeStub();
        recipeManager = new RecipeManager(database);
    }

    @Test
    public void testGetFirstRecipe() {
        String name = recipeManager.getFirstRecipe().getName();
        assertTrue("test recipe 1".equals(name));
    }

    @Test
    public void testGetRecipe() {
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
        recipeManager.deleteRecipe(lastRecipeID, "");
        assertNull(recipeManager.getRecipe(lastRecipeID));

        System.out.println("Finished testAddDeleteRecipe");
    }

    @Test
    public void testSearchRecipeByName() {
        System.out.println("\nStarting testSearchRecipeByName\n");
        ArrayList<Recipe> recipes;

        recipes = recipeManager.searchRecipeByName("fake@gmail.com","");
        assertTrue("No recipe with email exist. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByName("user@email.com", "asxaDDDFAfdsaafsdaDdad");
        assertTrue("Nothing should match searchy key. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByName("user@email.com","1");
        assertTrue("Should only return 1 recipe.", recipes.size() == 1);
        assertTrue("Recipe should contain search key.", recipes.get(0).getName().contains("1"));
        recipes = recipeManager.searchRecipeByName("user@email.com","test recipe");
        assertTrue("Should return 3 recipes.", recipes.size() == 3);

        System.out.println("Finished testSearchRecipeByName");
    }

    @Test
    public void testSearchRecipeByIngredient() {
        System.out.println("\nStarting testSearchRecipeByIngredient\n");
        ArrayList<Recipe> recipes;

        recipes = recipeManager.searchRecipeByIngredient("fake@gmail.com","");
        assertTrue("No recipe with email exist. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByIngredient("user@email.com", "asxaDDDFAfdsaafsdaDdad");
        assertTrue("Nothing should match searchy key. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByIngredient("user@email.com","ml ingredients");
        assertTrue("Should return 3 recipes.", recipes.size() == 3);

        System.out.println("Finished testSearchRecipeByIngredient");
    }
}
