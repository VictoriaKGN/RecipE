package com.comp3350.recip_e.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import com.comp3350.recip_e.tests.objects.RecipeTest;
import com.comp3350.recip_e.tests.logic.RecipeManagerTest;
import com.comp3350.recip_e.tests.data.RecipeStubDBTest;
import com.comp3350.recip_e.tests.logic.UserManagerTest;
import com.comp3350.recip_e.tests.objects.UserTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecipeTest.class,
        RecipeStubDBTest.class,
        RecipeManagerTest.class,
        UserManagerTest.class,
        UserTest.class
})


public class AllTests {
}
