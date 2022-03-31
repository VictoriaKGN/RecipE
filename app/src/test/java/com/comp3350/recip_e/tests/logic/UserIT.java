package com.comp3350.recip_e.tests.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

//import

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

import com.comp3350.recip_e.database.iUserManager;
import com.comp3350.recip_e.logic.UserManager;
import com.comp3350.recip_e.logic.exceptions.IncorrectPasswordException;
import com.comp3350.recip_e.logic.exceptions.UsernameDoesNotExistException; ///email???
import com.comp3350.recip_e.database.data.userPersisHsqlDB;
import com.comp3350.recip_e.objects.User;
import com.comp3350.recip_e.tests.utils.TestUtils;

public class UserIT {
    private File tempDB;
    private UserManager userManager;
    private User user;
    private String username = "Bob";
    private String email = "dogge@hot.com";
    private String password = "cool978988";

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final iUserManager database = new userPersisHsqlDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.userManager = new UserManager(database);
    }

    @Test
    public void testAddUser() {
        System.out.println("\nStarting testAddUser\n");

        User testUser = userManager.getUser(username);

        assertNotNull("User does not exist", testUser);
        assertTrue("Usernames should be equal", username.equals(testUser.getUsername()));
        assertTrue("Emails should be equal", email.equals(testUser.getEmail()));
        assertTrue("Passwords should be equal", password.equals(testUser.getPassword()));

        System.out.println("Finished testAddUser");
    }

    @Test
    public void testUpdateUser() {
        System.out.println("\nStarting testUpdateUser\n");

        String newPassword = "4321";
        userManager.updateUser(new User(email, username, newPassword));
        User testUser = userManager.getUser(username);
        User badUser = new User("user@mail.com","this Guy", "0000000");
        userManager.updateUser(badUser);
        badUser = userManager.getUser(badUser.getUsername());

        assertNotNull("User does not exist", testUser);
        assertTrue("Usernames should be equal", username.equals(testUser.getUsername()));
        assertTrue("Password should match new password", newPassword.equals(testUser.getPassword()));
        assertNull("New User was added to database during update", badUser);

        System.out.println("Finished testUpdateUser");
    }

    @Test
    public void testUsernameExists() {
        System.out.println("\nStarting testUsernameExists\n");

        assertTrue("Expected user not found", userManager.usernameExists(username));
        assertFalse("Unexpected user found", userManager.usernameExists("heckle"));

        System.out.println("Finished testUsernameExists");
    }

    @Test
    public void testEmailExists() {
        System.out.println("\nStarting testEmailExists\n");

        assertTrue("Expected user email not found", userManager.emailExists(email));
        assertFalse("Unexpected email found", userManager.emailExists("whodid@blah.com"));

        System.out.println("Finished testEmailExists");
    }

    @Test
    public void testValidateUser() {
        System.out.println("\n Starting testValidateUser\n");

        userManager.validateUser(user);
        User fakeUser = new User("", "", "");
        User wrongPassword = new User(email, username, "4321");

        assertThrows(UsernameDoesNotExistException.class, () -> userManager.validateUser(fakeUser));
        assertThrows(IncorrectPasswordException.class, () -> userManager.validateUser(wrongPassword));

        System.out.println("Finished testValidateUser");
    }


    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
