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
import android.widget.Toast;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class ProfileEditFragment extends Fragment {

    private View view;
    private SubActivity mSubActivity;
    private TextInputLayout profileEdit_layout_email, profileEdit_layout_fullName;
    private TextInputEditText profileEdit_edt_userName, profileEdit_edt_email, profileEdit_edt_fullName;
    private Button profileEdt_btn_saveChange, profileEdt_btn_cancelChange;
    private Toolbar toolbar;
    private users user;
    private List<users> lstUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubActivity = (SubActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        Mapping();
        user = DataLocalManager.getUser();

        profileEdit_edt_userName.setText(user.getUsername());

        //Set layout
        profileEdit_layout_email.setHint(user.getEmail());
        profileEdit_layout_fullName.setHint(user.getFullName());

        //Animate and Delay
        Animation animButton = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        profileEdt_btn_saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getUsers();
                        if(profileEdit_edt_email.getText().toString().isEmpty() || profileEdit_edt_fullName.getText().toString().isEmpty()){
                            Toast.makeText(mSubActivity, "Thông tin email hoặc họ tên không được trống", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            String Email = profileEdit_edt_email.getText().toString();
                            String FullName = profileEdit_edt_fullName.getText().toString();
                            boolean checkEmail = false;
                            for (users user: lstUsers) {
                                if (Email.equals(user.getEmail())) {
                                    checkEmail = true;
                                }
                            }
                            if(!checkEmail){
                                updateUser(user.getIDUser(), Email, FullName);
                                profileEdit_edt_email.setText("");
                                profileEdit_edt_fullName.setText("");
                                profileEdit_layout_email.setHint(Email);
                                profileEdit_layout_fullName.setHint(FullName);
                            }
                            else{
                                Toast.makeText(mSubActivity, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }
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
        profileEdit_edt_userName    = (TextInputEditText)view.findViewById(R.id.profileEdit_edt_userName);
        profileEdit_edt_email       = (TextInputEditText)view.findViewById(R.id.profileEdit_edt_email);
        profileEdit_edt_fullName    = (TextInputEditText)view.findViewById(R.id.profileEdit_edt_fullName);
        profileEdt_btn_saveChange   = (Button)view.findViewById(R.id.profileEdit_btn_saveChange);
        profileEdt_btn_cancelChange = (Button)view.findViewById(R.id.profileEdit_btn_cancelChange);
        lstUsers                    = new ArrayList<>();
    }

    private void getUsers(){
        APIService.apiService.getUser().enqueue(new Callback<List<users>>() {
            @Override
            public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                lstUsers = response.body();
            }

            @Override
            public void onFailure(Call<List<users>> call, Throwable t) {
                Toast.makeText(mSubActivity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserByUsername(String Username){
        APIService.apiService.getUserByUsername(Username).enqueue(new Callback<users>() {
            @Override
            public void onResponse(Call<users> call, Response<users> response) {
                DataLocalManager.setUser(response.body());
                user = DataLocalManager.getUser();
            }

            @Override
            public void onFailure(Call<users> call, Throwable t) {
                Toast.makeText(mSubActivity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(String IDUser, String Email, String FullName){
        APIService.apiService.updateUser(IDUser, Email, FullName).enqueue(new Callback<users>() {
            @Override
            public void onResponse(Call<users> call, Response<users> response) {
                getUserByUsername(user.getUsername());
                Toast.makeText(mSubActivity, "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<users> call, Throwable t) {
                Toast.makeText(mSubActivity, "Thay đổi thông tin thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}