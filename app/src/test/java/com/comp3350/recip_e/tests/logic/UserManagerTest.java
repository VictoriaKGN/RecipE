package com.comp3350.recip_e.tests.logic;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.comp3350.recip_e.database.data.UserStub;
import com.comp3350.recip_e.logic.UserManager;
import com.comp3350.recip_e.logic.exceptions.IncorrectPasswordException;
import com.comp3350.recip_e.logic.exceptions.EmailDoesNotExistException;
import com.comp3350.recip_e.objects.User;

public class UserManagerTest {
    private static UserManager userManager;
    private static UserStub database;
    private static User user;
    private static String username;
    private static String email;
    private static String password;

    @BeforeClass
    public static void oneTimeSetup() {
        database = new UserStub();
        userManager = new UserManager(database);
        username = "Username";
        email = "email@gmail.com";
        password = "1234";
        user = new User(email, username, password);
        userManager.addUser(user);
    }

    @Before
    public void setup() {
        userManager.updateUser(user);
    }

    @Test
    public void testAddUser() {
        System.out.println("\nStarting testAddUser\n");

        User testUser = userManager.getUser(username);

        assertNotNull("User does not exist", testUser);
        assertEquals("Usernames should be equal", username, testUser.getUsername());
        assertEquals("Emails should be equal", email, testUser.getEmail());
        assertEquals("Passwords should be equal", password, testUser.getPassword());

        System.out.println("Finished testAddUser");
    }

    @Test
    public void testUpdateUser() {
        System.out.println("\nStarting testUpdateUser\n");

        String newPassword = "4321";
        userManager.updateUser(new User(email, username, newPassword));
        User testUser = userManager.getUser(username);

        assertNotNull("User does not exist", testUser);
        assertEquals("Usernames should be equal", username, testUser.getUsername());
        assertEquals("Password should match new password", newPassword, testUser.getPassword());

        System.out.println("Finished testUpdateUser");
    }

    @Test
    public void testUsernameExists() {
        System.out.println("\nStarting testUsernameExists\n");

        assertTrue(userManager.usernameExists(username));

        System.out.println("Finished testUsernameExists");
    }

    @Test
    public void testEmailExists() {
        System.out.println("\nStarting testEmailExists\n");

        assertTrue(userManager.emailExists(email));

        System.out.println("Finished testEmailExists");
    }

    @Test
    public void testValidateUser() {
        System.out.println("\nStarting testValidateUser\n");

        userManager.validateUser(user);
        User fakeUser = new User("", "", "");
        User wrongPassword = new User(email, username, "4321");

        assertThrows(EmailDoesNotExistException.class, () -> userManager.validateUser(fakeUser));
        assertThrows(IncorrectPasswordException.class, () -> userManager.validateUser(wrongPassword));

        System.out.println("Finished testValidateUser");
    }
}
