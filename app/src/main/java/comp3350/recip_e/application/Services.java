package comp3350.recip_e.application;

import com.comp3350.recip_e.database.recipeManager;
import com.comp3350.recip_e.database.data.recipeDB;

public class Services {
    private static recipeManager recipePersistence = null;

    public static synchronized recipeManager getRecipePersistence() {
        if (recipePersistence == null) {
            recipePersistence = new recipeDB(); // TODO Add required path
        }
        return recipePersistence;
    }
}
