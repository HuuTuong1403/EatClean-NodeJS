package com.example.eatcleanapp.ui.nguoidung.data_local;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreferences {
    private final static String USER_SHAREDPREFERENCES = "USER_SHAREDPREFERENCES";
    private Context context;

    public UserSharedPreferences(Context context) {
        this.context = context;
    }

    public void putStringValue(String key ,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void deleteValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}
