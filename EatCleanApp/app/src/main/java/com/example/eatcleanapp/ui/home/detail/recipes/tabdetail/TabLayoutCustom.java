package com.example.eatcleanapp.ui.home.detail.recipes.tabdetail;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;


public class TabLayoutCustom extends TabLayout {
    public TabLayoutCustom(@NonNull Context context) {
        super(context);
    }

    public TabLayoutCustom(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayoutCustom(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup tabLayout = (ViewGroup) getChildAt(0);
        int childCount = tabLayout.getChildCount();
        if(childCount != 0){
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int tabMinWidth = displayMetrics.widthPixels / childCount;
            for(int i = 0; i < childCount; i++){
                tabLayout.getChildAt(i).setMinimumWidth(tabMinWidth);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
