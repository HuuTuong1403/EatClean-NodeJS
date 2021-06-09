package com.example.eatcleanapp.ui.home.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.databinding.ActivityDetailBinding;
import com.example.eatcleanapp.model.blogs;
import com.example.eatcleanapp.model.recipes;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
    private recipes recipes_detail;
    private blogs blogs_detail;

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
                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    recipes_detail = (recipes) bundle.get("item");
                    txvDetail.setText(recipes_detail.getRecipesTitle());
                    binding.toolbarDetail.setNavigationIcon(R.drawable.back24);
                    navGraph.setStartDestination(R.id.detail_recipes_fragment);
                    navController.setGraph(navGraph);
                    break;
                }
            }
            case 2:{
                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    blogs_detail = (blogs) bundle.get("item");
                    txvDetail.setText("Chi tiết blog");
                    binding.toolbarDetail.setNavigationIcon(R.drawable.back24);
                    navGraph.setStartDestination(R.id.detail_blogs_fragment);
                    navController.setGraph(navGraph);
                    break;
                }
            }
            case 3:{
                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    recipes_detail = (recipes) bundle.get("item");
                    txvDetail.setText(recipes_detail.getRecipesTitle());
                    binding.toolbarDetail.setNavigationIcon(R.drawable.back24);
                    navGraph.setStartDestination(R.id.update_recipes_fragment);
                    navController.setGraph(navGraph);
                    break;
                }
            }
            case 4:{
                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    blogs_detail = (blogs) bundle.get("item");
                    txvDetail.setText("Chỉnh sửa blog");
                    binding.toolbarDetail.setNavigationIcon(R.drawable.back24);
                    navGraph.setStartDestination(R.id.update_blogs_fragment);
                    navController.setGraph(navGraph);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_detail);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public recipes getRecipes(){
        return recipes_detail;
    }

    public blogs getBlogs(){ return blogs_detail; }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK){
            setResult(RESULT_OK, data);
        }
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