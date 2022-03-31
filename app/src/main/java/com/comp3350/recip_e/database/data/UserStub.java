package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.iUserManager;
import com.comp3350.recip_e.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class UserStub implements iUserManager {
    private List<User> users;

    public UserStub() {
        users = new ArrayList<User>();

        userList.add(new User("dogge@hot.com","Bob","cool978988"));
        userList.add(new User("gogle@mail.com","Alice","nice124355"));
        userList.add(new User("nail@ufo.com","Eve","check564423"));
    }

    @Override
    public User insertUser(User user) {
        if(user!=null) {
            users.add(user);
        }
        
        return user;
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
        
        return curUser;
    }

    @Override
    public User verifyUser(String usrEmail, String password) {
        User result=null;

        for (User Curr : userList) {
            if (email.equals(Curr.getUserEmail())&&password.equals(Curr.getUserPassword())) {
                result = Curr;
            }
        }

        return result;
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
