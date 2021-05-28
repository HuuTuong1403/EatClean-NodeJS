package com.example.eatcleanapp.ui.home.signup;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.model.users;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpFragment extends Fragment {

    private View view;
    private TextInputEditText edtUsername, edtEmail, edtPassword, edtPasswordAgain, edtFullName;
    private Button btnRegister;
    private final String registerUserLink = "https://msteatclean.000webhostapp.com/registerUser.php";
    private final String getUserLink = "https://msteatclean.000webhostapp.com/getUser.php";
    private  List<users> usersList;
    private ScrollView scrollView;
    private SubActivity mSubActivity;
    private String IDUser;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mSubActivity = (SubActivity) getActivity();
        View background = view.findViewById(R.id.backgroundSignUp);
        Drawable backgroundImage = background.getBackground();
        backgroundImage.setAlpha(80);
        Mapping();

        usersList = new ArrayList<>();
        GetData();

        Animation animTranslate = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random rd = new Random();

                        String Username = edtUsername.getText().toString().trim();
                        String Email = edtEmail.getText().toString().trim();

                        boolean checkIDUser = true;
                        while (checkIDUser){
                            checkIDUser = false;
                            int x = rd.nextInt((50000-1000 + 1) + 1000);
                            IDUser = "ID-U-" + x;
                            for (users user: usersList
                            ) {
                                if (IDUser.equals(user.getIDUser())){
                                    checkIDUser = true;
                                    break;
                                }
                            }
                        }

                        boolean checkEmail = false;
                        boolean checkUsername = false;
                        for (users user: usersList) {
                            if (Email.equals(user.getEmail())){
                                checkEmail = true;
                            }
                            if (Username.equals(user.getUsername())){
                                checkUsername = true;
                            }
                        }
                        if (!checkEmail){
                            if (!checkUsername){
                                if (edtPassword.getText().toString().trim().equals(edtPasswordAgain.getText().toString().trim())){
                                    registerUser(registerUserLink);
                                }
                                else{
                                    Toast.makeText(getActivity(), "Mật khẩu không giống nhau, vui lòng nhập lại", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(getActivity(), "Username đã bị trùng, vui lòng nhập email khác", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "Email đã bị trùng, vui lòng nhập email khác", Toast.LENGTH_LONG).show();
                        }
                    }
                }, 400);
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
        scrollView = (ScrollView)view.findViewById(R.id.backgroundSignUp);
    }
    private void registerUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(mSubActivity.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Bạn đã bị lỗi" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams () throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("IDUser", IDUser);
                params.put("Email", edtEmail.getText().toString().trim());
                params.put("Password", edtPassword.getText().toString().trim());
                params.put("FullName", edtFullName.getText().toString().trim());
                params.put("Image", "");
                params.put("LoginFB", "0");
                params.put("IDRole", "R003");
                params.put("Username", edtUsername.getText().toString().trim());
                return  params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetData (){
        APIService.apiService.getUser().enqueue(new Callback<List<users>>() {
            @Override
            public void onResponse(@NotNull Call<List<users>> call, @NotNull retrofit2.Response<List<users>> response) {
                usersList = response.body();
            }

            @Override
            public void onFailure(@NotNull Call<List<users>> call, @NotNull Throwable t) {
                Toast.makeText(view.getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}