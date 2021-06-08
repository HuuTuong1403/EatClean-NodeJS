package com.example.eatcleanapp.ui.home.tabHome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.home.tabHome.recipes.RecipesAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecipesFragment extends Fragment implements IClickListener {

    private View view;
    private RecyclerView rcvRecipes;
    private RecipesAdapter mRecipesAdapter;
    private List<recipes> listRecipes, oldList;
    private RequestQueue requestQueue;
    private EditText edt_search_recycle;
    private MainActivity mMainActivity;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_recipes, container, false);
        loadingDialog = new LoadingDialog(mMainActivity);
        Mapping();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        mRecipesAdapter = new RecipesAdapter(getContext(), this, false);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);;
        rcvRecipes.setLayoutManager(gridLayoutManager);
        loadingDialog.startLoadingDialog();
        GetData();
        rcvRecipes.setAdapter(mRecipesAdapter);
        Handler handler = new Handler();

        edt_search_recycle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fillText();
                        }
                    }, 400);
                    return true;
                }
                return false;
            }
        });

        edt_search_recycle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fillText();
                        }
                    }, 400);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void fillText() {
        String s = edt_search_recycle.getText().toString();
        mRecipesAdapter.getFilter().filter(s);
        listRecipes = mRecipesAdapter.change();
        Log.e("AAA", "size: " + listRecipes.size() + " string: " + s);
    }


    private void Mapping(){
        requestQueue = Volley.newRequestQueue(view.getContext());
        listRecipes = new ArrayList<>();
        oldList = new ArrayList<>();
        rcvRecipes = view.findViewById(R.id.list_recipes);
        edt_search_recycle = (EditText)mMainActivity.findViewById(R.id.edt_search_recycler);
    }


    public void GetData (){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/show-recipe")
                .method("GET", null)
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
                    listRecipes.add(recipe);
                    oldList.add(recipe);
                }
                mRecipesAdapter.setData(listRecipes);
                mRecipesAdapter.setOldData(oldList);
                loadingDialog.dismissDialog();
            }
            else {
                JSONObject error = Jobject.getJSONObject("error");
                Toast.makeText(view.getContext(),  error.toString() , Toast.LENGTH_LONG).show();
            }

        } catch (IOException | JSONException e) {
            Toast.makeText(view.getContext(),   e.toString() , Toast.LENGTH_LONG).show();
        }


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