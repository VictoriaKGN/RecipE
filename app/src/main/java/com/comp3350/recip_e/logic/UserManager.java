package com.comp3350.recip_e.logic;

import com.comp3350.recip_e.application.Services;
import com.comp3350.recip_e.database.iUserManager;
import com.comp3350.recip_e.logic.exceptions.IncorrectPasswordException;
import com.comp3350.recip_e.logic.exceptions.EmailDoesNotExistException;
import com.comp3350.recip_e.objects.User;

public class UserManager {
    private iUserManager database;

    public UserManager() {
        database = Services.getUserPersistence();
    }

    public UserManager(final iUserManager persistence) {
        this.database = persistence;
    }

    /**
     * Get a single user from the database
     *
     * @param useremail Username of the user to get
     * @return The user with the given username
     */
    public User getUser(String useremail) {
        return database.selectUser(useremail);
    }

    /**
     * Add a user to the database
     *
     * @param user The user to add
     */
    public void addUser(User user) {
        database.insertUser(user);
    }

    /**
     * Update a user in the database
     *
     * @param user User with updated values
     */
    public void updateUser(User user) {
        database.updateUser(user);
    }

    /**
     * Check if a user with the given username exists
     *
     * @param username The username to check
     * @return True if a user with the given username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        return database.usernameExists(username);
    }

    /**
     * Check if a user with the given email exists
     *
     * @param email The email to check
     * @return True if a user with the given email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return database.emailExists(email);
    }

    /**
     * Validate a given user
     *
     * @param user The user to validate
     * @throws EmailDoesNotExistException Indicates no user with the given username exists
     * @throws IncorrectPasswordException    Indicates that the given password doesn't match the users password
     */
    public void validateUser(User user) throws EmailDoesNotExistException, IncorrectPasswordException {
        User realUser;
        String email = user.getEmail();

        if (emailExists(email)) {
            realUser = getUser(email);

            if (!user.getPassword().equals(realUser.getPassword())) {
                throw new IncorrectPasswordException("Incorrect password.");
            }
        } else {
            throw new EmailDoesNotExistException("User " + email + " does not exist.");
        }
    }
}
