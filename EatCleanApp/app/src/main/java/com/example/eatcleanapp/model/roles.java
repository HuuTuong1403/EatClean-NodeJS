package com.example.eatcleanapp.model;

public class roles {
    private String IDRole;
    private String Rolename;

    public roles(String IDRole, String rolename) {
        this.IDRole = IDRole;
        Rolename = rolename;
    }

    public String getIDRole() {
        return IDRole;
    }

    public String getRolename() {
        return Rolename;
    }
}
