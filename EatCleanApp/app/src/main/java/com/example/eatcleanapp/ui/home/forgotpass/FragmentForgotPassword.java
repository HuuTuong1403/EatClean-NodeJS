package com.example.eatcleanapp.ui.home.forgotpass;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;

import org.jetbrains.annotations.NotNull;

public class FragmentForgotPassword extends Fragment {

    private View view;
    private SubActivity mSubActivity;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubActivity = (SubActivity) getActivity();
        mSubActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        return view;
    }
}