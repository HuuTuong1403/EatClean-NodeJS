package com.example.eatcleanapp.model;


import java.io.Serializable;

public class users implements Serializable {
    private String IDUser;
    private String Email;
    private String Password;
    private String FullName;
    private String Image;
    private String LoginFB;
    private String IDRole;
    private String Username;
    public String getLoginFB() {
        return LoginFB;
    }

    public void setLoginFB(String loginFB) {
        LoginFB = loginFB;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public users(String IDUser, String email, String password, String fullName, String image, String loginFB,
                 String IDRole, String username) {
        this.IDUser = IDUser;
        Email = email;
        Password = password;
        FullName = fullName;
        Image = image;
        LoginFB = loginFB;
        this.IDRole = IDRole;
        Username = username;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


    public String getIDRole() {
        return IDRole;
    }

    public void setIDRole(String IDRole) {
        this.IDRole = IDRole;
    }

    @Override
    public String toString() {
        return "users{" +
                "IDUser='" + IDUser + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", FullName='" + FullName + '\'' +
                ", Image='" + Image + '\'' +
                ", LoginFB='" + LoginFB + '\'' +
                ", IDRole='" + IDRole + '\'' +
                ", Username='" + Username + '\'' +
                '}';
    }
}