package com.comp3350.recip_e.database;

import com.comp3350.recip_e.objects.Users;

public interface IuserManager {
    //insert new user into database
    Users insertUser(Users user);

    //update current user info; name/password
    Users updateUser(Users user);





}
