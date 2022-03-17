package com.comp3350.recip_e.objects;

public class Instruction {

    private int recipeID;
    private String instruction;

    public Instruction(String newInstruction) {
        this.recipeID = 0; //updated from associated Recipe object
        this.instruction = newInstruction;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setInstruction(String newInstruction) {
        this.instruction = newInstruction;
    }

    public String getInstruction() {
        return instruction;
    }

    public boolean equals(Instruction other) {
        return (this.recipeID == other.getRecipeID() &&
                this.instruction.equals(other.getInstruction()));
    }
}
