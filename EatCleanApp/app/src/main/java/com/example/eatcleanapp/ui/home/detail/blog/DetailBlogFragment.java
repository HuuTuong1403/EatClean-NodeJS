package com.example.eatcleanapp.ui.home.detail.blog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatcleanapp.R;

public class DetailBlogFragment extends Fragment {

    private View view;

    public static DetailBlogFragment newInstance() {
        return new DetailBlogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);
        view = inflater.inflate(R.layout.fragment_detail_blog, container, false);
        return view;
    }
}