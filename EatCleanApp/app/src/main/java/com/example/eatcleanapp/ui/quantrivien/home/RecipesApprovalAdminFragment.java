package com.example.eatcleanapp.ui.quantrivien.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipeimages;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.example.eatcleanapp.ui.quantrivien.AdminActivity;
import com.example.eatcleanapp.ui.quantrivien.home.adapter.ApprovalRecipeAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipesApprovalAdminFragment extends Fragment implements IClickListener {

    private View view;
    private RecyclerView rcvApprovalRecipes;
    private List<recipes> listRecipes;
    private ApprovalRecipeAdapter approvalRecipeAdapter;
    private AdminActivity mAdminActivity;
    private List<recipeimages> listRecipeImage;
    private BottomNavigationView bottomNavigationView;
    private users user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        mAdminActivity = (AdminActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_recipes_approval_admin, container, false);
        user = DataLocalManager.getUser();
        Mapping();
        CreateRecyclerView();
        GetData();
        rcvApprovalRecipes.setAdapter(approvalRecipeAdapter);
        return view;
    }

    public void GetData() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/admin/show-recipe-inconfirm")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
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
                            object.getString("ImageMain"),
                            object.getString("Time")
                    );
                    listRecipes.add(recipe);
                }
                setBadge();
                approvalRecipeAdapter.setData(listRecipes);
            }
            else {
                JSONObject error = Jobject.getJSONObject("error");
                Toast.makeText(view.getContext(),  error.toString() , Toast.LENGTH_LONG).show();
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void CreateRecyclerView() {
        approvalRecipeAdapter = new ApprovalRecipeAdapter(view.getContext(), this, mAdminActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvApprovalRecipes.setLayoutManager(linearLayoutManager);
    }

    public void Mapping() {
        listRecipes = new ArrayList<>();
        rcvApprovalRecipes = view.findViewById(R.id.list_recipes_approval);
        listRecipeImage = new ArrayList<>();
        bottomNavigationView = mAdminActivity.findViewById(R.id.bottom_menu_admin);
    }

    public void setBadge(){
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_recipes24_not_approval);
        badgeDrawable.setNumber(listRecipes.size());
        badgeDrawable.setVisible(true);
    }

    @Override
    public void clickItem(int position) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("detail-back", 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", listRecipes.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}