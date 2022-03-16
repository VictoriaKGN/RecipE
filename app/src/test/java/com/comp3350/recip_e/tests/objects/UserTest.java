package com.comp3350.recip_e.tests.objects;

import org.junit.Test;

import com.comp3350.recip_e.objects.User;

import static org.junit.Assert.*;

public class UserTest {
    String email = "email@gmail.com";
    String username = "Username";
    String password = "1234";

    @Test
    public void testCreateUser() {
        System.out.println("\nStarting testCreateUser\n");

        User user = new User(email, username, password);

        assertNotNull("User not created", user);
        assertEquals("Email does not match", email, user.getEmail());
        assertEquals("Username does not match", username, user.getUsername());
        assertEquals("Password does not match", password, user.getPassword());

        System.out.println("Finished testCreateUser");
    }
}
