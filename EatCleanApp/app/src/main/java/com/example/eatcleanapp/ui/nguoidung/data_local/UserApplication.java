package com.example.eatcleanapp.ui.nguoidung.data_local;

import android.app.Application;

public class UserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
