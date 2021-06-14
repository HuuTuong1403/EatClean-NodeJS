package com.example.eatcleanapp.ui.quantrivien.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.blogs;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.example.eatcleanapp.ui.quantrivien.AdminActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ApprovalBlogAdapter extends RecyclerView.Adapter<ApprovalBlogAdapter.ApprovalBlogViewHolder>{

    private Context context;
    private AdminActivity adminActivity;
    private IClickListener iClickListener;
    private List<blogs> listBlogs;
    private BottomNavigationView bottomNavigationView;
    private users user  = DataLocalManager.getUser();
    public ApprovalBlogAdapter(Context context, AdminActivity adminActivity, IClickListener iClickListener) {
        this.context = context;
        this.adminActivity = adminActivity;
        this.iClickListener = iClickListener;
    }

    public void setData(List<blogs> list){
        this.listBlogs = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApprovalBlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_approval_blog, parent, false);
        return new ApprovalBlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalBlogViewHolder holder, int position) {
        blogs blog = listBlogs.get(position);
        if(blog == null){
            return;
        }
        Glide.with(context).load(blog.getImageMain()).placeholder(R.drawable.gray).into(holder.imgV_approvalBlog);
        holder.txv_approvalBlog_Title.setText(blog.getBlogTitle());
        holder.txv_approvalBlog_Author.setText(blog.getBlogAuthor());
        String date = FormatDate(blog.getCreatedAt());
        holder.txv_approvalBlog_Time.setText("Ngày đăng: " + date);

        bottomNavigationView = adminActivity.findViewById(R.id.bottom_menu_admin);

        holder.btn_approvalBlog_Approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approvalBlog(listBlogs.get(position).get_id(), position);
                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_blog_not_approval);
                badgeDrawable.setNumber(listBlogs.size());
                badgeDrawable.setVisible(true);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });

        holder.btn_approvalBlog_Deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denyBlog(listBlogs.get(position).get_id(), position);
                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_blog_not_approval);
                badgeDrawable.setNumber(listBlogs.size());
                badgeDrawable.setVisible(true);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listBlogs != null){
            return listBlogs.size();
        }
        return 0;
    }

    private void denyBlog(String IDBlog, int position){
//        APIService.apiService.denyBlog(IDBlog).enqueue(new Callback<blogs>() {
//            @Override
//            public void onResponse(Call<blogs> call, Response<blogs> response) {
//                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
//                        .setActivity(adminActivity)
//                        .setTitle("Thông báo")
//                        .setMessage("Từ chối phê duyệt blog thành công")
//                        .setType("success")
//                        .Build();
//                customAlertActivity.showDialog();
//            }
//
//            @Override
//            public void onFailure(Call<blogs> call, Throwable t) {
//                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
//                        .setActivity(adminActivity)
//                        .setTitle("Thông báo")
//                        .setMessage("Từ chối phê duyệt blog thất bại")
//                        .setType("success")
//                        .Build();
//                customAlertActivity.showDialog();
//            }
//        });
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/admin/delete-blog?id=" + IDBlog)
                .method("PUT", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                listBlogs.remove(listBlogs.get(position));
                Toast.makeText(context,  "Từ chối phê duyệt blog thành công", Toast.LENGTH_LONG).show();
            }
            else {
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject error = jsonObject.getJSONObject("error");
                Toast.makeText(context,  error.toString() , Toast.LENGTH_LONG).show();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void approvalBlog(String IDBlog, int position){
//        APIService.apiService.approveBlog(IDBlog).enqueue(new Callback<blogs>() {
//            @Override
//            public void onResponse(Call<blogs> call, Response<blogs> response) {
//                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
//                        .setActivity(adminActivity)
//                        .setTitle("Thông báo")
//                        .setMessage("Phê duyệt blog thành công")
//                        .setType("success")
//                        .Build();
//                customAlertActivity.showDialog();
//            }
//
//            @Override
//            public void onFailure(Call<blogs> call, Throwable t) {
//                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
//                        .setActivity(adminActivity)
//                        .setTitle("Thông báo")
//                        .setMessage("Phê duyệt blog thất bại")
//                        .setType("error")
//                        .Build();
//                customAlertActivity.showDialog();
//            }
//        });
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/admin/confirm-blog?id=" + IDBlog)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                listBlogs.remove(listBlogs.get(position));
                Toast.makeText(context,  "Phê duyệt blog thành công", Toast.LENGTH_LONG).show();
            }
            else {
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject error = jsonObject.getJSONObject("error");
                Toast.makeText(context,  error.toString() , Toast.LENGTH_LONG).show();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private String FormatDate(String date){
        String newDate = null;
        try {
            Date Date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            newDate = new SimpleDateFormat("dd/MM/yyyy").format(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public class ApprovalBlogViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgV_approvalBlog;
        private TextView txv_approvalBlog_Title, txv_approvalBlog_Author, txv_approvalBlog_Time;
        private Button btn_approvalBlog_Deny, btn_approvalBlog_Approval;

        public ApprovalBlogViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgV_approvalBlog = (ImageView)itemView.findViewById(R.id.imgV_approvalBlog);
            txv_approvalBlog_Title = (TextView)itemView.findViewById(R.id.txv_approvalBlog_Title);
            txv_approvalBlog_Author = (TextView)itemView.findViewById(R.id.txv_approvalBlog_Author);
            txv_approvalBlog_Time = (TextView)itemView.findViewById(R.id.txv_approvalBlog_Time);
            btn_approvalBlog_Deny = (Button)itemView.findViewById(R.id.btn_approvalBlog_Deny);
            btn_approvalBlog_Approval = (Button)itemView.findViewById(R.id.btn_approvalBlog_Approval);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickListener.clickItem(getAdapterPosition());
                }
            });

        }
    }

}
