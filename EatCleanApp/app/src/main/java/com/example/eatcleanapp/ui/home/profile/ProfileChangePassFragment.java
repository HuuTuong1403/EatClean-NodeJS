package com.example.eatcleanapp.ui.home.profile;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;


public class ProfileChangePassFragment extends Fragment {

    private View view;
    private SubActivity mSubActivity;
    private Toolbar toolbar;
    private EditText profileChangePass_edt_oldPassword, profileChangePass_edt_newPassword, profileChangePass_edt_newPasswordAgain;
    private Button profile_changePass_btn_changePass, profile_changePass_btn_cancel;
    private users user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_change_pass, container, false);
        mSubActivity = (SubActivity) getActivity();
        Mapping();

        Animation animButton = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();
        user = DataLocalManager.getUser();
        profile_changePass_btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(profileChangePass_edt_oldPassword.getText().toString().isEmpty() || profileChangePass_edt_newPassword.getText().toString().isEmpty() || profileChangePass_edt_newPasswordAgain.getText().toString().isEmpty()){
                            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                    .setActivity(mSubActivity)
                                    .setTitle("Thông báo")
                                    .setMessage("Trường thông tin không được trống")
                                    .setType("error")
                                    .Build();
                            customAlertActivity.showDialog();
                        }
                        else{
                            String oldPass = profileChangePass_edt_oldPassword.getText().toString();
                            String newPass = profileChangePass_edt_newPassword.getText().toString();
                            String newPassAgain = profileChangePass_edt_newPasswordAgain.getText().toString();
                            if(newPass.equals(oldPass)){
                                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                        .setActivity(mSubActivity)
                                        .setTitle("Thông báo")
                                        .setMessage("Mật khẩu mới phải khác mật khẩu cũ")
                                        .setType("error")
                                        .Build();
                                customAlertActivity.showDialog();                            }
                            else{
                                if(newPassAgain.equals(newPass)){
                                    changePass(user.getToken(), newPass, newPassAgain,oldPass);
                                }
                                else{
                                    CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                            .setActivity(mSubActivity)
                                            .setTitle("Thông báo")
                                            .setMessage("Mật khẩu nhập lại không đúng")
                                            .setType("error")
                                            .Build();
                                    customAlertActivity.showDialog();
                                }
                            }
                        }
                    }
                }, 400);
            }
        });

        profile_changePass_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        profileChangePass_edt_oldPassword.setText("");
                        profileChangePass_edt_newPassword.setText("");
                        profileChangePass_edt_newPasswordAgain.setText("");
                    }
                }, 400);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProfileChangePassFragment.this).navigate(R.id.action_profile_changePass_fragment_to_profile_fragment);
            }
        });

        return view;
    }


    private void changePass(String userToken, String Password, String confirmPassword ,String OldPassword ){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonSignIn = new JSONObject();
        try {
            jsonSignIn.put("PasswordOld", OldPassword);
            jsonSignIn.put("PasswordNew", Password);
            jsonSignIn.put("ConfirmPassword", confirmPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(mediaType, jsonSignIn.toString());
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/change-password")
                .method("PUT", body)
                .addHeader("Authorization", "Bearer " + userToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
                JSONObject data = Jobject.getJSONObject("data");
                Gson g = new Gson();
                users usertemp = g.fromJson(String.valueOf(data), users.class);
                usertemp.setToken(user.getToken());
                DataLocalManager.setUser(usertemp);
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mSubActivity)
                        .setTitle("Thông báo")
                        .setMessage("Đổi mật khẩu thành công")
                        .setType("success")
                        .Build();
                customAlertActivity.showDialog();
                profileChangePass_edt_oldPassword.setText("");
                profileChangePass_edt_newPassword.setText("");
                profileChangePass_edt_newPasswordAgain.setText("");
            }
            else {
                String error = Jobject.getString("error");
                if(error.trim().equals("Wrong Old Password")){
                    CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder().setActivity(mSubActivity)
                            .setTitle("Thông báo")
                            .setMessage("Mật khẩu cũ không đúng")
                            .setType("error")
                            .Build();
                    customAlertActivity.showDialog();
                }
                else{
                    CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                            .setActivity(mSubActivity)
                            .setTitle("Thông báo")
                            .setMessage("Đổi mật khẩu thất bại")
                            .setType("error")
                            .Build();
                    customAlertActivity.showDialog();
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mSubActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
        }
    }

    private void Mapping() {
        toolbar = (Toolbar)mSubActivity.findViewById(R.id.toolbar);

        mSubActivity.setText("Đổi mật khẩu");
        mSubActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);

        profileChangePass_edt_oldPassword       = (EditText)view.findViewById(R.id.profileChangePass_edt_oldPassword);
        profileChangePass_edt_newPassword       = (EditText)view.findViewById(R.id.profileChangePass_edt_newPassword);
        profileChangePass_edt_newPasswordAgain  = (EditText)view.findViewById(R.id.profileChangePass_edt_newPasswordAgain);
        profile_changePass_btn_changePass       = (Button)view.findViewById(R.id.profileChangePass_btn_changePass);
        profile_changePass_btn_cancel           = (Button)view.findViewById(R.id.pprofileChangePass_btn_cancel);
    }
}