package com.example.eatcleanapp.ui.home.forgotpass;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.ui.home.profile.ProfileFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentForgotPassword extends Fragment {

    private View view;
    private SubActivity mSubActivity;
    private Toolbar toolbar;
    private EditText forgot_pass_edtUsername, forgot_pass_edtPhone, forgot_pass_edtCode;
    private Button forgot_pass_btnAcceptCode, forgot_pass_btnSendCode;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubActivity = (SubActivity) getActivity();
        mSubActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        Mapping();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        Animation animButton = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        forgot_pass_btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendCodeToPhoneNumber();
                    }
                }, 400);
            }
        });

        forgot_pass_btnAcceptCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavHostFragment.findNavController(FragmentForgotPassword.this).navigate(R.id.action_forgot_pass_fragment_to_forgotChange_pass_fragment);
                    }
                }, 400);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubActivity.finish();
            }
        });

        return view;
    }

    private void SendCodeToPhoneNumber() {
        String test = forgot_pass_edtUsername.getText().toString();
        if(forgot_pass_edtUsername.getText().toString().isEmpty() || forgot_pass_edtPhone.getText().toString().isEmpty()){
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mSubActivity)
                    .setTitle("Thông báo")
                    .setMessage("Thông tin email hoặc họ tên không được trống")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
        }
        else{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://eat-clean-nhom04.herokuapp.com/me/send-password-sms?Username=" + forgot_pass_edtUsername.getText().toString().trim()
                                        + "&SoDienThoai=" + forgot_pass_edtPhone.getText().toString().trim())
                    .method("GET", null)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                            .setActivity(mSubActivity)
                            .setTitle("Thông báo")
                            .setMessage("Đã gửi mã code thành công, vui lòng kiểm tra tin nhắn")
                            .setType("success")
                            .Build();
                    customAlertActivity.showDialog();
                }
                else {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONObject error = jsonObject.getJSONObject("error");
                    CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                            .setActivity(mSubActivity)
                            .setTitle("Thông báo")
                            .setMessage("Đã gửi mã code thất bại")
                            .setType("error")
                            .Build();
                    customAlertActivity.showDialog();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void Mapping(){
        toolbar                     = (Toolbar)mSubActivity.findViewById(R.id.toolbar);
        forgot_pass_edtUsername     = (EditText)view.findViewById(R.id.forgot_pass_edtUsername);
        forgot_pass_edtPhone        = (EditText)view.findViewById(R.id.forgot_pass_edtPhone);
        forgot_pass_edtCode         = (EditText)view.findViewById(R.id.forgot_pass_edtCode);
        forgot_pass_btnAcceptCode   = (Button)view.findViewById(R.id.forgot_pass_btnAcceptCode);
        forgot_pass_btnSendCode     = (Button)view.findViewById(R.id.forgot_pass_btnSendCode);
    }
}