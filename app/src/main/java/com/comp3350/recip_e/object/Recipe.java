package com.comp3350.recip_e.object;

import java.io.File;
import java.util.Objects;
//import static java.nio.file.StandardCopyOption.*;
//import java.nio.file.Files;


public class Recipe {

    private static int nextID = 0;
    private final int id; //other attributes might change, but not id
    private String name;
    private String ingredients;
    private String instructions;
    private File picture;
    private int servings; //portions
    private int prepTime; //in minutes
    private int cookTime; //in minutes

    //no picture
    public Recipe(String newName, String newIngredients, String newInstructions, int serve,
                                                                        int prep, int cook) {

        id = nextID;
        name = newName;
        ingredients = newIngredients;
        instructions = newInstructions;
        servings = serve;
        prepTime = prep;
        cookTime = cook;
        picture = null;

        nextID++;
    }

    //with picture
    public Recipe(String newName, String newIngredients, String newInstructions, int serve,
                                                            int prep, int cook, File picFile) {

        id = nextID;
        name = newName;
        ingredients = newIngredients;
        instructions = newInstructions;
        servings = serve;
        prepTime = prep;
        cookTime = cook;
        picture = picFile;

        nextID++;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getServings() {
        return servings;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public File getPicture() {
        return picture;
    }

    public Boolean hasPicture() {
        return picture != null;
    }

    public Boolean equals(Recipe r) {
        return this.id == r.getID();
    }
}
