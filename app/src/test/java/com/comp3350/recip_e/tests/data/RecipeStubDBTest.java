package com.comp3350.recip_e.tests.data;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.comp3350.recip_e.database.data.RecipeStub;
import com.comp3350.recip_e.objects.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RecipeStubDBTest {

    private RecipeStub db;
    String name1 = "test recipe 1";
    String name2 = "test recipe 2";
    ArrayList<String> ingred = new ArrayList<>();
    ArrayList<String> instr = new ArrayList<>();
    int serv = 1;
    int prep = 1;
    int cook = 1;
    String pic = "some/path";
    Recipe recipeNoPic;
    Recipe recipeWPic;

    @Before
    public void setUp()
    {
        db = new RecipeStub();
        assertNotNull(db);

        for (int i = 1; i <= 3; i++) {
            ingred.add((i*100) + "ml ingredients");
            instr.add(i + ". Test instruction.");
        }

        recipeNoPic = new Recipe(name1, ingred, instr, serv, prep, cook);
        recipeWPic = new Recipe(name2, ingred, instr, serv, prep, cook,  pic);
        recipeNoPic.setID(0);
        recipeWPic.setID(1);
    }

    @After
    public void tearDown()
    {
        db = null;
        assertNull(db);
    }


    @Test
    public void getRecipe()
    {
        System.out.println("\nBeginning recipe stub database tests\n");

        assertNotNull("should retrieve a recipe without a picture", db.getRecipe(0, false));
        assertNotNull("should retrieve a recipe with a picture", db.getRecipe(1, true));
    }

    /*****
    @Test
    public void getRecipeName()
    {
        assertTrue("names should match", name1.equals(db.getRecipeName(0)));
    }

    @Test
    public void getIngredients()
    {
        ArrayList<String> gotIngredients = db.getIngredients(0);
        for (int i = 0; i < 3; i++)
        {
            assertTrue("ingredients should be the same",ingred.get(i).equals());
        }
    }

    @Test
    public void getDirection()
    {
        assertTrue("directions should be the same", "Step 1:\nCombine flour and cayenne pepper in a large resealable plastic bag. Add a few chicken strips at a time and shake to coat.\nStep 2:\nMelt butter in a large, nonstick skillet over medium heat. Add chicken and cook until browned on all sides, 4 to 6 minutes.\nStep 3:\nCombine lime juice, honey, brown sugar, and Worcestershire sauce in a bowl; pour over the chicken. Continue to cook until juices run clear and the sauce is thickened, 3 to 5 more minutes.\n".equals(db.getDirection(0)));
    }

    @Test
    public void getServing()
    {
        assertTrue("servings should match", "2".equals(db.getServing(0)));
    }

    @Test
    public void getPrepTime()
    {
        assertTrue("prep time should be the same", "10".equals(db.getPrepTime(0)));
    }

    @Test
    public void getCookTime()
    {
        assertTrue("cook time should be the same", "10".equals(db.getCookTime(0)));
    }
     ******/

    @Test
    public void addRecipe()
    {
        Recipe test = db.addRecipe(recipeWPic);

        assertNotNull("should be able to access added recipes", db.getRecipe(3, true));
        assertTrue("name should match the added recipe", name2.equals(test.getName()));

        ArrayList<String> testIngreds = test.getIngredients();
        ArrayList<String> testInstrs = test.getInstructions();

        for (int i = 0; i < 3; i++)
        {
            assertTrue("ingredients should match the added recipe", ingred.get(i).equals(testIngreds.get(i)));
            assertTrue("ingredients should match the added recipe", instr.get(i).equals(testInstrs.get(i)));
        }

        assertEquals("serving should match the added recipe", serv, test.getServings());
        assertEquals("prep time should match the added recipe", prep, test.getPrepTime());
        assertEquals("cook time should match the added recipe", cook, test.getCookTime());
        assertTrue("picture should exist", test.hasPicture());

        db.delRecipe(3);
    }

    @Test
    public void delRecipe()
    {
        Recipe test = db.addRecipe(recipeNoPic);

        assertTrue("the recipe should be deleted", db.delRecipe(3));
        assertNull("should not be able to access the recipe anymore", db.getRecipe(3,false));
        assertFalse("should not be able to delete again", db.delRecipe(3));
        assertFalse("should not be able to delete a recipe that doesn't exist", db.delRecipe(-1));

        System.out.println("\nEnd of recipe stub database tests\n");
    }

    /***
    @Test
    public void getCoverPic()
    {
        assertTrue("should get the matching file path", ("src/main/asset/recipeCover/" + "Bacon-WrappedVenison.jpg").equals(db.getCoverPic(1)));
        assertTrue("should not find a matching specific file", "No such pic.".equals(db.getCoverPic(100)));
    }
    ***/
}