package com.example.eatcleanapp.ui.home.detail.recipes.tabdetail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.model.comments;
import com.example.eatcleanapp.model.favoriterecipes;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.home.signin.SignInFragment;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;


public class TabDetailCommentFragment extends Fragment implements IClickListener {

    private View view;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private List<comments> listComments;
    private DetailActivity detailActivity;
    private users user;
    private EditText edt_comment_addComment;
    private ImageButton btn_comment_send;
    private recipes recipe;
    private String IDComment;
    private ImageView imgV_comment_imageUser;
    private LoadingDialog loadingDialog;
    private Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailActivity  = (DetailActivity) getActivity();
        loadingDialog = new LoadingDialog(detailActivity);
        view = inflater.inflate(R.layout.fragment_tab_detail_comment, container, false);
        Mapping();

        Animation animScale = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_scale);
        handler = new Handler();

        CreateRecyclerView();

        rcvComment.setAdapter(commentAdapter);


        btn_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                v.startAnimation(animScale);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(edt_comment_addComment.getText().toString().isEmpty()){
                            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                    .setActivity(detailActivity)
                                    .setTitle("Thông báo")
                                    .setMessage("Trường nhập không được trống")
                                    .setType("error")
                                    .Build();
                            customAlertActivity.showDialog();
                            loadingDialog.dismissDialog();
                        }
                        else{
                            String IDRecipe = recipe.get_id();
                            String Comment = edt_comment_addComment.getText().toString();
                            sendAddComment(IDRecipe, Comment);
                        }
                    }
                }, 400);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        loadingDialog.startLoadingDialog();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetData();
            }
        }, 400);
        super.onStart();
    }

    private void sendAddComment(String IDRecipe, String Comment) {
        users user = DataLocalManager.getUser();
        edt_comment_addComment.setText("");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonSignIn = new JSONObject();
        try {
            jsonSignIn.put("IDRecipe", IDRecipe);
            jsonSignIn.put("Comment", Comment);
        } catch (JSONException e) {
            Toast.makeText(view.getContext(),  e.toString() , Toast.LENGTH_LONG).show();
        }
        RequestBody body = RequestBody.create(mediaType, jsonSignIn.toString());
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/create-comment")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + user.getToken() )
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
                edt_comment_addComment.setText("");
                GetData();
            }
            else {
                JSONObject error = Jobject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(detailActivity)
                        .setTitle("Thông báo")
                        .setMessage("Thêm comment thất bại")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(detailActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            e.printStackTrace();
            loadingDialog.dismissDialog();
        }
    }

    private void GetData() {
        listComments.clear();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/show-comment-recipe?IDRecipe=" + recipe.get_id())
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
                JSONArray myResponse = Jobject.getJSONArray("data");
                for (int i = 0; i < myResponse.length();i ++){
                    JSONObject object = myResponse.getJSONObject(i);
                    comments comment = new comments(
                            object.getString("_id"),
                            object.getString("IDRecipe"),
                            object.getString("Comment"),
                            object.getString("Username"),
                            object.getString("Image"),
                            object.getString("IDUser")
                    );
                    listComments.add(comment);
                }
                commentAdapter.setData(listComments);
            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(detailActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            e.printStackTrace();
            loadingDialog.dismissDialog();
        }
    }

    private void CreateRecyclerView() {
        commentAdapter = new CommentAdapter(getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvComment.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Mapping() {

        edt_comment_addComment  = (EditText)view.findViewById(R.id.edt_comment_addComment);
        btn_comment_send        = (ImageButton)view.findViewById(R.id.btn_comment_send);
        imgV_comment_imageUser  = (ImageView)view.findViewById(R.id.imgV_comment_imageUser);
        listComments            = new ArrayList<>();
        rcvComment              = view.findViewById(R.id.list_comments);

        recipe = detailActivity.getRecipes();
        if(recipe == null){
            return;
        }
        user = DataLocalManager.getUser();
        if(user == null){
            imgV_comment_imageUser.setImageResource(R.drawable.gray);
            edt_comment_addComment.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("call-back", 1);
                    startActivity(intent);
                    return false;
                }
            });
            btn_comment_send.setVisibility(View.INVISIBLE);
        }
        else
        {
            Glide.with(view).load(user.getImage()).placeholder(R.drawable.gray).into(imgV_comment_imageUser);
        }
    }

    @Override
    public void clickItem(int position) {
        if(user == null){
            Toast.makeText(detailActivity, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("call-back", 1);
            startActivity(intent);
        }
        else{
            if(listComments.get(position).getIDUser().equals(user.get_id())){
                Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_request_favorite_recipes);
                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowAtributes = window.getAttributes();
                windowAtributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAtributes);

                Button btn_accept = (Button)dialog.findViewById(R.id.dialog_request_favorite_recipes_btn_accept);
                Button btn_cancel = (Button)dialog.findViewById(R.id.dialog_request_favorite_recipes_btn_cancel);
                TextView txv_title = (TextView)dialog.findViewById(R.id.dialog_request_favorite_recipes_txv_title);
                txv_title.setText("Bạn có muốn xóa comment này không?");
                Animation animTranslate = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_scale);
                btn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialog.startLoadingDialog();
                        v.startAnimation(animTranslate);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                deleteComment(listComments.get(position).get_id());
                                dialog.dismiss();
                            }
                        }, 400);
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(animTranslate);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 400);
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
            else{
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(detailActivity)
                        .setTitle("Thông báo")
                        .setMessage("Không được xóa comment của người khác")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();            }
        }
    }

    private void deleteComment(String IDComment){
        users user = DataLocalManager.getUser();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/delete-comment?id=" + IDComment)
                .method("DELETE", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
                GetData();
            }
            else {
                JSONObject error = Jobject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(detailActivity)
                        .setTitle("Thông báo")
                        .setMessage("Xóa comment thất bại")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(detailActivity)
                    .setTitle("Thông báo")
                    .setMessage("Đã xảy ra lỗi!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            loadingDialog.dismissDialog();
        }
    }
}