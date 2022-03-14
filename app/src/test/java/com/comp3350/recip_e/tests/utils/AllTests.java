package com.comp3350.recip_e.tests.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import com.comp3350.recip_e.tests.objects.RecipeTest;
import com.comp3350.recip_e.tests.logic.RecipeManagerTest;
import com.comp3350.recip_e.database.data.recipeDBTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecipeTest.class,
        recipeDBTest.class,
        RecipeManagerTest.class
})


public class AllTests {
}
