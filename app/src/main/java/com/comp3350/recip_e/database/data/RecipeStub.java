package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.iRecipeManager;
import com.comp3350.recip_e.objects.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipeStub implements iRecipeManager {
    private List<Recipe> recipes;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;

    public RecipeStub() {
        recipes = new ArrayList<Recipe>();
        ingredients = new ArrayList<String>();
        instructions = new ArrayList<String>();

        for (int i = 1; i <= 3; i++) {
            ingredients.add((i * 100) + "ml ingredients");
            instructions.add(i + ". Test instruction.");
        }

        Recipe recipe1 = new Recipe("test recipe 1", ingredients, instructions, 1, 1, 1, "this/path/1", "user@email.com");
        Recipe recipe2 = new Recipe("test recipe 2", ingredients, instructions, 1, 1, 1, "this/path/2", "user@email.com");
        Recipe recipe3 = new Recipe("test recipe 3", ingredients, instructions, 1, 1, 1, "default/path", "user@email.com");
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

    @Override
    public ArrayList<Recipe> getUserRecipes(String user) {
        ArrayList<Recipe> userRecipes = new ArrayList<>();
        Iterator<Recipe> iterator = recipes.listIterator();

        while (iterator.hasNext()) {
            Recipe r = iterator.next();

            if (r.getUserID().equals(user)) {
                userRecipes.add(r);
            }
        }

        return userRecipes;
    }

    @Override
    public ArrayList<Recipe> searchRecipeByName(String user, String searchKey) {
        ArrayList<Recipe> userRecipes = new ArrayList<Recipe>();

        for (Recipe cur : recipes) {
            if (cur.getUserID().equals(user) && cur.getName().contains(searchKey)) {
                userRecipes.add(cur);
            }
        }
        return userRecipes;
    }

    @Override
    public ArrayList<Recipe> searchRecipeByIngredient(String user, String searchKey) {
        ArrayList<Recipe> userRecipes = new ArrayList<Recipe>();
        Iterator<String> iterator;
        boolean added;

        for (Recipe cur : recipes) {
            if (cur.getUserID().equals(user)) {
                added = false;
                iterator = cur.getIngredients().listIterator();

                while (iterator.hasNext() && !added) {
                    if (iterator.next().contains(searchKey)) {
                        userRecipes.add(cur);
                        added = true;
                    }
                }
            }
        }
        return userRecipes;
    }
}
