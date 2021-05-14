package com.example.eatcleanapp.ui.home.signup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.databinding.FragmentSignUpBinding;
import com.example.eatcleanapp.databinding.SignInFragmentBinding;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.signin.SignInViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpFragment extends Fragment {

    private SignUpViewModel mViewModel;
    private FragmentSignUpBinding binding;
    private View view;
    private TextInputEditText edtUsername, edtEmail, edtPassword, edtPasswordAgain, edtFullName;
    private Button btnRegister;
    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }
    private  List<users> usersList;
    String IDUser;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        View background = view.findViewById(R.id.backgroundSignUp);
        Drawable backgroundImage = background.getBackground();
        backgroundImage.setAlpha(80);
        Mapping();

        usersList = new ArrayList<>();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void Mapping(){
        edtUsername = (TextInputEditText) view.findViewById(R.id.signup_edtUsername);
        edtEmail = (TextInputEditText) view.findViewById(R.id.signup_edtEmail);
        edtPassword = (TextInputEditText) view.findViewById(R.id.signup_edtPassword);
        edtPasswordAgain = (TextInputEditText) view.findViewById(R.id.signup_edtPasswordAgain);
        edtFullName = (TextInputEditText) view.findViewById(R.id.signup_edtFullname);
        btnRegister = (Button) view.findViewById(R.id.signup_btnRegister);
    }

    /*private void GetData (){
        APIService.apiService.getUser().enqueue(new Callback<List<users>>() {
            @Override
            public void onResponse(Call<List<users>> call, retrofit2.Response<List<users>> response) {
                usersList = response.body();
            }

            @Override
            public void onFailure(Call<List<users>> call, Throwable t) {
                Toast.makeText(view.getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}