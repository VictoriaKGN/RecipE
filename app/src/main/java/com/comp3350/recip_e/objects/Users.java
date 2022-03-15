package com.comp3350.recip_e.objects;



public class Users {

    private int userId;
    private String userName;
    private String userPassword;

    public Users(int id,String name,String password){
        userId=id;
        userName=name;
        userPassword=password;
    }

    public void setUserID(int userID) { this.userId = userID; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
