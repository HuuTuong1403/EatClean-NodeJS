package com.example.eatcleanapp.ui.congtacvien;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.eatcleanapp.ui.congtacvien.tabCTV.AddBlogFragment;
import com.example.eatcleanapp.ui.congtacvien.tabCTV.AddRecipeFragment;
import com.example.eatcleanapp.ui.congtacvien.tabCTV.ListAddBlogFragment;
import com.example.eatcleanapp.ui.congtacvien.tabCTV.ListAddRecipeFragment;


public class ViewPagerAdapterCtv extends FragmentStatePagerAdapter {

    public ViewPagerAdapterCtv(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new AddRecipeFragment();
            case 1:
                return new AddBlogFragment();
            case 2:
                return new ListAddRecipeFragment();
            case 3:
                return new ListAddBlogFragment();
            default:
                return new AddRecipeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
