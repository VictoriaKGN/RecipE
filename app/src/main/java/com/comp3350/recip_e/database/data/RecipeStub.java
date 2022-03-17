package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.recipeManager;
import com.comp3350.recip_e.objects.Recipe;
import com.comp3350.recip_e.objects.Ingredient;
import com.comp3350.recip_e.objects.Instruction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipeStub implements recipeManager {
    private List<Recipe> recipes;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Instruction> instructions;

    public RecipeStub() {
        recipes = new ArrayList<>();
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();

        for(int i = 1; i <= 3; i++) {
            ingredients.add(new Ingredient(((i*100) + "ml ingredients")));
            instructions.add(new Instruction(i + ". Test instruction."));
        }

        Recipe recipe1 = new Recipe("test recipe 1", ingredients, instructions, 1, 1, 1);
        Recipe recipe2 = new Recipe("test recipe 2", ingredients, instructions, 1, 1, 1);
        Recipe recipe3 = new Recipe("test recipe 3", ingredients, instructions, 1, 1, 1);
        recipe1.setID(0);
        recipe2.setID(1);
        recipe3.setID(2);
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
    }

    @Override
    public Recipe getRecipe(int recipeId, boolean withPic) {
        Recipe recipe = null;

        for (Recipe cur : recipes) {
            if (cur.getID() == recipeId) {
                recipe = cur;
            }
        }

        return recipe;
    }

    @Override
    public String getRecipeName(int recipeId) {
        return null;
    }

    @Override
    public String getIngredients(int recipeId) {
        return null;
    }

    @Override
    public String getDirection(int recipeId) {
        return null;
    }

    @Override
    public String getServing(int recipeId) {
        return null;
    }

    @Override
    public String getPrepTime(int recipeId) {
        return null;
    }

    @Override
    public String getCookTime(int recipeId) {
        return null;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipe.setID(getNextID());
        recipes.add(recipe);
        return recipe;
    }

    @Override
    public boolean delRecipe(int recipeId) {
        Iterator<Recipe> iterator = recipes.listIterator();
        boolean removed = false;
        Recipe recipe;

        while (iterator.hasNext()) {
            if (iterator.next().getID() == recipeId) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public String getCoverPic(int recipeId) {
        return null;
    }

    private int getNextID() {
        return recipes.get(recipes.size() - 1).getID() + 1;
    }
}
