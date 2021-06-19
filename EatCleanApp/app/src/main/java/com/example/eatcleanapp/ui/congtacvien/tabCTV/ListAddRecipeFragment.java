package com.example.eatcleanapp.ui.congtacvien.tabCTV;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipeimages;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

public class ListAddRecipeFragment extends Fragment implements IClickListener {

    private View view;
    private RecyclerView rcvUpdateRecipe;
    private UpdateRecipeAdapter updateRecipeAdapter;
    private List<recipes> listRecipes;
    private MainActivity mMainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_list_add_recipe, container, false);
        Mapping();
        CreateRecyclerView();
        GetData();
        rcvUpdateRecipe.setAdapter(updateRecipeAdapter);
        return view;
    }



    private void GetData() {
        users user = DataLocalManager.getUser();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/collaborator/show-my-recipes")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + user.getToken())
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
                            object.getString("ImageMain"),
                            object.getString("Time")
                    );
                    listRecipes.add(recipe);
                }
                updateRecipeAdapter.setData(listRecipes);
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
        updateRecipeAdapter = new UpdateRecipeAdapter(getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvUpdateRecipe.setLayoutManager(linearLayoutManager);
    }

    private void Mapping() {
        listRecipes = new ArrayList<>();
        rcvUpdateRecipe = view.findViewById(R.id.list_updateRecipe);
    }

    @Override
    public void clickItem(int position) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("detail-back", 3);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", listRecipes.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}