package com.example.eatcleanapp.model;

public class users {
    private String IDUser;
    private String Email;
    private String Phone;
    private String Password;
    private String FullName;
    private String Gender;
    private String 	Image;
    private int LoginFB;
    private String IDRole;


    public users(String IDUser, String email, String phone, String password,
                 String fullName, String gender, String image, int loginFB, String IDRole) {
        this.IDUser = IDUser;
        Email = email;
        Phone = phone;
        Password = password;
        FullName = fullName;
        Gender = gender;
        Image = image;
        LoginFB = loginFB;
        this.IDRole = IDRole;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getLoginFB() {
        return LoginFB;
    }

    public void setLoginFB(int loginFB) {
        LoginFB = loginFB;
    }

    public String getIDRole() {
        return IDRole;
    }

    public void setIDRole(String IDRole) {
        this.IDRole = IDRole;
    }
}
