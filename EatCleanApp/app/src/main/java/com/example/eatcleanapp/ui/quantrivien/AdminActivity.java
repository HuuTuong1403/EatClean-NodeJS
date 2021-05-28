package com.example.eatcleanapp.ui.quantrivien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.databinding.ActivityAdminBinding;
import com.example.eatcleanapp.model.users;

import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.example.eatcleanapp.ui.quantrivien.home.HomeAdminFragment;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityAdminBinding binding;
    private TextView txvTitleAdmin;
    private static final int FRAGMENT_HOME = 1;
    private int currentFragment = FRAGMENT_HOME;
    private ImageButton btnProfile_Admin;
    private NavigationView navigationView;
    private users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Mapping();
        setSupportActionBar(binding.appBarAdmin.toolbarAdmin);
        navigationView = binding.navAdmin;
        DrawerLayout drawerLayout = binding.drawerAdmin;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, binding.appBarAdmin.toolbarAdmin, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu24);

        user = DataLocalManager.getUser();
        if(user != null){

        }
        else{
            Bundle bundleReceive = getIntent().getExtras();
            if(bundleReceive != null){
                users user = (users) bundleReceive.get("object_user");
                if(user != null){
                    ChangeText(user);
                }
            }
        }

        btnProfile_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, SubActivity.class);
                intent.putExtra("fragment-back", 3);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        replaceFragment(new HomeAdminFragment(), "Trang chủ Admin");
        currentFragment = FRAGMENT_HOME;
        navigationView.getMenu().findItem(R.id.menu_home_nav_admin).setChecked(true);
    }

    private void Mapping(){
        txvTitleAdmin = (TextView)findViewById(R.id.txvTitleAdmin);
        txvTitleAdmin.setText("Trang của Admin");
        btnProfile_Admin = (ImageButton)findViewById(R.id.btnProfile_Admin);

    }

    private void ChangeText(users user){
        NavigationView navigationView   = (NavigationView)findViewById(R.id.nav_admin);
        View headerView                 = navigationView.getHeaderView(0);
        TextView txv_fullname           = (TextView)headerView.findViewById(R.id.user_fullname_admin);
        TextView txv_email              = (TextView)headerView.findViewById(R.id.user_email_admin);
        txv_fullname.setText(user.getFullName());
        txv_email.setText(user.getEmail());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = binding.drawerAdmin;
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_home_nav_admin:
                if(FRAGMENT_HOME != currentFragment)
                {
                    replaceFragment(new HomeAdminFragment(), "Trang chủ");
                    currentFragment = FRAGMENT_HOME;
                }
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_admin);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment, String title){
        txvTitleAdmin.setText(title);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_admin, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(v instanceof EditText){
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if(!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())){
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}