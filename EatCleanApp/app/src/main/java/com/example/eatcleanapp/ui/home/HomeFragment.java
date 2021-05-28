package com.example.eatcleanapp.ui.home;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.tabHome.ViewPagerAdapterHome;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private TextView txv_user_fullName_home, txv_user_email_home;
    private View view;
    private ImageButton searchBox;
    private MainActivity mMainActivity;

    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mMainActivity = (MainActivity) getActivity();
        initUI();
        users mUser = DataLocalManager.getUser();
        if(mUser != null){
            settingHasUser(mUser);
        }
        else{
            Bundle bundle = getArguments();
            if(bundle != null){
                users user = (users) bundle.get("object_user");
                if(user != null){
                    settingHasUser(user);
                }
            }
        }
        return view;
    }

    private void settingHasUser(users mUser) {
        NavigationView navigationView = (NavigationView)mMainActivity.findViewById(R.id.nav_view);
        View headerView                 = navigationView.getHeaderView(0);
        TextView txv_fullName           = (TextView)headerView.findViewById(R.id.user_fullname_home);
        TextView txv_email              = (TextView)headerView.findViewById(R.id.user_email_home);
        txv_fullName.setText(mUser.getFullName());
        txv_email.setText(mUser.getEmail());
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_signin).setVisible(false);
        ImageButton btnProfile = (ImageButton)mMainActivity.findViewById(R.id.btnProfile);
        btnProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initUI(){
        viewPager = view.findViewById(R.id.view_pager_home);
        bottomNavigationView = view.findViewById(R.id.bottom_menu_home);
        ViewPagerAdapterHome viewPagerAdapter = new ViewPagerAdapterHome(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_recipes_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_blog_home:
                        viewPager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_recipes_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_blog_home).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}