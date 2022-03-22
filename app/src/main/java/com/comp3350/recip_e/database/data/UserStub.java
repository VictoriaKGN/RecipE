package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.IuserManager;
import com.comp3350.recip_e.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class UserStub implements IuserManager {
    private List<User> users;

    public UserStub() {
        users = new ArrayList<User>();
    }

    @Override
    public User insertUser(User user) {
        users.add(user);
        return null;
    }

    @Override
    public User updateUser(User user) {
        ListIterator<User> iterator = users.listIterator();
        User curUser;
        boolean updated = false;

        while (iterator.hasNext() && !updated) {
            curUser = iterator.next();

            if (curUser.getEmail().equals(user.getEmail())) {
                iterator.set(user);
                updated = true;
            }
        }
        return null;
    }

    @Override
    public User selectUser(String email) {
        ListIterator<User> iterator = users.listIterator();
        User curUser = null;
        boolean found = false;

        while (iterator.hasNext() && !found) {
            curUser = iterator.next();

            if (curUser.getEmail().equals(email)) {
                found = true;
            }
        }

        if (!found) {
            curUser = null;
        }
        return curUser;
    }

    @Override
    public User verifyUser(String usrEmail, String password) {
        return null;
    }

    @Override
    public boolean usernameExists(String username) {
        ListIterator<User> iterator = users.listIterator();
        User curUser = null;
        boolean found = false;

        while (iterator.hasNext() && !found) {
            curUser = iterator.next();

            if (curUser.getUsername().equals(username)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public boolean emailExists(String email) {
        ListIterator<User> iterator = users.listIterator();
        User curUser = null;
        boolean found = false;

        while (iterator.hasNext() && !found) {
            curUser = iterator.next();

            if (curUser.getEmail().equals(email)) {
                found = true;
            }
        }
        return found;
    }
}
