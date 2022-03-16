package com.comp3350.recip_e.database;

import com.comp3350.recip_e.objects.User;

public interface IuserManager {
    //insert new user into database
    User insertUser(User user);

    //update current user info; name/password
    User updateUser(User user);

    //Get specific user info from database
    User selectUser(int userId);




}
