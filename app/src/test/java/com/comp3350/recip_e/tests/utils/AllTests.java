package com.comp3350.recip_e.tests.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import com.comp3350.recip_e.tests.objects.RecipeTest;
import com.comp3350.recip_e.tests.logic.RecipeManagerTest;
//add database test package


@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecipeTest.class,
        RecipeManagerTest.class
        //add database tests
})


public class AllTests {
}
