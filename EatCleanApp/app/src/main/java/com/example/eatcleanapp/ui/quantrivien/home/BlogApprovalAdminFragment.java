package com.example.eatcleanapp.ui.quantrivien.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.blogimages;
import com.example.eatcleanapp.model.blogs;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.example.eatcleanapp.ui.quantrivien.AdminActivity;
import com.example.eatcleanapp.ui.quantrivien.home.adapter.ApprovalBlogAdapter;
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

public class BlogApprovalAdminFragment extends Fragment implements IClickListener {

    private View view;
    private RecyclerView rcvApprovalBlogs;
    private List<blogs> listBlogs;
    private ApprovalBlogAdapter approvalBlogAdapter;
    private AdminActivity adminActivity;
    private List<blogimages> listBlogImage;
    private BottomNavigationView bottomNavigationView;
    private LoadingDialog loadingDialog;
    private users user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adminActivity = (AdminActivity) getActivity();
        loadingDialog = new LoadingDialog(adminActivity);
        view = inflater.inflate(R.layout.fragment_blog_approval_admin, container, false);
        user = DataLocalManager.getUser();
        Mapping();
        CreateRecyclerView();
        GetData();
        rcvApprovalBlogs.setAdapter(approvalBlogAdapter);
        return view;
    }

    private void GetData() {
        loadingDialog.startLoadingDialog();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/admin/show-blog-inconfirm")
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
                setBadge();
                approvalBlogAdapter.setData(listBlogs);
            }
            else {
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(adminActivity)
                        .setTitle("Thông báo")
                        .setMessage("Không lấy được dữ liệu")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(adminActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            loadingDialog.dismissDialog();
        }
    }


    private void setBadge() {
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_blog_not_approval);
        badgeDrawable.setNumber(listBlogs.size());
        badgeDrawable.setVisible(true);
    }

    private void CreateRecyclerView() {
        approvalBlogAdapter = new ApprovalBlogAdapter(view.getContext(), adminActivity, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvApprovalBlogs.setLayoutManager(linearLayoutManager);
    }

    private void Mapping() {
        listBlogs = new ArrayList<>();
        listBlogImage = new ArrayList<>();
        rcvApprovalBlogs = view.findViewById(R.id.list_blogs_approval);
        bottomNavigationView = adminActivity.findViewById(R.id.bottom_menu_admin);
    }

    @Override
    public void clickItem(int position) {
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("detail-back", 2);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", listBlogs.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}