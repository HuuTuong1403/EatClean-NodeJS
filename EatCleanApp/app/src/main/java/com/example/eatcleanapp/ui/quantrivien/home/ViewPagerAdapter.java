package com.example.eatcleanapp.ui.quantrivien.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new NotificationAdminFragment();
            case 1:
                return new BlogApprovalAdminFragment();
            case 2:
                return new RecipesApprovalAdminFragment();
            case 3:
                return new SettingAdminFragment();
            default:
                return new NotificationAdminFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
