package com.comp3350.recip_e.application;

import com.comp3350.recip_e.database.iUserManager;
import com.comp3350.recip_e.database.data.RecipeStub;
import com.comp3350.recip_e.database.data.UserStub;
import com.comp3350.recip_e.database.iRecipeManager;
import com.comp3350.recip_e.database.data.userPersisHsqlDB;
import com.comp3350.recip_e.database.data.recipePersisHsqlDB;

public class Services {
    private static iRecipeManager recipePersistence = null;
    private static iUserManager userPersistence = null;

    public static synchronized iRecipeManager getRecipePersistence() {
        if (recipePersistence == null) {
            //recipePersistence = new RecipeStub();
            recipePersistence = new recipePersisHsqlDB(Main.getDBPathName());
        }
        return recipePersistence;
    }

    public static synchronized iUserManager getUserPersistence() {
        if (userPersistence == null) {
            //userPersistence = new UserStub();
            userPersistence = new userPersisHsqlDB(Main.getDBPathName());
        }
        return userPersistence;
    }
}
