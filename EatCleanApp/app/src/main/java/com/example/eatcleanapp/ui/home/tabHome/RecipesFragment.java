package com.example.eatcleanapp.ui.home.tabHome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.ui.home.tabHome.recipes.Recipes;
import com.example.eatcleanapp.ui.home.tabHome.recipes.RecipesAdapter;

import java.util.ArrayList;
import java.util.List;


public class RecipesFragment extends Fragment {

    private View view;
    private RecyclerView rcvRecipes;
    private RecipesAdapter mRecipesAdapter;

    public static RecipesFragment newInstance() { return new RecipesFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipes, container, false);
        rcvRecipes = view.findViewById(R.id.list_recipes);
        mRecipesAdapter = new RecipesAdapter(getContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvRecipes.setLayoutManager(gridLayoutManager);
        mRecipesAdapter.setData(getListData());
        rcvRecipes.setAdapter(mRecipesAdapter);
        return view;
    }

    private List<Recipes> getListData(){
        List<Recipes> list = new ArrayList<>();
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));
        list.add(new Recipes(R.drawable.background, "Tường"));

        return list;
    }
}