package com.comp3350.recip_e.application;

import com.comp3350.recip_e.database.IuserManager;
import com.comp3350.recip_e.database.data.RecipeStub;
import com.comp3350.recip_e.database.data.UserStub;
import com.comp3350.recip_e.database.recipeManager;
import com.comp3350.recip_e.database.data.recipeDB;

public class Services {
    private static recipeManager recipePersistence = null;
    private static IuserManager userPersistence = null;

    public static synchronized recipeManager getRecipePersistence() {
        if (recipePersistence == null) {
            recipePersistence = new RecipeStub();
        }
        return recipePersistence;
    }

    public static synchronized IuserManager getUserPersistence() {
        if (userPersistence == null) {
            userPersistence = new UserStub();
        }
        return userPersistence;
    }
}
