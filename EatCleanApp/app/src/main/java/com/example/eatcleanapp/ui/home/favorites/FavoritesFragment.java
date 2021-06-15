package com.example.eatcleanapp.ui.home.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.home.signin.SignInFragment;
import com.example.eatcleanapp.ui.home.tabHome.recipes.RecipesAdapter;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FavoritesFragment extends Fragment implements IClickListener {

    private View view;
    private TextView favorite_txv_signIn_signUp;
    private MainActivity mMainActivity;
    private users userIsLogin;
    private NavigationView navigationView;
    private Menu menu;
    private RecyclerView rcv_Favorites_Recipes;
    private RecipesAdapter mRecipesAdapter;
    private List<recipes> listFavoritesRecipes;
    private LoadingDialog loadingDialog;
    private Handler hander;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        loadingDialog = new LoadingDialog(mMainActivity);
        userIsLogin = DataLocalManager.getUser();
        hander = new Handler();
        if(userIsLogin != null){
            view = inflater.inflate(R.layout.fragment_favorites, container, false);
            Mapping();
            CreateViewRecycler();
            loadingDialog.startLoadingDialog();
            hander.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetData();
                }
            }, 500);
            rcv_Favorites_Recipes.setAdapter(mRecipesAdapter);
        }
        else{
            view = inflater.inflate(R.layout.layout_favorites_no_user, container, false);
            Mapping();
            favorite_txv_signIn_signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMainActivity.replaceFragment(new SignInFragment(), "Đăng nhập");
                    menu.findItem(R.id.nav_signin).setChecked(true);
                    mMainActivity.setCurrentFragment(2);
                }
            });
        }

        return view;
    }

    private void CreateViewRecycler(){
        mRecipesAdapter = new RecipesAdapter(getContext(), this, true);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);;
        rcv_Favorites_Recipes.setLayoutManager(gridLayoutManager);
    }

    private void Mapping(){
        navigationView                  = (NavigationView)mMainActivity.findViewById(R.id.nav_view);
        favorite_txv_signIn_signUp = (TextView)view.findViewById(R.id.favorite_txv_signIn_signUp);
        menu                            = navigationView.getMenu();
        rcv_Favorites_Recipes           = (RecyclerView)view.findViewById(R.id.rcv_favoritesList_User);
        listFavoritesRecipes = new ArrayList<>();
        loadingDialog = new LoadingDialog(mMainActivity);
    }

    public void GetData (){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/show-recipe-favorite")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + userIsLogin.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
                //JSONObject data = Jobject.getJSONObject("data");
                JSONArray myResponse = Jobject.getJSONArray("data");
                for (int i = 0; i < myResponse.length();i ++){
                    JSONObject object = myResponse.getJSONObject(i);
                    recipes recipe = new recipes(
                            object.getString("_id"),
                            object.getString("RecipesTitle"),
                            object.getString("RecipesAuthor"),
                            object.getString("RecipesContent"),
                            object.getString("NutritionalIngredients"),
                            object.getString("Ingredients"),
                            object.getString("Steps"),
                            object.getString("IDAuthor"),
                            object.getString("Status"),
                            object.getString("ImageMain")
                    );
                    listFavoritesRecipes.add(recipe);
                }
                mRecipesAdapter.setData(listFavoritesRecipes);
            }
            else {
                JSONObject error = Jobject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mMainActivity)
                        .setTitle("Thông báo")
                        .setMessage("Không thể tải dữ liệu")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mMainActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            loadingDialog.dismissDialog();
        }
    }

    @Override
    public void clickItem(int position) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("detail-back", 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", listFavoritesRecipes.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}