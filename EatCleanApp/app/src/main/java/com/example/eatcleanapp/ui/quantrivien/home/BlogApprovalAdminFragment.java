package com.example.eatcleanapp.ui.quantrivien.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.eatcleanapp.R;

public class BlogApprovalAdminFragment extends Fragment {

    private View view;

    public static BlogApprovalAdminFragment newInstance() { return new BlogApprovalAdminFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blog_approval_admin, container, false);

        return view;
    }
}