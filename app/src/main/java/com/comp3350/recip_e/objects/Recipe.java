package com.comp3350.recip_e.objects;


public class Recipe {

    private int id;
    private String name;
    private String ingredients;
    private String instructions;
    private String picture; // Filepath
    private int servings; //portions
    private int prepTime; //in minutes
    private int cookTime; //in minutes


    //with picture
    public Recipe(String newName, String newIngredients, String newInstructions, int serve,
                  int prep, int cook, String picFile) {

        id = 0;
        name = newName;
        ingredients = newIngredients;
        instructions = newInstructions;
        servings = serve;
        prepTime = prep;
        cookTime = cook;
        picture = picFile;
    }

    //no picture
    public Recipe(String newName, String newIngredients, String newInstructions, int serve,
                                                                        int prep, int cook) {

        this(newName, newIngredients, newInstructions, serve, prep, cook, null);
    }



    public int getID() {
        return id;
    }

    public void setID(int newID) {

        id = newID;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {

        name = newName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String newIngred) {

        ingredients = newIngred;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String newInstr) {

        instructions = newInstr;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int newServ) {

        servings = newServ;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int newTime) {

        prepTime = newTime;
    }

    public int getCookTime() {

        return cookTime;
    }

    public void setCookTime(int newTime) {

        cookTime = newTime;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String newPath) {

        picture = newPath;
    }

    public Boolean hasPicture() {
        return picture != null;
    }

    public Boolean equals(Recipe r) {
        return this.id == r.getID();
    }
}
