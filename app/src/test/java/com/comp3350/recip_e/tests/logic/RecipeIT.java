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
        int lastRecipeID = 3;

        Recipe checkRecipe = recipeManager.addRecipe(recipe);
        assertEquals(checkRecipe.getID(), lastRecipeID);
        recipeManager.deleteRecipe(lastRecipeID);
        assertNull(recipeManager.getRecipe(lastRecipeID));

        System.out.println("Finished testAddDeleteRecipe");
    }


    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
