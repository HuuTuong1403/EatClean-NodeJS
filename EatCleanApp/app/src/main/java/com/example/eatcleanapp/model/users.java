package com.example.eatcleanapp.model;


import java.io.Serializable;

public class users implements Serializable {
    private String _id;
    private String Username;
    private String Email;
    private String Password;
    private String FullName;
    private String 	Image;
    private String LoginFB;
    private String IDRole;
    private String Status;
    private String token;
    private String SoDienThoai;

    public users(String _id, String username, String email, String password, String fullName, String image, String loginFB,
                 String IDRole, String status, String token, String soDienThoai) {
        this._id = _id;
        Username = username;
        Email = email;
        Password = password;
        FullName = fullName;
        Image = image;
        LoginFB = loginFB;
        this.IDRole = IDRole;
        Status = status;
        this.token = token;
        SoDienThoai = soDienThoai;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
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

    public String getLoginFB() {
        return LoginFB;
    }

    public void setLoginFB(String loginFB) {
        LoginFB = loginFB;
    }

    public String getIDRole() {
        return IDRole;
    }

    public void setIDRole(String IDRole) {
        this.IDRole = IDRole;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }
}