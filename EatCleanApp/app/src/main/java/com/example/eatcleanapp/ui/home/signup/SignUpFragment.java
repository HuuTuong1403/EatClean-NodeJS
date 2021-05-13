package com.example.eatcleanapp.ui.home.signup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.databinding.FragmentSignUpBinding;
import com.example.eatcleanapp.databinding.SignInFragmentBinding;
import com.example.eatcleanapp.ui.home.signin.SignInViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignUpFragment extends Fragment {

    private SignUpViewModel mViewModel;
    private FragmentSignUpBinding binding;
    private View view;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        View background = view.findViewById(R.id.backgroundSignUp);
        Drawable backgroundImage = background.getBackground();
        backgroundImage.setAlpha(80);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}