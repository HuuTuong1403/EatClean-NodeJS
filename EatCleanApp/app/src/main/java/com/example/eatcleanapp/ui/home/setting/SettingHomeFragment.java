package com.example.eatcleanapp.ui.home.setting;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.HomeFragment;
import com.example.eatcleanapp.ui.home.signin.SignInFragment;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.google.android.material.navigation.NavigationView;

public class SettingHomeFragment extends Fragment {

    private View view;
    private Button setting_home_btn_isLogIn, setting_home_btn_giveFeedBack;
    private TextView setting_home_txv_show_isLogIn;
    private MainActivity mMainActivity;
    private users user;
    private NavigationView navigationView;
    private Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_setting_home, container, false);
        Mapping();
        user = DataLocalManager.getUser();
        Animation animButtom = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_scale);
        Handler handler = new Handler();

        if(user != null){
            String s = "Bạn đang đăng nhập với tài khoản <b>" + user.getUsername() + "</b>" ;
            setting_home_txv_show_isLogIn.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY));
            setting_home_btn_isLogIn.setText("Đăng xuất");
            setting_home_btn_isLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(animButtom);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DataLocalManager.deleteUser();
                            settingLogOut();
                        }
                    }, 400);
                }
            });
        }
        else{
            setting_home_txv_show_isLogIn.setText("Bạn đã đăng xuất khỏi hệ thống");
            setting_home_btn_isLogIn.setText("Đăng nhập");
            setting_home_btn_isLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(animButtom);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMainActivity.settingSignIn();
                        }
                    },400);
                }
            });
        }
        return view;
    }

    //Setting when User LogOut
    private void settingLogOut(){
        View headerView                 = navigationView.getHeaderView(0);
        TextView txv_fullName           = (TextView)headerView.findViewById(R.id.user_fullname_home);
        TextView txv_email              = (TextView)headerView.findViewById(R.id.user_email_home);
        ImageView user_avatar_home      = (ImageView)headerView.findViewById(R.id.user_avatar_home);
        ImageButton img_search          = (ImageButton)mMainActivity.findViewById(R.id.searchBox);
        img_search.setVisibility(View.VISIBLE);
        txv_fullName.setText("Eat Clean Application");
        txv_email.setText("eatcleanCompany@gmail.com");
        user_avatar_home.setImageResource(R.drawable.imageheader);

        menu.findItem(R.id.nav_signin).setVisible(true);
        ImageButton btnProfile = (ImageButton)mMainActivity.findViewById(R.id.btnProfile);
        btnProfile.setVisibility(View.INVISIBLE);
        setting_home_txv_show_isLogIn.setText("Bạn đã đăng xuất khỏi hệ thống");
        setting_home_btn_isLogIn.setText("Đăng nhập");
        mMainActivity.replaceFragment(new HomeFragment(), "Trang chủ");
        mMainActivity.setCurrentFragment(1);
        menu.findItem(R.id.nav_home).setChecked(true);
    }

    private void Mapping() {
        setting_home_txv_show_isLogIn   = (TextView)view.findViewById(R.id.setting_home_txv_show_isLogIn);
        setting_home_btn_isLogIn        = (Button)view.findViewById(R.id.setting_home_btn_isLogIn);
        setting_home_btn_giveFeedBack   = (Button)view.findViewById(R.id.setting_home_btn_giveFeedBack);
        navigationView                  = (NavigationView)mMainActivity.findViewById(R.id.nav_view);
        menu                            = navigationView.getMenu();
    }
}