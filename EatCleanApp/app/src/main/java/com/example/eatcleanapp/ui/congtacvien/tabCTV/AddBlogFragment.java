package com.example.eatcleanapp.ui.congtacvien.tabCTV;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.RealPathUtil;
import com.example.eatcleanapp.model.blogs;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class AddBlogFragment extends Fragment {

    private View view;
    private ImageView imgV_addBlog_uploadImage;
    private EditText edt_addBlog_blogTitle, edt_addBlog_blogContent;
    private Button btn_addBlog_sendApproval;
    private MainActivity mMainActivity;
    private users user;
    private Uri mUri;
    private LoadingDialog loadingDialog;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        loadingDialog = new LoadingDialog(mMainActivity);
        view = inflater.inflate(R.layout.fragment_add_blog, container, false);
        Mapping();

        imgV_addBlog_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMainActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && mMainActivity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    openDialogChooseImage();
                }
                else
                {
                    openRequest();
                }
            }
        });

        Animation animScale = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_scale);
        Handler handler = new Handler();

        btn_addBlog_sendApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                v.startAnimation(animScale);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendApproval();
                    }
                }, 400);
            }
        });

        return view;
    }

    private void sendApproval() {
        if(edt_addBlog_blogTitle.getText().toString().isEmpty() || edt_addBlog_blogContent.getText().toString().isEmpty()){
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mMainActivity)
                    .setTitle("Thông báo")
                    .setMessage("Các trường nhập liệu không được trống")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            loadingDialog.dismissDialog();
        }
        else{
            if(mUri == null) {
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mMainActivity)
                        .setTitle("Thông báo")
                        .setMessage("Vui lòng tải hỉnh ảnh món ăn")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
                loadingDialog.dismissDialog();
            }
            else{
                String BlogTitle    = edt_addBlog_blogTitle.getText().toString();
                String BlogContent  = edt_addBlog_blogContent.getText().toString();
                addBlogCtv(BlogTitle, BlogContent);
            }
        }
    }

    private void addBlogCtv(String title, String content){
        String pathImage = RealPathUtil.getRealPath(view.getContext(), mUri);
        File file = new File(pathImage);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("BlogTitle",title)
                .addFormDataPart("BlogContent", content)
                .addFormDataPart("Image", file.getName(),
                RequestBody.create(MEDIA_TYPE_PNG, file))
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/collaborator/create-blog")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                edt_addBlog_blogTitle.setText("");
                edt_addBlog_blogContent.setText("");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mMainActivity)
                        .setTitle("Thông báo")
                        .setMessage("Thêm blog thành công! Vui lòng chờ quản trị viên phê duyệt")
                        .setType("success")
                        .Build();
                customAlertActivity.showDialog();
                edt_addBlog_blogTitle.setText("");
                edt_addBlog_blogContent.setText("");
                imgV_addBlog_uploadImage.setImageResource(R.drawable.up);
            }
            else {
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject error = jsonObject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mMainActivity)
                        .setTitle("Thông báo")
                        .setMessage("Thêm công thức thất bại")
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
            e.printStackTrace();
            loadingDialog.dismissDialog();
        }
    }

    private void Mapping() {
        imgV_addBlog_uploadImage    = (ImageView)view.findViewById(R.id.imgV_addBlog_uploadImage);
        edt_addBlog_blogTitle       = (EditText)view.findViewById(R.id.edt_addBlog_blogTitle);
        edt_addBlog_blogContent     = (EditText)view.findViewById(R.id.edt_addBlog_blogContent);
        btn_addBlog_sendApproval    = (Button)view.findViewById(R.id.btn_addBlog_sendApproval);
        user                        = DataLocalManager.getUser();
    }

    private Dialog createDialog(int layout){
        Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if(window == null){
            return null;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAtributes);
        return dialog;
    }

    private void openRequest(){
        Dialog dialog = createDialog(R.layout.layout_dialog_request);
        if(dialog == null)
            return;
        Button btnAccept = (Button)dialog.findViewById(R.id.btn_accept_request);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Dexter.withContext(view.getContext())
                        .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .withListener(new MultiplePermissionsListener(){
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if(multiplePermissionsReport.areAllPermissionsGranted()){
                                    openDialogChooseImage();
                                }
                                if(multiplePermissionsReport.isAnyPermissionPermanentlyDenied()){
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", mMainActivity.getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        })
                        .withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError dexterError) {
                                Toast.makeText(mMainActivity, "Có lỗi xảy ra!", Toast.LENGTH_LONG).show();
                            }
                        }).onSameThread().check();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    private void openDialogChooseImage(){
        Dialog dialog = createDialog(R.layout.layout_choose_image);
        if(dialog == null)
            return;

        Button btn_chooseImage_Camera = (Button)dialog.findViewById(R.id.btn_chooseImage_Camera);
        Button btn_chooseImage_Media = (Button)dialog.findViewById(R.id.btn_chooseImage_Media);
        Button btn_chooseImage_Cancel = (Button)dialog.findViewById(R.id.btn_chooseImage_Cancel);

        btn_chooseImage_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, 0);
            }
        });

        btn_chooseImage_Media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        btn_chooseImage_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 0:
                if(resultCode == mMainActivity.RESULT_OK){
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                            byteArray.length);
                    imgV_addBlog_uploadImage.setImageBitmap(bitmap);
                    mUri = getImageUri(view.getContext(), bmp);
                    break;
                }
            case 1:
                if(resultCode == mMainActivity.RESULT_OK){
                    Uri pickImage = data.getData();
                    mUri = pickImage;
                    imgV_addBlog_uploadImage.setImageURI(pickImage);
                    break;
                }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, String.valueOf(System.currentTimeMillis()), null);
        return Uri.parse(path);
    }
}