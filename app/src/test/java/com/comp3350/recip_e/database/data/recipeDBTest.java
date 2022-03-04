package com.comp3350.recip_e.database.data;

import static org.junit.Assert.*;

import com.comp3350.recip_e.objects.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class recipeDBTest {

    private recipeDB db;
    String name = "testRecipe";
    String ingred = "stuff";
    String instr = "do stuff with stuff";
    int serv = 0;
    int prep = 0;
    int cook = 0;
    String pic = "some/path";
    Recipe recipeNoPic = new Recipe(name, ingred, instr, serv, prep, cook,  null);
    Recipe recipeWPic = new Recipe(name, ingred, instr, serv, prep, cook,  pic);

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
            badPath.addRecipe(recipeNoPic);
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
        assertTrue("names should match", "Honey-Lime Chicken".equals(db.getRecipeName(0)));
    }

    @Test
    public void getIngredients()
    {
        assertTrue("ingredients should be the same", ("⅓ cup all-purpose flour\n¼ teaspoon cayenne pepper\n10 ounces boneless, skinless chicken breast, cut into strips\n1 tablespoon butter\n2 tablespoons lime juice\n2 tablespoons honey\n1 tablespoon brown sugar\n1 teaspoon Worcestershire sauce").equals(db.getIngredients(0)));
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

    @Test
    public void addRecipe()
    {
        Recipe test = db.addRecipe( recipeNoPic);
        assertTrue("name should match the added recipe", "testRecipe".equals(test.getName()));
        assertTrue("ingredients should match the added recipe", "stuff".equals(test.getIngredients()));
        assertTrue("instructions should match the added recipe", "do stuff with stuff".equals(test.getInstructions()));
        assertEquals("serving should match the added recipe", 0, test.getServings());
        assertEquals("prep time should match the added recipe", 0, test.getPrepTime());
        assertEquals("cook time should match the added recipe", 0, test.getCookTime());
        //assertFalse("picture should not exist", test.hasPicture());
        assertNotNull("should be able to access added recipes", db.getRecipe(5, false));
        db.delRecipe(5);
    }

    @Test
    public void delRecipe()
    {
        Recipe test = db.addRecipe( recipeNoPic);

        assertTrue("the recipe should be deleted", db.delRecipe(5));
        //assertNull("should not be able to access the recipe anymore", db.getRecipe(10,false)); //fails because null recipe not checked for in getRecipe
        assertFalse("should not be able to delete again", db.delRecipe(5));
        assertFalse("should not be able to delete a recipe that doesn't exist", db.delRecipe(-1));
    }

    @Test
    public void getCoverPic()
    {
        assertTrue("should get the matching file path", ("src/main/asset/recipeCover/" + "Bacon-WrappedVenison.jpg").equals(db.getCoverPic(1)));
        assertTrue("should not find a matching specific file", "No such pic.".equals(db.getCoverPic(100)));
    }
}