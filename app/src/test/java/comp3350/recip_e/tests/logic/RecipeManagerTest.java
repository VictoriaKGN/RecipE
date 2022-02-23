package comp3350.recip_e.tests.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.recip_e.logic.RecipeManager;
import comp3350.recip_e.objects.Recipe;

import java.util.List;

//
public class RecipeManagerTest {
    private RecipeManager recipeManager;

    @Before
    public void setup() {
        recipeManager = new RecipeManager();
    }

    @Test
    public void testGetAllRecipes() {
        // TODO Needs hardcoded test data
        System.out.println("\nStarting testGetAllRecipes\n");
        List<Recipe> recipeList = recipeManager.getAllRecipes();

        assertNotNull(recipeList);

        System.out.println("Finished testGetAllRecipes");
    }

    @Test
    public void testGetRecipe() {
        // TODO Needs hardcoded test data
        System.out.println("\nStarting testGetRecipe\n");
        Recipe recipe = recipeManager.getRecipe(0); // TODO Change id when database has test data

        assertNotNull(recipe);
        assertEquals(recipe.getID(), 0); // TODO Change id when database has test data

        System.out.println("Finished testGetRecipe");
    }

    @Test
    public void testAddDeleteRecipe() {
        System.out.println("\nStarting testAddDeleteRecipe\n");
        Recipe recipe = new Recipe("Recipe", "ingredients", "instructions", 4, 30, 30);

        recipeManager.addRecipe(recipe);
        assertEquals(recipeManager.getRecipe(recipe.getID()).getID(), recipe.getID());
        recipeManager.deleteRecipe(recipe.getID());
        assertNull(recipeManager.getRecipe(recipe.getID()));

        System.out.println("Finished testAddDeleteRecipe");
    }
}
