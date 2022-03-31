package com.comp3350.recip_e.objects;

import com.comp3350.recip_e.logic.exceptions.InvalidRecipeException;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;
    private String picture; // Filepath
    private int servings; //portions
    private int prepTime; //in minutes
    private int cookTime; //in minutes
    private String userID;
    private int currIngredient;
    private int currInstruction;


    /**
     * Recipe constructor
     * @throws InvalidRecipeException
     */
    public Recipe(String newName, ArrayList<String> newIngredients, ArrayList<String> newInstructions,
                  int serve, int prep, int cook, String picFile, String user) throws InvalidRecipeException {

        id = 0;
        name = newName;
        ingredients = newIngredients;
        instructions = newInstructions;
        servings = serve;
        prepTime = prep;
        cookTime = cook;
        picture = picFile;
        userID = user;
        currIngredient = 0;
        currInstruction = 0;

        validate(this);
    }


    /**
     * Get the Recipe if
     * @return  The new Recipe id
     */
    public int getID() {
        return id;
    }

    /**
     * Set a new Recipe id
     * @param  newID: the new Recipe id
     */
    public void setID(int newID) {

        id = newID;
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
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    /****Do we even need this?? Or is this RecipeManager's job to create and swap whole object?****/
    public void updateIngredients(ArrayList<String> newIngreds) {

        ingredients.clear();
        ingredients = newIngreds;
        resetNextIngredient();
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
     * @return  The next Ingredient in the list, or empty string if out of list
     */
    public String getNextIngredient() {
        String currIngred = "";

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


    /**
     * Get a list of all the Instructions
     * @return  An ArrayList of all the Instructions
     */
    public ArrayList<String> getInstructions() {
        return instructions;
    }


    /***don't need this if remaking whole Recipe object*****/
    public void updateInstructions(ArrayList<String> newInstrs) {

        instructions.clear();
        instructions = newInstrs;
        resetNextInstruction();
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
     * @return  The next Instruction in the list, or empty string if out of list
     */
    public String getNextInstruction() {
        String currInstr = "";

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

    /**
     * Get the Recipe user
     * @return  The user email (identifier)
     */
    public String getUserID() {
        return this.userID;
    }


    /**
     * Validates a Recipe
     *
     * @param recipe Recipe to validate
     * @throws InvalidRecipeException
     */
    private void validate(Recipe recipe) throws InvalidRecipeException {
        if (recipe == null) {
            throw new InvalidRecipeException("recipe is null.");
        } else if (recipe.getName() == null || recipe.getName().equals("")) {
            throw new InvalidRecipeException("name is blank.");
        } else if (recipe.getServings() <= 0) {
            throw new InvalidRecipeException("servings must be positive and non-zero.");
        } else if (recipe.getPrepTime() < 0) {
            throw new InvalidRecipeException("prep time must be positive.");
        } else if (recipe.getCookTime() < 0) {
            throw new InvalidRecipeException("cook time must be positive.");
        }
    }
}
