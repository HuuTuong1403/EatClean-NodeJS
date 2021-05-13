package com.example.eatcleanapp.ui.quantrivien.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.eatcleanapp.R;

public class NotificationAdminFragment extends Fragment {

    private View view;

    public static NotificationAdminFragment newInstance() { return new NotificationAdminFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_admin, container, false);

        return view;
    }
}