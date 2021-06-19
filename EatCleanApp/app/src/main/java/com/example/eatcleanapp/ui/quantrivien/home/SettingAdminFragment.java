package com.example.eatcleanapp.ui.quantrivien.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.example.eatcleanapp.ui.quantrivien.AdminActivity;

public class SettingAdminFragment extends Fragment {

    private View view;
    private AdminActivity mAdminActivity;
    private TextView setting_admin_txv_show_isLogIn;
    private Button setting_admin_btn_isLogIn;
    private users user;

    public static SettingAdminFragment newInstance() { return new SettingAdminFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAdminActivity = (AdminActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_setting_admin, container, false);
        Mapping();

        setting_admin_btn_isLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOutAdmin();
            }
        });

        return view;
    }

    private void LogOutAdmin(){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        DataLocalManager.deleteUser();
        startActivity(intent);
        mAdminActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mAdminActivity.finish();
    }

    private void Mapping() {
        user = DataLocalManager.getUser();
        setting_admin_txv_show_isLogIn  = (TextView)view.findViewById(R.id.setting_admin_txv_show_isLogIn);
        setting_admin_btn_isLogIn       = (Button)view.findViewById(R.id.setting_admin_btn_isLogIn);

        if(user == null){
            return;
        }
        else{
            String s = "Bạn đang đăng nhập với tài khoản <b>" + user.getUsername() + "</b>" + " là quản trị viên";
            setting_admin_txv_show_isLogIn.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY));
        }
    }


}