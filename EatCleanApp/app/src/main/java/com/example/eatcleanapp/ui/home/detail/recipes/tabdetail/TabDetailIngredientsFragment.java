package com.example.eatcleanapp.ui.home.detail.recipes.tabdetail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;

public class TabDetailIngredientsFragment extends Fragment {

    private View view;
    private DetailActivity mDetailActivity;
    private recipes recipes_detail;
    private TextView txv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_tab_detail_ingredients, container, false);
        mDetailActivity = (DetailActivity) getActivity();
        Mapping();
        txv.setText(recipes_detail.getIngredients());
        return view;
    }
    private void Mapping(){
        recipes_detail = mDetailActivity.getRecipes();
        txv = (TextView)view.findViewById(R.id.txv_detail_ingredients);
    }
}