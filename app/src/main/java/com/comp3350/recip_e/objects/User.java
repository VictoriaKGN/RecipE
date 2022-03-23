package com.comp3350.recip_e.objects;


import java.util.Objects;

public class User {

    private final String userEmail;
    private String userName;
    private String userPassword;

    public User(String email, String name, String password){
        userEmail=email;
        userName=name;
        userPassword=password;
    }

//    public void setUserID(int userID) { this.userId = userID; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean equals(final User usr){
        return Objects.equals(usr.getUserEmail(),userEmail);
    }
}
