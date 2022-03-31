package com.comp3350.recip_e.application;

import android.app.Application;

import com.comp3350.recip_e.objects.User;

public class App extends Application {
    private User currentUser = null;

    /**
     * Set the current logged-in user
     *
     * @param newUser The user to log in
     */
    public void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    /**
     * Get the current logged-in user
     *
     * @return
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Remove the currently logged-in user i.e., log out the current user
     */
    public void removeCurrentUser() {
        currentUser = null;
    }
}
