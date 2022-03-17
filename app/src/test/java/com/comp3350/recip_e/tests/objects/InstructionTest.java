package com.comp3350.recip_e.tests.objects;

import org.junit.Test;

import com.comp3350.recip_e.objects.Ingredient;
import com.comp3350.recip_e.objects.Instruction;

import static org.junit.Assert.*;

public class InstructionTest {

    private Instruction instruction1, instruction2;
    private String instrs1 = "1. Rub chicken with salt and pepper";
    private String instrs2 = "2. Brush hot grill with oil";
    private int recipeID1 = 100;
    private int recipeID2 = 200;


    private void reset() {
        instruction1 = new Instruction(instrs1);
        instruction2 = new Instruction(instrs2);
    }

    @Test
    public void testCreateInstruction() {
        reset();

        System.out.println("\nBeginning testInstruction\n");
        System.out.println("Testing Instruction creation");

        assertNotNull("Instruction object is null", instruction1);
        assertEquals("Instruction does not match", instrs1, instruction1.getInstruction());

        System.out.println("Finished testing Instruction creation");
    }

    @Test
    public void testUpdateInstruction() {
        reset();

        System.out.println("Testing Instruction update");

        instruction1.setInstruction(instrs2);
        instruction1.setRecipeID(recipeID1);

        assertEquals("Instruction update does not match", instrs2, instruction1.getInstruction());
        assertEquals("Instruction not assigned to correct Recipe", recipeID1, instruction1.getRecipeID());

        System.out.println("Finished testing Instruction update");
    }

    @Test
    public void testCompareInstructions() {
        reset();
        System.out.println("Testing compare Instructions");

        instruction1.setRecipeID(recipeID1);
        instruction2.setRecipeID(recipeID2);

        assertFalse("The instructions should not be equal", instruction1.equals(instruction2));

        instruction2.setInstruction(instrs1);

        assertFalse("The instructions should not be equal", instruction1.equals(instruction2));

        instruction2.setRecipeID(recipeID1);

        assertTrue("Instructions should be equal", instruction1.equals(instruction2));

        System.out.println("Finished testing compare Instructions");
        System.out.println("\nEnd of testInstruction\n");
    }
}
