package comp3350.recip_e.tests.objects;

import org.junit.Test;

import comp3350.recip_e.objects.Recipe;

import static org.junit.Assert.*;


public class RecipeTest {

    //data for testing purposes
    String name = "Basic Grilled Chicken";
    String ingred = "2 lbs chicken\n1 tsp salt\n1/2 tsp pepper\n1 tbsp olive oil";
    String instr = "1. Rub chicken with salt and pepper\n2. Brush hot grill with oil\n3. Grill chicken";
    int serv = 4;
    int prep = 5;
    int cook = 20;
    String pic = "../../../../../../main/assets/images/pic.jpg";


    @Test
    public void testCreateRecipe() {
        Recipe recipe = new Recipe(name, ingred, instr, serv, prep, cook);

        System.out.println("\nBeginning Recipe Tests\n");
        System.out.println("Testing Recipe creation");

        assertNotNull("Recipe1 not created", recipe);
        assertEquals("Recipe name does not match", name, recipe.getName());
        assertEquals("Recipe ingredients do not match", ingred, recipe.getIngredients());
        assertEquals("Recipe instructions do not match", instr, recipe.getInstructions());
        assertEquals("Recipe servings do not match", serv, recipe.getServings());
        assertEquals("Recipe prep time does not match", prep, recipe.getPrepTime());
        assertEquals("Recipe cook time does not match", cook, recipe.getCookTime());

        System.out.println("Finished testing recipe creation");
    }


    @Test
    public void testCompareRecipes() {
        Recipe recipe1 = new Recipe(name, ingred, instr, serv, prep, cook);
        Recipe recipe2 = new Recipe(name, ingred, instr, serv, prep, cook, pic);

        System.out.println("Testing Recipe comparisons");

        recipe1.setID(0);
        recipe2.setID(1);

        assertNotNull("Recipe2 not created", recipe1);
        assertNotNull("Recipe2 not created", recipe2);
        assertFalse("Recipe1 should not equal Recipe2", recipe1.equals(recipe2));

        System.out.println("Finished testing recipe comparisons");
    }


    @Test
    public void testPicturePathExists() {
        Recipe recipe1 = new Recipe(name, ingred, instr, serv, prep, cook);
        Recipe recipe2 = new Recipe(name, ingred, instr, serv, prep, cook, pic);

        System.out.println("Testing Recipe picture attributes");

        assertNull("Recipe1 picture should return NULL", recipe1.getPicture());
        assertFalse("Recipe1 picture should be NULL", recipe1.hasPicture());
        assertTrue("Recipe2 should have a picture URI", recipe2.hasPicture());

        System.out.println("Finished testing recipe picture attributes");

        System.out.println("\nEnd of testRecipe\n");
    }
}
