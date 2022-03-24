package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.iUserManager;
import com.comp3350.recip_e.objects.User;

import java.util.ArrayList;
import java.util.List;

public class UserStub implements iUserManager {

    private List<User> userList;

    public UserStub(){
        this.userList=new ArrayList<>();

        userList.add(new User("dogge@hot.com","Bob","cool978988"));
        userList.add(new User("gogle@mail.com","Alice","nice124355"));
        userList.add(new User("nail@ufo.com","Eve","check564423"));
    }

    public int userListSize(){
        return userList.size();
    }

    public User insertUser(User user){
        if(user!=null) {
            userList.add(user);
        }

        return user;
    }

    public User updateUser(User user){

        if(user!=null) {
            int index = userList.indexOf(user);

            if (index > 0) {
                userList.set(index, user);
            }
        }

        return user;
    }

    public User selectUser(String email){
        User result=null;

        for (User Curr : userList) {
            if (email.equals(Curr.getUserEmail())) {
                result = Curr;
            }
        }

        return result;
    }

    public User verifyUser(String email, String password){
        User result=null;

        for (User Curr : userList) {
            if (email.equals(Curr.getUserEmail())&&password.equals(Curr.getUserPassword())) {
                    result = Curr;
            }
        }

        return result;
    }




}
