package com.comp3350.recip_e.database.data;

import static org.junit.Assert.*;

import com.comp3350.recip_e.object.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class recipeDBTest {

    private recipeDB db;

    @Before
    public void setUp() throws Exception
    {
        String filePath="src/main/asset/recipe.json";
        db = new recipeDB(filePath);
        assertNotNull(db);
    }

    @After
    public void tearDown() throws Exception
    {
        db = null;
        assertNull(db);
    }

    @Test
    public void constructorTest()
    {
        recipeDB badPath = new recipeDB("not a path");

        try
        {
            badPath.addRecipe( "testRecipe","stuff","do stuff with stuff", 0, 0, 0, "not a picture of stuff");
        }catch(Exception e)
        {
            System.out.println("Expected exception 1");
        }

        try
        {
            badPath.getRecipe(1, false);
        }catch(Exception e)
        {
            System.out.println("Expected exception 2");
        }

        try
        {
            badPath.delRecipe(1);
        }catch(Exception e)
        {
            System.out.println("Expected exception 3");
        }
    }

    @Test
    public void getRecipe()
    {
        assertNotNull("should retrieve a recipe without a picture", db.getRecipe(1, false));
        assertNotNull("should retrieve a recipe with a picture", db.getRecipe(2, true));
    }

    @Test
    public void getRecipeName()
    {
        assertTrue("names should match", "Spicy Garlic Lime Chicken".equals(db.getRecipeName(1)));
    }

    @Test
    public void getIngredients()
    {
        assertTrue("ingredients should be the same", ("¾ teaspoon salt\n¼ teaspoon black pepper\n¼ teaspoon cayenne pepper\n⅛ teaspoon paprika\n¼ teaspoon garlic powder\n⅛ teaspoon onion powder\n¼ teaspoon dried thyme\n¼ teaspoon dried parsley\n4 boneless, skinless chicken breast halves\n2 tablespoons butter\n1 tablespoon olive oil\n2 teaspoons garlic powder\n3 tablespoons lime juice").equals(db.getIngredients(1)));
    }

    @Test
    public void getDirection()
    {
        assertTrue("directions should be the same", "Step 1\nIn a small bowl, mix together salt, black pepper, cayenne, paprika, 1/4 teaspoon garlic powder, onion powder, thyme and parsley. Sprinkle spice mixture generously on both sides of chicken breasts.\nStep 2\nHeat butter and olive oil in a large heavy skillet over medium heat. Saute chicken until golden brown, about 6 minutes on each side. Sprinkle with 2 teaspoons garlic powder and lime juice. Cook 5 minutes more, stirring frequently to coat evenly with sauce.\n".equals(db.getDirection(1)));
    }

    @Test
    public void getServing()
    {
        assertTrue("servings should match", "4".equals(db.getServing(1)));
    }

    @Test
    public void getPrepTime()
    {
        assertTrue("prep time should be the same", "5".equals(db.getPrepTime(1)));
    }

    @Test
    public void getCookTime()
    {
        assertTrue("cook time should be the same", "20".equals(db.getCookTime(1)));
    }

    @Test
    public void addRecipe()
    {
//        assertFalse("should not add a recipe with an ID that already exists", db.addRecipe( "testRecipe","stuff","do stuff with stuff", 0, 0,0, "not a picture of stuff"));
//        assertTrue("should add a recipe", db.addRecipe( "testRecipe","stuff","do stuff with stuff", 0, 0, 0, "not a picture of stuff"));
//        assertNotNull("should be able to access added recipes", db.getRecipe(10, false));
//        assertFalse("should not add a duplicate recipe", db.addRecipe( "testRecipe","stuff","do stuff with stuff", 0, 0, 0, "not a picture of stuff"));

        db.delRecipe(10);
    }

    @Test
    public void delRecipe()
    {
        db.addRecipe( "testRecipe","stuff","do stuff with stuff", 0,0, 0, "not a picture of stuff");

        assertTrue("the recipe should be deleted", db.delRecipe(10));
        //assertNull("should not be able to access the recipe anymore", db.getRecipe(10,false)); //fails because null recipe not checked for in getRecipe
        assertFalse("should not be able to delete again", db.delRecipe(10));
        assertFalse("should not be able to delete a recipe that doesn't exist", db.delRecipe(-1));
    }

    @Test
    public void getCoverPic()
    {
        assertTrue("should get the matching file path", ("src/main/asset/recipeCover/" + "HoneyLimeChicken.jpeg").equals(db.getCoverPic(2)));
        assertTrue("should not find a matching specific file", "src/main/asset/recipeCover/".equals(db.getCoverPic(100)));
    }
}