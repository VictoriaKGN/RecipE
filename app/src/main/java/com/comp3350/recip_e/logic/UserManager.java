package com.comp3350.recip_e.logic;

import com.comp3350.recip_e.application.Services;
import com.comp3350.recip_e.logic.exceptions.IncorrectPasswordException;
import com.comp3350.recip_e.logic.exceptions.UsernameDoesNotExistException;
import com.comp3350.recip_e.objects.User;

public class UserManager {
    // TODO Instance of User database

    public UserManager() {
        // TODO Set instance of database user
    }

    /**
     * Get a single user from the database
     *
     * @param username Username of the user to get
     * @return The user with the given username
     */
    public User getUser(String username) {
        // TODO Get user from database
        return null;
    }

    /**
     * Add a user to the database
     *
     * @param user The user to add
     */
    public void addUser(User user) {
        // TODO Add user to database
    }

    /**
     * Update a user in the database
     *
     * @param user User with updated values
     */
    public void updateUser(User user) {
        // TODO Update user in database
    }

    /**
     * Check if a user with the given username exists
     *
     * @param username The username to check
     * @return True if a user with the given username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        // TODO Call db function
        return false;
    }

    /**
     * Check if a user with the given email exists
     *
     * @param email The email to check
     * @return True if a user with the given email exists, false otherwise
     */
    public boolean emailExists(String email) {
        // TODO Call db function
        return false;
    }

    /**
     * Validate a given user
     *
     * @param user The user to validate
     * @throws UsernameDoesNotExistException Indicates no user with the given username exists
     * @throws IncorrectPasswordException    Indicates that the given password doesn't match the users password
     */
    public void validateUser(User user) throws UsernameDoesNotExistException, IncorrectPasswordException {
        User realUser;
        String username = user.getUsername();

        if (usernameExists(username)) {
            realUser = getUser(username);

            if (!user.getPassword().equals(realUser.getPassword())) {
                throw new IncorrectPasswordException("Incorrect password.");
            }
        } else {
            throw new UsernameDoesNotExistException("User " + username + " does not exist.");
        }
    }
}
