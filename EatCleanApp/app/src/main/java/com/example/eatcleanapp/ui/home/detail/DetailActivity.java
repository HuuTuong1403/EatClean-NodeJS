package com.example.eatcleanapp.ui.home.detail;

import android.content.Intent;
import android.os.Bundle;

import com.example.eatcleanapp.databinding.ActivityDetailBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eatcleanapp.R;

public class DetailActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDetailBinding binding;
    private TextView txvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        txvDetail = (TextView)findViewById(R.id.txvDetail);
        setSupportActionBar(binding.toolbarDetail);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_detail);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.toolbarDetail.setNavigationIcon(R.drawable.back24);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.toolbarDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        int id = intent.getIntExtra("detail-back", 0);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.nav_graph2);
        switch(id){
            case 1: {
                txvDetail.setText("Tên món ăn");
                binding.toolbarDetail.setNavigationIcon(R.drawable.back24);
                navGraph.setStartDestination(R.id.detail_recipes_fragment);
                break;
            }
            case 2:{
                txvDetail.setText("Tên blog");
                navGraph.setStartDestination(R.id.detail_blogs_fragment);
                break;
            }
        }
        navController.setGraph(navGraph);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_detail);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}