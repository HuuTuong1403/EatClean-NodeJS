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

public class TabDetailDirectionsFragment extends Fragment {

    private View view;
    private recipes recipes_detail;
    private DetailActivity mDetailActivity;
    private TextView txv_detail_direction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_detail_directions, container, false);
        mDetailActivity = (DetailActivity) getActivity();
        Mapping();

        return view;
    }

    private void Mapping(){
        recipes_detail = mDetailActivity.getRecipes();
        txv_detail_direction = (TextView)view.findViewById(R.id.txv_detail_directions);
        txv_detail_direction.setText(recipes_detail.getSteps());
    }
}