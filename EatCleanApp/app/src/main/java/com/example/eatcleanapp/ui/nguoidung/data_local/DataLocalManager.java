package com.example.eatcleanapp.ui.nguoidung.data_local;

import android.content.Context;

import com.example.eatcleanapp.model.users;
import com.google.gson.Gson;

public class DataLocalManager {

    //Singleton
    private static final String PRE_OBJECT_USER = "PRE_OBJECT_USER";
    private static DataLocalManager instance;
    private UserSharedPreferences userSharedPreferences;

    private DataLocalManager() { }

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.userSharedPreferences = new UserSharedPreferences(context);
    }

    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setUser(users user){
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManager.getInstance().userSharedPreferences.putStringValue(PRE_OBJECT_USER, strJsonUser);
    }

    public static users getUser(){
        String strJsonUser = DataLocalManager.getInstance().userSharedPreferences.getStringValue(PRE_OBJECT_USER);
        Gson gson = new Gson();
        users mUser = gson.fromJson(strJsonUser, users.class);
        return mUser;
    }
    public static void deleteUser(){
        DataLocalManager.getInstance().userSharedPreferences.deleteValue(PRE_OBJECT_USER);
    }
}
