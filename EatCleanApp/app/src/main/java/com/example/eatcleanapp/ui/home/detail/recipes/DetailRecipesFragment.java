package com.example.eatcleanapp.ui.home.detail.recipes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatcleanapp.R;

public class DetailRecipesFragment extends Fragment {

    private View view;

    public static DetailRecipesFragment newInstance() { return new DetailRecipesFragment(); }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_recipes, container, false);
        return view;
    }
}