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
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.home.tabHome.blogs.BlogsAdapter;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

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
        users user = DataLocalManager.getUser();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/collaborator/show-my-blogs")
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
                    blogs blog = new blogs(
                            object.getString("_id"),
                            object.getString("BlogTitle"),
                            object.getString("BlogAuthor"),
                            object.getString("BlogContent"),
                            object.getString("IDAuthor"),
                            object.getString("ImageMain"),
                            object.getString("Status"),
                            object.getString("createdAt")
                    );
                    String time = blog.getCreatedAt().toString();
                    int endIndex = time.indexOf("T");
                    blog.setCreatedAt(time.substring(0,endIndex));
                    listBlogs.add(blog);
                }
                mBlogsAdapter.setData(listBlogs);
            }
            else {
                JSONObject error = Jobject.getJSONObject("error");
                Toast.makeText(view.getContext(),  error.toString() , Toast.LENGTH_LONG).show();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        mBlogsAdapter.setData(listBlogs);
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