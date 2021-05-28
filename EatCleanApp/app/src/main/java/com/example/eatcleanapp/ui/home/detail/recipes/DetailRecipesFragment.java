package com.example.eatcleanapp.ui.home.detail.recipes;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.home.detail.recipes.tabdetail.DetailViewPagerAdapter;
import com.example.eatcleanapp.ui.home.detail.recipes.tabdetail.TabLayoutCustom;
import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class DetailRecipesFragment extends Fragment{

    private View view;
    private TabLayoutCustom mTabLayout;
    private ViewPager mViewPager;
    private DetailViewPagerAdapter detailViewPagerAdapter;
    private DetailActivity mDetailActivity;
    private recipes recipes_detail;
    private TextView txv_show_title_recipes, txv_show_content_recipes;
    private RoundedImageView image_detail_recipes;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_recipes, container, false);
        mDetailActivity = (DetailActivity) getActivity();
        Mapping();


        return view;
    }
    private void Mapping(){
        //Mapping
        mTabLayout                  = (TabLayoutCustom) view.findViewById(R.id.tab_detail_recipes);
        mViewPager                  = (ViewPager)view.findViewById(R.id.viewpager_detail_recipes);
        image_detail_recipes        = (RoundedImageView)view.findViewById(R.id.image_detail_recipes);
        txv_show_title_recipes      = (TextView)view.findViewById(R.id.txv_show_title_recipes);
        txv_show_content_recipes    = (TextView)view.findViewById(R.id.txv_show_content_recipes);

        //Get data
        recipes_detail = mDetailActivity.getRecipes();
        Glide.with(view).load(recipes_detail.getImage()).into(image_detail_recipes);
        txv_show_title_recipes.setText(recipes_detail.getRecipesTitle());
        txv_show_content_recipes.setText(recipes_detail.getRecipesContent());

        //ViewPager
        detailViewPagerAdapter = new DetailViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(detailViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabLayout();
    }

    private void setTabLayout(){
        View root = mTabLayout.getChildAt(0);
        if(root instanceof LinearLayout){
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.GRAY);
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(3);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }
}