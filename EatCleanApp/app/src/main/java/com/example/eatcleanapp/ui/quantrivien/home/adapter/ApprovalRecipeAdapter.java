package com.example.eatcleanapp.ui.quantrivien.home.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.example.eatcleanapp.ui.quantrivien.AdminActivity;
import com.example.eatcleanapp.ui.quantrivien.home.RecipesApprovalAdminFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ApprovalRecipeAdapter extends RecyclerView.Adapter<ApprovalRecipeAdapter.ApprovalRecipeViewHolder>{

    private Context context;
    private IClickListener iClickListener;
    private List<recipes> listRecipes;
    private AdminActivity adminActivity;
    private BottomNavigationView bottomNavigationView;
    private users user  = DataLocalManager.getUser();
    private LoadingDialog loadingDialog;

    public ApprovalRecipeAdapter(Context context, IClickListener iClickListener, AdminActivity adminActivity) {
        this.context = context;
        this.iClickListener = iClickListener;
        this.adminActivity = adminActivity;
    }

    public void setData(List<recipes> list){
        this.listRecipes = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApprovalRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_approval_recipes, parent, false);
        return new ApprovalRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalRecipeViewHolder holder, int position) {
        recipes recipe = listRecipes.get(position);
        if(recipe == null){
            return;
        }
        Glide.with(context).load(recipe.getImageMain()).placeholder(R.drawable.gray).into(holder.imgV_approvalRecipe);
        holder.txv_approvalRecipe_Title.setText(recipe.getRecipesTitle());
        holder.txv_approvalRecipe_Author.setText(recipe.getRecipesAuthor());
    }

    @Override
    public int getItemCount() {
        if(listRecipes != null){
            return listRecipes.size();
        }
        return 0;
    }

    public class ApprovalRecipeViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imgV_approvalRecipe;
        private final TextView txv_approvalRecipe_Title, txv_approvalRecipe_Author;
        private final Button btn_approvalRecipe_Deny, btn_approvalRecipe_Approval;

        public ApprovalRecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            imgV_approvalRecipe = (ImageView)itemView.findViewById(R.id.imgV_approvalRecipe);
            txv_approvalRecipe_Title = (TextView)itemView.findViewById(R.id.txv_approvalRecipe_Title);
            txv_approvalRecipe_Author = (TextView)itemView.findViewById(R.id.txv_approvalRecipe_Author);
            btn_approvalRecipe_Deny = (Button)itemView.findViewById(R.id.btn_approvalRecipe_Deny);
            btn_approvalRecipe_Approval = (Button)itemView.findViewById(R.id.btn_approvalRecipe_Approval);

            Animation animScale = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.anim_scale);
            Handler handler = new Handler();

            loadingDialog = new LoadingDialog(adminActivity);
            bottomNavigationView = adminActivity.findViewById(R.id.bottom_menu_admin);

            btn_approvalRecipe_Approval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.startLoadingDialog();
                    v.startAnimation(animScale);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            approvalRecipe(listRecipes.get(getAdapterPosition()).get_id(), getAdapterPosition());
                        }
                    }, 400);
                }
            });

            btn_approvalRecipe_Deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.startLoadingDialog();
                    v.startAnimation(animScale);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deny(listRecipes.get(getAdapterPosition()).get_id(), getAdapterPosition());
                        }
                    }, 400);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickListener.clickItem(getAdapterPosition());
                }
            });

        }
    }

    private void approvalRecipe(String IDRecipes, int position){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/admin/confirm-recipe?id=" + IDRecipes)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                listRecipes.remove(listRecipes.get(position));
                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_recipes24_not_approval);
                badgeDrawable.setNumber(listRecipes.size());
                badgeDrawable.setVisible(true);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(adminActivity)
                        .setTitle("Thông báo")
                        .setMessage("Phê duyệt công thức thành công")
                        .setType("success")
                        .Build();
                customAlertActivity.showDialog();
            }
            else {
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject error = jsonObject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(adminActivity)
                        .setTitle("Thông báo")
                        .setMessage("Phê duyệt công thức thất bại")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(adminActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            e.printStackTrace();
            loadingDialog.dismissDialog();
        }
    }

    private void deny(String IDRecipes, int position){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/admin/delete-recipe?id=" + IDRecipes)
                .method("PUT", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                listRecipes.remove(listRecipes.get(position));
                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_recipes24_not_approval);
                badgeDrawable.setNumber(listRecipes.size());
                badgeDrawable.setVisible(true);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(adminActivity)
                        .setTitle("Thông báo")
                        .setMessage("Từ chối phê duyệt công thức thành công")
                        .setType("success")
                        .Build();
                customAlertActivity.showDialog();
            }
            else {
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject error = jsonObject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(adminActivity)
                        .setTitle("Thông báo")
                        .setMessage("Từ chối phê duyệt công thức thất bại")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(adminActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            e.printStackTrace();
            loadingDialog.dismissDialog();
        }
    }
}
