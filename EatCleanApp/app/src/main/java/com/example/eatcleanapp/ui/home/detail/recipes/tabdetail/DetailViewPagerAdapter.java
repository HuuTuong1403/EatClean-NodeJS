package com.example.eatcleanapp.ui.home.detail.recipes.tabdetail;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;



public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

    private int mCurrentPosition = -1;

    public DetailViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new TabDetailIngredientsFragment();
            case 1:
                return new TabDetailDirectionsFragment();
            case 2:
                return new TabDetailNutritionFragment();
            case 3:
                return new TabDetailCommentFragment();
            default:
                return new TabDetailIngredientsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch(position){
            case 0:
                title = "Nguyên liệu";
                break;
            case 1:
                title = "Hướng dẫn làm";
                break;
            case 2:
                title = "Dinh dưỡng";
                break;
            case 3:
                title = "Bình luận";
                break;
        }
        return title;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            CustomViewPager pager = (CustomViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }
}
