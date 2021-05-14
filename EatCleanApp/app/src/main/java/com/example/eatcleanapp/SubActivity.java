package com.example.eatcleanapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eatcleanapp.databinding.ActivitySubBinding;

import org.w3c.dom.Text;

public class SubActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySubBinding binding;
    private TextView txvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        txvTitle = (TextView)findViewById(R.id.txvTitle);
        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sub);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.toolbar.setNavigationIcon(R.drawable.back24);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        int id = intent.getIntExtra("fragment-back", 0);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.nav_graph);
        switch(id){
            case 1: {
                txvTitle.setText("Đăng ký");
                navGraph.setStartDestination(R.id.signup_fragment);
                break;
            }
            case 2: {
                txvTitle.setText("Quên mật khẩu");
                navGraph.setStartDestination(R.id.forgot_pass_fragment);
                break;
            }
            case 3: {
                txvTitle.setText("Thông tin tài khoản");
                navGraph.setStartDestination(R.id.profile_fragment);
                break;
            }
        }
        navController.setGraph(navGraph);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sub);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}