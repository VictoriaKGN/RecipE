package com.comp3350.recip_e.database;

import com.comp3350.recip_e.objects.User;

public interface IuserManager {
    //insert new user into database
    User insertUser(User user);

    //update current user info; name/password
    User updateUser(User user);

    //Get specific user info from database
    User selectUser(String email);

    //verifyUser by input name and password, would have a null return if name/password doesn't match
    User verifyUser(String usrEmail, String password);

    boolean usernameExists(String username);
    boolean emailExists(String email);

}
