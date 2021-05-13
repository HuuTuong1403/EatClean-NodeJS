package com.example.eatcleanapp.ui.home.signin;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;

import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.databinding.SignInFragmentBinding;
import com.example.eatcleanapp.ui.home.HomeFragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Arrays;

public class SignInFragment extends Fragment {

    //private SignInViewModel mViewModel;
    private SignInFragmentBinding binding;
    private View view;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView txvSignUp, txvForgotPass;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FacebookSdk.getApplicationContext();
        callbackManager = CallbackManager.Factory.create();
        view = inflater.inflate(R.layout.sign_in_fragment, container, false);
        loginButton();
        AnhXa();

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.setFragment(this);
        setLogin_Button();

        txvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("fragment-back", 1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        txvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubActivity.class);
                intent.putExtra("fragment-back", 2);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        return view;
    }

    private void loginButton(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                result();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                result();
                loginButton.setVisibility(View.INVISIBLE);
                HomeFragment homeFragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("isloggin", 1);
                homeFragment.setArguments(bundle);

                ((MainActivity)getActivity()).replaceFragment(homeFragment, "Trang chủ");
                NavigationView naview = getActivity().findViewById(R.id.nav_view);
                naview.getMenu().findItem(R.id.nav_home).setChecked(true);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void result(){
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, email, first_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void AnhXa(){
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        txvSignUp = (TextView)view.findViewById(R.id.signup);
        txvForgotPass = (TextView)view.findViewById(R.id.txv_forgotpass);
    }

    @Override
    public void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}