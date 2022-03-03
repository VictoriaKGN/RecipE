package comp3350.recip_e.tests.utils;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import comp3350.recip_e.objects.Recipe;
import comp3350.recip_e.tests.objects.RecipeTest;
import comp3350.recip_e.tests.logic.RecipeManagerTest;
//add database test package


@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecipeTest.class,
        RecipeManagerTest.class
        //add database tests
})


public class AllTests {
}
