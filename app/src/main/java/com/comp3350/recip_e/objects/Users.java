package com.comp3350.recip_e.objects;



public class Users {

    private int userID;
    private String userName;
    private int userPassword;

    public Users(int id,String name,int password){
        userID=id;
        userName=name;
        userPassword=password;
    }

    public void setUserID(int userID) { this.userID = userID; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(int userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserPassword() {
        return userPassword;
    }
}
