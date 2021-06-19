package com.example.eatcleanapp.ui.home.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.POST;

public class ProfileEditFragment extends Fragment {

    private View view;
    private SubActivity mSubActivity;
    private TextInputLayout profileEdit_layout_email, profileEdit_layout_fullName, profileEdit_layout_sdt;
    private TextInputEditText profileEdit_edt_userName, profileEdit_edt_email, profileEdit_edt_fullName, profileEdit_edt_sdt;
    private Button profileEdt_btn_saveChange, profileEdt_btn_cancelChange;
    private ImageView imageView_avatar_user_edit;
    private Toolbar toolbar;
    private users user;
    private List<users> lstUsers;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubActivity = (SubActivity) getActivity();
        loadingDialog = new LoadingDialog(mSubActivity);
        view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        Mapping();
        user = DataLocalManager.getUser();
        if(user != null){
            profileEdit_edt_userName.setText(user.getUsername());

            //Set layout
            profileEdit_layout_email.setHint(user.getEmail());
            profileEdit_layout_fullName.setHint(user.getFullName());
            profileEdit_layout_sdt.setHint(user.getSoDienThoai());

            Glide.with(view).load(user.getImage()).placeholder(R.drawable.gray).into(imageView_avatar_user_edit);
        }

        //Animate and Delay
        Animation animButton = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        profileEdt_btn_saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(profileEdit_edt_email.getText().toString().isEmpty() ||
                                profileEdit_edt_fullName.getText().toString().isEmpty() ||
                        profileEdit_edt_sdt.getText().toString().isEmpty()){
                            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                    .setActivity(mSubActivity)
                                    .setTitle("Thông báo")
                                    .setMessage("Thông tin email hoặc họ tên không được trống")
                                    .setType("error")
                                    .Build();
                            customAlertActivity.showDialog();
                        }
                        else{
                            String Email = profileEdit_edt_email.getText().toString();
                            String FullName = profileEdit_edt_fullName.getText().toString();
                            String Phone = profileEdit_edt_sdt.getText().toString();
                            updateUser(Email, FullName, Phone);
                        }
                        loadingDialog.dismissDialog();
                    }
                }, 400);
            }
        });

        profileEdt_btn_cancelChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        profileEdit_edt_email.setText("");
                        profileEdit_edt_fullName.setText("");
                        profileEdit_edt_sdt.setText("");
                    }
                }, 400);

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProfileEditFragment.this).navigate(R.id.action_profile_edit_fragment_to_profile_fragment);
            }
        });

        return view;
    }

    private void Mapping() {
        toolbar = (Toolbar)mSubActivity.findViewById(R.id.toolbar);
        mSubActivity.setText("Chỉnh sửa thông tin");
        mSubActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);

        profileEdit_layout_email    = (TextInputLayout)view.findViewById(R.id.profileEdit_layout_email);
        profileEdit_layout_fullName = (TextInputLayout)view.findViewById(R.id.profileEdit_layout_fullName);
        profileEdit_layout_sdt      = (TextInputLayout) view.findViewById(R.id.profileEdit_layout_sdt);
        profileEdit_edt_userName    = (TextInputEditText)view.findViewById(R.id.profileEdit_edt_userName);
        profileEdit_edt_email       = (TextInputEditText)view.findViewById(R.id.profileEdit_edt_email);
        profileEdit_edt_fullName    = (TextInputEditText)view.findViewById(R.id.profileEdit_edt_fullName);
        profileEdt_btn_saveChange   = (Button)view.findViewById(R.id.profileEdit_btn_saveChange);
        profileEdt_btn_cancelChange = (Button)view.findViewById(R.id.profileEdit_btn_cancelChange);
        profileEdit_edt_sdt         = (TextInputEditText)view.findViewById(R.id.profileEdit_edt_sdt);
        imageView_avatar_user_edit  = (ImageView)view.findViewById(R.id.imageView_avatar_user_edit);
        lstUsers                    = new ArrayList<>();
    }


    private void updateUser(String Email, String FullName, String Phone){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("FullName", FullName)
                .addFormDataPart("Email", Email)
                .addFormDataPart("SoDienThoai", Phone)
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/edit-information")
                .method("PUT", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
                JSONObject data = Jobject.getJSONObject("data");
                Gson g = new Gson();
                user = g.fromJson(String.valueOf(data), users.class);
                DataLocalManager.setUser(user);
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mSubActivity)
                        .setTitle("Thông báo")
                        .setMessage("Chỉnh sửa tài khoản thành công")
                        .setType("success")
                        .Build();
                customAlertActivity.showDialog();
            }
            else{
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mSubActivity)
                        .setTitle("Thông báo")
                        .setMessage("Chỉnh sửa tài khoản thất bại")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mSubActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            e.printStackTrace();
        }
    }
}