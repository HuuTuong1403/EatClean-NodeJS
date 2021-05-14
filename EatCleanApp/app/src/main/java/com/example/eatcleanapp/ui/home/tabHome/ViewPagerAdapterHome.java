package com.example.eatcleanapp.ui.home.tabHome;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import org.jetbrains.annotations.NotNull;


public class ViewPagerAdapterHome extends FragmentStatePagerAdapter {

    public ViewPagerAdapterHome(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new RecipesFragment();
            case 1:
                return new BlogFragment();
            default:
                return new RecipesFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
