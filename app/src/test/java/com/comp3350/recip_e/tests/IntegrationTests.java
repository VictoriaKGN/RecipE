package com.comp3350.recip_e.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.comp3350.recip_e.tests.logic.UserIT;
import com.comp3350.recip_e.tests.logic.RecipeIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserIT.class,
        RecipeIT.class
})

public class IntegrationTests {
}
