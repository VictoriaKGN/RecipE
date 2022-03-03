package com.comp3350.recip_e;

import org.junit.Test;
import com.comp3350.recip_e.database.data.recipeDB;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void checkAdd(){
        String filePath="src/main/asset/recipe.json";
        recipeDB db=new recipeDB(filePath);
        String ingre="¾ teaspoon salt\n" +
                "¼ teaspoon black pepper\n" +
                "¼ teaspoon cayenne pepper\n" +
                "⅛ teaspoon paprika\n" +
                "¼ teaspoon garlic powder\n" +
                "⅛ teaspoon onion powder\n" +
                "¼ teaspoon dried thyme\n" +
                "¼ teaspoon dried parsley\n" +
                "4 boneless, skinless chicken breast halves\n" +
                "2 tablespoons butter\n" +
                "1 tablespoon olive oil\n" +
                "2 teaspoons garlic powder\n" +
                "3 tablespoons lime juice";
        String dir="Step 1\n" +
                "In a small bowl, mix together salt, black pepper, cayenne, paprika, 1/4 teaspoon garlic powder, onion powder, thyme and parsley. Sprinkle spice mixture generously on both sides of chicken breasts.\n" +
                "Step 2\n" +
                "Heat butter and olive oil in a large heavy skillet over medium heat. Saute chicken until golden brown, about 6 minutes on each side. Sprinkle with 2 teaspoons garlic powder and lime juice. " +
                "Cook 5 minutes more, stirring frequently to coat evenly with sauce.\n";
        String cover="HoneyLimeChicken.jpeg";
        db.addRecipe(2,"Spicy Garlic Lime Chicken",ingre,dir,4, 5, 20,cover);
    }

}

