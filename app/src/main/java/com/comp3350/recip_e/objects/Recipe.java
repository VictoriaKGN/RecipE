package com.comp3350.recip_e.objects;

import java.util.ArrayList;
import com.comp3350.recip_e.objects.Ingredient;
import com.comp3350.recip_e.objects.Instruction;

public class Recipe {

    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Instruction> instructions;
    private String picture; // Filepath
    private int servings; //portions
    private int prepTime; //in minutes
    private int cookTime; //in minutes
    private int currIngredient;
    private int currInstruction;


    /**
     * Recipe constructor, for Recipe with picture
     */
    public Recipe(String newName, ArrayList<Ingredient> newIngredients, ArrayList<Instruction> newInstructions,
                  int serve, int prep, int cook, String picFile) {

        id = 0;
        name = newName;
        ingredients = newIngredients;
        instructions = newInstructions;
        servings = serve;
        prepTime = prep;
        cookTime = cook;
        picture = picFile;
        currIngredient = 0;
        currInstruction = 0;
    }

    /**
     * Recipe constructor, for Recipe without picture
     */
    public Recipe(String newName, ArrayList<Ingredient> newIngredients, ArrayList<Instruction> newInstructions,
                  int serve, int prep, int cook) {

        this(newName, newIngredients, newInstructions, serve, prep, cook, null);
    }


    /**
     * Get the Recipe if
     * @return  The new Recipe id
     */
    public int getID() {
        return id;
    }

    /**
     * Set a new Recipe id; also updates attached Ingredients and Instructions
     * @param  newID: the new Recipe id
     */
    public void setID(int newID) {

        id = newID;

        //update attached Ingredients
        for (Ingredient ing : ingredients) {
            ing.setRecipeID(newID);
        }

        for (Instruction inst : instructions) {
            inst.setRecipeID(newID);
        }
        //update in DB?
    }

    /**
     * Get the name of the recipe
     * @return  Recipe name
     */
    public String getName() {
        return name;
    }

    /**
     * Set a new name for the Recipe
     * @param newName: The new name for the Recipe
     */
    public void setName(String newName) {

        name = newName;
    }

    /**
     * Get a list of all the Ingredients
     * @return  An ArrayList of all the Ingredients
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    //does the Ingredient get vetted before call or in here?
    /****Do we even need this?? Or is this RecipeManager's job to create and swap whole object?****/
    public void updateIngredients(ArrayList<Ingredient> newIngreds) {

        ingredients.clear();
        ingredients = newIngreds;
        for (Ingredient ing : ingredients) {
            ing.setRecipeID(id);
        }
    }

    /**
     * Works in conjunction with the Ingredient iterator
     * @return  True/false about whether it's at the end of the Ingredient list
     */
    public boolean hasNextIngredient() {
        return currIngredient < ingredients.size();
    }

    /**
     * Works as an iterator for the Ingredient list
     * @return  The next Ingredient in the list, or null if out of list
     */
    public Ingredient getNextIngredient() {
        Ingredient currIngred = null;

        if(hasNextIngredient()) {
            currIngred = ingredients.get(currIngredient);
            currIngredient++;
        }

        return currIngred;
    }

    /**
     * Reset the Ingredient iterator
     */
    public void resetNextIngredient() {
        currIngredient = 0;
    }

    /****
     * Do we need delete individual ingredients? Or just swap whole list?
     *
     * public void deleteIngredient(Ingredient ingred) {
     *   ingredients.remove(ingred);
     *
     *    //update DB?
     * }
     ******/

    /**
     * Get a list of all the Instructions
     * @return  An ArrayList of all the Instructions
     */
    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }


    /***don't need this if remaking whole Recipe object*****/
    public void updateInstructions(ArrayList<Instruction> newInstrs) {

        instructions.clear();
        instructions = newInstrs;
        for (Instruction inst : instructions) {
            inst.setRecipeID(id);
        }
    }

    /**
     * Works in conjunction with the Instruction iterator
     * @return  True/false about whether it's at the end of the Instruction list
     */
    public boolean hasNextInstruction() {
        return currInstruction < instructions.size();
    }

    /**
     * Works as an iterator for the Instruction list
     * @return  The next Instruction in the list, or null if out of list
     */
    public Instruction getNextInstruction() {
        Instruction currInstr = null;

        if(hasNextInstruction()) {
            currInstr = instructions.get(currInstruction);
            currInstruction++;
        }

        return currInstr;
    }


    /**
     * Reset the Instruction iterator
     */
    public void resetNextInstruction() {
        currInstruction = 0;
    }

    /**
     * Get the Recipe servings
     * @return  The servings
     */
    public int getServings() {
        return servings;
    }

    /**
     * Set a new Recipe servings
     * @param  newServ: The new servings
     */
    public void setServings(int newServ) {

        servings = newServ;
    }

    /**
     * Get the Recipe prep time
     * @return  The prep time
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * Set a new Recipe prep time
     * @param  newTime: The new prep time
     */
    public void setPrepTime(int newTime) {

        prepTime = newTime;
    }

    /**
     * Get the Recipe cook time
     * @return  The cook time
     */
    public int getCookTime() {

        return cookTime;
    }

    /**
     * Set a new Recipe cook time
     * @param  newTime: The new cook time
     */
    public void setCookTime(int newTime) {

        cookTime = newTime;
    }


    /**
     * Get the Recipe picture
     * @return  The file path to the picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Set a new Recipe picture file path
     * @param  newPath: The new file path to the picture
     */
    public void setPicture(String newPath) {

        picture = newPath;
    }

    /**
     * Checks whether the Recipe has an associated picture
     * @return True/false for whether a path exists
     */
    public Boolean hasPicture() {
        return picture != null;
    }

    /**
     * Check whether two Recipes are the same (that is, have the same id)
     * @return True/false
     */
    public Boolean equals(Recipe r) {
        return this.id == r.getID();
    }
}
