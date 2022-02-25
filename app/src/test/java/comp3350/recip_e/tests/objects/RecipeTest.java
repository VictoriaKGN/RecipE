package comp3350.recip_e.tests.objects;

import org.junit.Test;
import java.io.File;

import comp3350.recip_e.objects.Recipe;

import static org.junit.Assert.*;


public class RecipeTest {

    @Test
    public void testRecipe() {
        Recipe recipe1;
        Recipe recipe2;

        System.out.println("\nBeginning testRecipe\n");

        String name = "Basic Grilled Chicken";
        String ingred = "2 lbs chicken\n1 tsp salt\n1/2 tsp pepper\n1 tbsp olive oil";
        String instr = "1. Rub chicken with salt and pepper\n2. Brush hot grill with oil\n3. Grill chicken";
        int serv = 4;
        int prep = 5;
        int cook = 20;
        File pic = new File("../../../../../../main/assets/images/pic.jpg");

        System.out.println("Testing Recipe creation");

        recipe1 = new Recipe(name, ingred, instr, serv, prep, cook);

        assertNotNull("Recipe1 not created", recipe1);

        assertEquals("Recipe name does not match", name, recipe1.getName());
        assertEquals("Recipe ingredients do not match", ingred, recipe1.getIngredients());
        assertEquals("Recipe instructions do not match", instr, recipe1.getInstructions());
        assertEquals("Recipe servings do not match", serv, recipe1.getServings());
        assertEquals("Recipe prep time does not match", prep, recipe1.getPrepTime());
        assertEquals("Recipe cook time does not match", cook, recipe1.getCookTime());

        System.out.println("Testing Recipe comparisons");

        recipe2 = new Recipe(name, ingred, instr, serv, prep, cook, pic);

        assertNotNull("Recipe2 not created", recipe2);
        assertFalse("Recipe1 should not equal Recipe2", recipe1.equals(recipe2));

        System.out.println("Testing Recipe picture attributes");

        assertNull("Recipe1 picture should return NULL", recipe1.getPicture());
        assertFalse("Recipe1 picture should be NULL", recipe1.hasPicture());
        assertTrue("Recipe2 should have a picture URI", recipe2.hasPicture());

        System.out.println("\nEnd of testRecipe\n");
    }
}
