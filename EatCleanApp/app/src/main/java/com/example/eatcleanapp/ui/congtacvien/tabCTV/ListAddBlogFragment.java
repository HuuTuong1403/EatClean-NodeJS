package com.example.eatcleanapp.ui.congtacvien.tabCTV;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.blogimages;
import com.example.eatcleanapp.model.blogs;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.home.tabHome.blogs.BlogsAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAddBlogFragment extends Fragment implements IClickListener {

    private View view;
    private RecyclerView rcvBlogs;
    private BlogsAdapter mBlogsAdapter;
    private List<blogs> listBlogs;
    private MainActivity mMainActivity;
    private List<blogimages> listBlogsImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_list_add_blog, container, false);
        Mapping();
        CreateRecyclerView();
        GetData();
        rcvBlogs.setAdapter(mBlogsAdapter);

        return view;
    }

    private void CreateRecyclerView(){
        mBlogsAdapter = new BlogsAdapter(getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvBlogs.setLayoutManager(linearLayoutManager);
    }

    private void GetData(){
        APIService.apiService.getBlogs().enqueue(new Callback<List<blogs>>() {
            @Override
            public void onResponse(Call<List<blogs>> call, Response<List<blogs>> response) {
                listBlogs = response.body();
                mBlogsAdapter.setData(listBlogs);
                GetImage();
            }

            @Override
            public void onFailure(Call<List<blogs>> call, Throwable t) {
                Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetImage(){
        APIService.apiService.getImageBlog().enqueue(new Callback<List<blogimages>>() {
            @Override
            public void onResponse(Call<List<blogimages>> call, Response<List<blogimages>> response) {
                listBlogsImage = response.body();
                for(int i = 0; i < listBlogs.size(); i++){
                    for(int j = 0; j < listBlogsImage.size(); j++){
                        if(listBlogs.get(i).get_id().equals(listBlogsImage.get(j).getIDBlog())){
                            listBlogs.get(i).setImageMain(listBlogsImage.get(j).getBlogImages());
                            break;
                        }
                    }
                }
                mBlogsAdapter.setData(listBlogs);
            }

            @Override
            public void onFailure(Call<List<blogimages>> call, Throwable t) {
                Toast.makeText(mMainActivity, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Mapping() {
        listBlogs = new ArrayList<>();
        rcvBlogs = view.findViewById(R.id.list_updateBlog);
        listBlogsImage = new ArrayList<>();
    }

    @Override
    public void clickItem(int position) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("detail-back", 4);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", (Serializable) listBlogs.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}