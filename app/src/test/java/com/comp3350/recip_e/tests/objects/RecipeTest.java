package com.comp3350.recip_e.tests.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.comp3350.recip_e.logic.exceptions.InvalidRecipeException;
import com.comp3350.recip_e.objects.Recipe;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;


public class RecipeTest {

    //data for testing purposes
    private Recipe recipe1, recipe2;
    private String name = "Basic Grilled Chicken";
    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> instructions = new ArrayList<>();
    private int serv = 4;
    private int prep = 5;
    private int cook = 20;
    private String pic = "../../../../../../main/assets/images/pic.jpg";
    private String user = "user@email.com";
    private String[] ingreds = {"2 lbs chicken", "1/4C onions", "200ml sauce"};
    private String[] instrs = {"1. Rub chicken with salt and pepper", "2. Brush hot grill with oil", "3. Grill chicken"};


    private void reset() {

        ingredients.clear();
        instructions.clear();

        for (int i = 0; i < 3; i++) {
            ingredients.add(ingreds[i]);
            instructions.add(instrs[i]);
        }

        recipe1 = new Recipe(name, ingredients, instructions, serv, prep, cook, null, user);
        recipe2 = new Recipe(name, ingredients, instructions, serv, prep, cook, pic, user);
    }


    @Test
    public void testCreateRecipe() {
        reset();

        System.out.println("\nBeginning Recipe Tests\n");
        System.out.println("Testing Recipe creation");

        assertNotNull("Recipe not created", recipe1);
        assertEquals("Recipe name does not match", name, recipe1.getName());

        ArrayList<String> tempIngred = recipe1.getIngredients();
        ArrayList<String> tempInstr = recipe1.getInstructions();

        for (int i = 0; i < 3; i++) {
            assertTrue("Ingredient " + i + " does not match", ingredients.get(i).equals(tempIngred.get(i)));
            assertTrue("Instruction " + i + " does not match", instructions.get(i).equals(tempInstr.get(i)));
        }

        assertEquals("Recipe servings do not match", serv, recipe1.getServings());
        assertEquals("Recipe prep time does not match", prep, recipe1.getPrepTime());
        assertEquals("Recipe cook time does not match", cook, recipe1.getCookTime());
        assertTrue("User does not match", user.equals(recipe1.getUserID()));


        System.out.println("Finished testing Recipe creation");
    }


    @Test
    public void testValidateRecipe() {
        System.out.println("Starting testValidateRecipe");
        InvalidRecipeException e;

        try {
            // Test for blank name
            e = assertThrows(InvalidRecipeException.class, () -> new Recipe("", ingredients, instructions, serv, prep, cook, pic, user));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("name is blank."));

            // Test for non-positive servings
            e = assertThrows(InvalidRecipeException.class, () -> new Recipe(name, ingredients, instructions, -1, prep, cook, pic, user));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("servings must be positive and non-zero."));

            // Test for negative prep time
            e = assertThrows(InvalidRecipeException.class, () -> new Recipe(name, ingredients, instructions, serv, -1, cook, pic, user));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("prep time must be positive."));

            // Test for negative cook time
            e = assertThrows(InvalidRecipeException.class, () -> new Recipe(name, ingredients, instructions, serv, prep, -1, pic, user));
            assertTrue(Objects.requireNonNull(e.getMessage()).contains("cook time must be positive."));
        } catch (NullPointerException npe) {
            fail(npe.getMessage());
        }

        System.out.println("End of testValidateRecipe");
    }


    @Test
    public void testCompareRecipes() {
        reset();

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

        reset();

        System.out.println("Testing Recipe picture attributes");

        assertNull("Recipe1 picture should return NULL", recipe1.getPicture());
        assertFalse("Recipe1 picture should be NULL", recipe1.hasPicture());
        assertTrue("Recipe2 should have a picture URI", recipe2.hasPicture());

        System.out.println("Finished testing recipe picture attributes");

    }


    @Test
    public void testGetNextIngredient() {
        reset();

        System.out.println("Testing Ingredient iterator");

        int i = 0;
        while(recipe1.hasNextIngredient()) {
            String myIngredient = recipe1.getNextIngredient();
            assertTrue("Iterator ingredients do not match", myIngredient.equals(ingreds[i]));
            i++;
        }

        recipe1.resetNextIngredient();
        assertTrue("Iterator did not reset to first ingredient",
                recipe1.getNextIngredient().equals(ingreds[0]));

        System.out.println("Finished testing Ingredient iterator");

    }


    @Test
    public void testGetNextInstruction() {
        reset();

        int i = 0;
        while(recipe1.hasNextInstruction()) {
            String myInstruction = recipe1.getNextInstruction();
            assertTrue("Iterator instructions do not match", myInstruction.equals(instrs[i]));
            i++;
        }

        recipe1.resetNextInstruction();
        assertTrue("Iterator did not reset to first instruction",
                recipe1.getNextInstruction().equals(instrs[0]));

        System.out.println("Finished testing Instruction iterator");
        System.out.println("\nEnd of testRecipe\n");
    }
}
