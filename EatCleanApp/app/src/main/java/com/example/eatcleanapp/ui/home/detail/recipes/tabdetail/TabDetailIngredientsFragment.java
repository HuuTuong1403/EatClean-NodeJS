package com.example.eatcleanapp.ui.home.detail.recipes.tabdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.eatcleanapp.R;

public class TabDetailIngredientsFragment extends Fragment {

   private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_tab_detail_ingredients, container, false);


        return view;
    }
}