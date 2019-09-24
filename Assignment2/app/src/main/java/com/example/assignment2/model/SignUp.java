package com.example.assignment2.model;

import com.example.assignment2.MainActivity;

public class SignUp extends MainActivity {
    String email;
    String userPassword;

    public SignUp(String email,String userPassword){
        this.email=email;
        this.userPassword=userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
