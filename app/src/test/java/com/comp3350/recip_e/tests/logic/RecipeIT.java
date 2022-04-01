package com.comp3350.recip_e.tests.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.comp3350.recip_e.objects.Recipe;
import com.comp3350.recip_e.tests.utils.TestUtils;
import com.comp3350.recip_e.logic.RecipeManager;
import com.comp3350.recip_e.database.iRecipeManager;
import com.comp3350.recip_e.database.data.recipePersisHsqlDB;
//import db exception?

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RecipeIT {

    private File tempDB;
    private RecipeManager recipeManager;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final iRecipeManager database = new recipePersisHsqlDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.recipeManager = new RecipeManager(database);
    }


    @Test
    public void testGetFirstRecipe() {
        System.out.println("\nStarting testGetFirstRecipe\n");
        String name = recipeManager.getFirstRecipe().getName();
        assertTrue("Bacon-Wrapped Venison Tenderloin with Garlic Cream Sauce".equals(name));
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
        int lastRecipeID = 4;

        Recipe checkRecipe = recipeManager.addRecipe(recipe);
        assertEquals(checkRecipe.getID(), lastRecipeID);
        recipeManager.deleteRecipe(lastRecipeID, "user@email.com");
        assertNull(recipeManager.getRecipe(lastRecipeID));

        System.out.println("Finished testAddDeleteRecipe");
    }


    @Test
    public void testUpdateRecipe() {
        System.out.println("\nStarting testUpdateRecipe\n");

        Recipe oldRecipe = recipeManager.getRecipe(0);
        Recipe updatedRecipe = new Recipe(oldRecipe.getName(), oldRecipe.getIngredients(), oldRecipe.getInstructions(), 100, 100, 100,"path/", "user@email.com");
        updatedRecipe.setID(oldRecipe.getID());

        recipeManager.updateRecipe(updatedRecipe);
        updatedRecipe = recipeManager.getRecipe(0);

        assertEquals("Servings is not as expected", updatedRecipe.getServings(), 100);
        assertEquals("Cook time is not as expected", updatedRecipe.getCookTime(), 100);
        assertEquals("Prep time is not as expected", updatedRecipe.getPrepTime(), 100);

        System.out.println("Finished testUpdateRecipe");
    }

    @Test
    public void testGetUserRecipes() {
        System.out.println("\nStarting testGetUserRecipes\n");

        boolean match = false;

        ArrayList<Recipe> myRecipes = recipeManager.getUserRecipes("dogge@hot.com");
        for (Recipe r : myRecipes) {
            match = (r.getUserID() == null || r.getUserID().equals("dogge@hot.com"));
            assertTrue("User does not match recipe user", match);
        }

        System.out.println("Finished testGetUserRecipes");
    }

    @Test
    public void testSearchRecipeByName() {
        System.out.println("\nStarting testSearchRecipeByName\n");
        ArrayList<Recipe> recipes;

        //recipes = recipeManager.searchRecipeByName("dogge@hot.com","");
        //assertTrue("No recipe with email exist. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByName("dogge@hot.com", "asxaDDDFAfdsaafsdaDdad");
        assertTrue("Nothing should match search key. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByName("dogge@hot.com","Easy Meatloaf");
        assertTrue("Should only return 1 recipe.", recipes.size() == 1);
        assertTrue("Recipe should contain search key.", recipes.get(0).getName().contains("Easy Meatloaf"));
        recipes = recipeManager.searchRecipeByName("dogge@hot.com","and");
        assertTrue("Should return 2 recipes.", recipes.size() == 2);

        System.out.println("Finished testSearchRecipeByName");
    }

    @Test
    public void testSearchRecipeByIngredient() {
        System.out.println("\nStarting testSearchRecipeByIngredient\n");
        ArrayList<Recipe> recipes;

        //recipes = recipeManager.searchRecipeByIngredient("dogge@hot.com","");
        //assertTrue("No recipe with email exist. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByIngredient("dogge@hot.com", "asxaDDDFAfdsaafsdaDdad");
        assertTrue("Nothing should match search key. Should return empty list.", recipes.size() == 0);
        recipes = recipeManager.searchRecipeByIngredient("dogge@hot.com","salt");
        assertTrue("Should return 3 recipes.", recipes.size() == 2);

        System.out.println("Finished testSearchRecipeByIngredient");
    }


    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
