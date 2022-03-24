package com.comp3350.recip_e.application;

import com.comp3350.recip_e.database.data.RecipeStub;
import com.comp3350.recip_e.database.iRecipeManager;

public class Services {
    private static iRecipeManager recipePersistence = null;

    public static synchronized iRecipeManager getRecipePersistence() {
        if (recipePersistence == null) {
            recipePersistence = new RecipeStub();
        }
        return recipePersistence;
    }
}
