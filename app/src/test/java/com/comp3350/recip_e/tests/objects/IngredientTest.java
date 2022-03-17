package com.comp3350.recip_e.tests.objects;

import org.junit.Test;

import com.comp3350.recip_e.objects.Ingredient;

import static org.junit.Assert.*;

public class IngredientTest {

    private Ingredient ingredient1, ingredient2;
    private String ingred1 = "2 lbs chicken";
    private String ingred2 = "1/4 C onions";
    private int recipeID1 = 100;
    private int recipeID2 = 200;


    private void reset() {
        ingredient1 = new Ingredient(ingred1);
        ingredient2 = new Ingredient(ingred2);
    }

    @Test
    public void testCreateIngredient() {
        reset();

        System.out.println("\nBeginning testIngredient\n");
        System.out.println("Testing Ingredient creation");

        //Ingredient ingredient = new Ingredient(ingred1);

        assertNotNull("Ingredient object is null", ingredient1);
        assertEquals("Ingredient name does not match", ingred1, ingredient1.getIngredientData());

        System.out.println("Finished testing Ingredient creation");
    }

    @Test
    public void testUpdateIngredient() {
        reset();

        System.out.println("Testing Ingredient update");

        //Ingredient ingredient = new Ingredient(ingred1);
        ingredient1.setIngredientData(ingred2);
        ingredient1.setRecipeID(recipeID1);

        assertEquals("Ingredient update does not match", ingred2, ingredient1.getIngredientData());
        assertEquals("Ingredient not assigned to correct Recipe", recipeID1, ingredient1.getRecipeID());

        System.out.println("Finished testing Ingredient update");
    }

    @Test
    public void testCompareIngredients() {
        reset();
        System.out.println("Testing compare Ingredients");

        //Ingredient ingredient1 = new Ingredient(ingred1);
        ingredient1.setRecipeID(recipeID1);
        //Ingredient ingredient2 = new Ingredient(ingred2);
        ingredient2.setRecipeID(recipeID2);

        assertFalse("The ingredients should not be equal", ingredient1.equals(ingredient2));

        ingredient2.setIngredientData(ingred1);

        assertFalse("The ingredients should not be equal", ingredient1.equals(ingredient2));

        ingredient2.setRecipeID(recipeID1);

        assertTrue("Ingredients should be equal", ingredient1.equals(ingredient2));

        System.out.println("Finished testing compare Ingredients");
        System.out.println("\nEnd of testIngredient\n");
    }

}
