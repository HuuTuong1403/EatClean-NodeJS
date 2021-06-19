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
            CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    forgot_pass_btnSendCode.setEnabled(false);
                    forgot_pass_btnSendCode.setText("0:" + millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    forgot_pass_btnSendCode.setText("Gửi lại mã xác nhận");
                    forgot_pass_btnSendCode.setEnabled(true);
                }
            }.start();
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