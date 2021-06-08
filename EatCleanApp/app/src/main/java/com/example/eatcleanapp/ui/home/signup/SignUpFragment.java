package com.example.eatcleanapp.ui.home.signup;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpFragment extends Fragment {

    private View view;
    private TextInputEditText edtUsername, edtEmail, edtPassword, edtPasswordAgain, edtFullName, edtPhone;
    private Button btnRegister;;
    private ScrollView scrollView;
    private SubActivity mSubActivity;
    private String IDUser;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saved0InstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mSubActivity = (SubActivity) getActivity();
        View background = view.findViewById(R.id.backgroundSignUp);
        Drawable backgroundImage = background.getBackground();
        backgroundImage.setAlpha(80);
        Mapping();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        Animation animTranslate = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registerUser();
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
        edtPhone = (TextInputEditText) view.findViewById(R.id.signup_edtSDT);
        scrollView = (ScrollView)view.findViewById(R.id.backgroundSignUp);
    }
    private void registerUser(){
        String password =  edtPassword.getText().toString().trim();
        String passwordAgain = edtPasswordAgain.getText().toString().trim();
        if (password.equals(passwordAgain)){
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("Email",edtEmail.getText().toString().trim())
                    .addFormDataPart("Password",edtPassword.getText().toString().trim())
                    .addFormDataPart("FullName",edtFullName.getText().toString().trim())
                    .addFormDataPart("Username",edtUsername.getText().toString().trim())
                    .addFormDataPart("SoDienThoai",edtPhone.getText().toString().trim())
                    .build();
            Request request = new Request.Builder()
                    .url("https://eat-clean-nhom04.herokuapp.com/user/register-user")
                    .method("POST", body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    Toast.makeText(view.getContext(),  "Đăng ký thành công", Toast.LENGTH_LONG).show();
                }
                else {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONObject error = jsonObject.getJSONObject("error");
                    Toast.makeText(view.getContext(),  error.toString() , Toast.LENGTH_LONG).show();
                }
            } catch (IOException | JSONException e) {
                Toast.makeText(view.getContext(), e.toString() + "", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(view.getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        }
    }


}