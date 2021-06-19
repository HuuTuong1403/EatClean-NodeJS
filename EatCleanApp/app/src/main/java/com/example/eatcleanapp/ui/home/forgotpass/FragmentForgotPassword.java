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
import com.example.eatcleanapp.ui.home.LoadingDialog;
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
    private EditText forgot_pass_edtUsername, forgot_pass_edtPhone;
    private Button forgot_pass_btnSendCode;
    private LoadingDialog loadingDialog;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubActivity = (SubActivity) getActivity();
        mSubActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);
        loadingDialog = new LoadingDialog(mSubActivity);
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        Mapping();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        Animation animButton = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        forgot_pass_btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SendCodeToPhoneNumber();
                    }
                }, 400);
            }
        });

        return view;
    }

    private void SendCodeToPhoneNumber() {
        if(forgot_pass_edtUsername.getText().toString().isEmpty() || forgot_pass_edtPhone.getText().toString().isEmpty()){
            loadingDialog.dismissDialog();
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mSubActivity)
                    .setTitle("Thông báo")
                    .setMessage("Thông tin tài khoản và số điện thoại không được trống")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
        }
        else if(forgot_pass_edtPhone.getText().length() < 10 || forgot_pass_edtPhone.getText().length() > 10){
            loadingDialog.dismissDialog();
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mSubActivity)
                    .setTitle("Thông báo")
                    .setMessage("Số điện thoại không chính xác")
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
                            .setMessage("Đã gửi mật khẩu mới thành công! Vui lòng kiểm tra tin nhắn")
                            .setType("success")
                            .Build();
                    customAlertActivity.showDialog();
                    forgot_pass_edtPhone.setText("");
                    forgot_pass_edtUsername.setText("");
                }
                else {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String error = jsonObject.getString("error");
                    CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                            .setActivity(mSubActivity)
                            .setTitle("Thông báo")
                            .setMessage(error)
                            .setType("error")
                            .Build();
                    customAlertActivity.showDialog();
                }
                loadingDialog.dismissDialog();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                loadingDialog.dismissDialog();
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mSubActivity)
                        .setTitle("Thông báo")
                        .setMessage("Đã xảy ra lỗi!!!")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
        }
    }

    private void Mapping(){
        toolbar                     = (Toolbar)mSubActivity.findViewById(R.id.toolbar);
        forgot_pass_edtUsername     = (EditText)view.findViewById(R.id.forgot_pass_edtUsername);
        forgot_pass_edtPhone        = (EditText)view.findViewById(R.id.forgot_pass_edtPhone);
        forgot_pass_btnSendCode     = (Button)view.findViewById(R.id.forgot_pass_btnSendCode);
    }
}