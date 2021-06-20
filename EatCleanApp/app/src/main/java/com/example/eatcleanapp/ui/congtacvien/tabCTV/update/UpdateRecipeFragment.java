package com.example.eatcleanapp.ui.congtacvien.tabCTV.update;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.API.APIService;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.RealPathUtil;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
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
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateRecipeFragment extends Fragment {

    private View view;
    private recipes recipe;
    private DetailActivity detailActivity;
    private ImageView imgV_updateRecipe_uploadImage;
    private EditText edt_updateRecipe_recipeTitle, edt_updateRecipe_recipeContent, edt_updateRecipe_recipeNutritional, edt_updateRecipe_recipeIngredients, edt_updateRecipe_recipeSteps, edt_updateRecipe_recipeTime;
    private Button btn_updateRecipe_sendApproval;
    private Uri mUri;
    private LoadingDialog loadingDialog;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailActivity = (DetailActivity) getActivity();
        loadingDialog = new LoadingDialog(detailActivity);
        view = inflater.inflate(R.layout.fragment_update_recipe, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        Mapping();
        setData();

        Animation animScale = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_scale);
        Handler handler = new Handler();

        imgV_updateRecipe_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && detailActivity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    openDialogChooseImage();
                }
                else
                {
                    openRequest();
                }
            }
        });

        btn_updateRecipe_sendApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                v.startAnimation(animScale);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendUpdate();
                    }
                }, 400);
            }
        });


        edt_updateRecipe_recipeSteps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                setScrollEditText(view, motionEvent);
                return false;
            }
        });
        edt_updateRecipe_recipeIngredients.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                setScrollEditText(view, motionEvent);
                return false;
            }
        });


        return view;
    }

    private void setScrollEditText(View view, MotionEvent motionEvent){
        view.getParent().requestDisallowInterceptTouchEvent(true);
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
    }

    private void sendUpdate() {
        if (edt_updateRecipe_recipeTitle.getText().toString().isEmpty() ||
                edt_updateRecipe_recipeContent.getText().toString().isEmpty() ||
                edt_updateRecipe_recipeNutritional.getText().toString().isEmpty() ||
                edt_updateRecipe_recipeIngredients.getText().toString().isEmpty() ||
                edt_updateRecipe_recipeSteps.getText().toString().isEmpty() ||
                edt_updateRecipe_recipeTime.getText().toString().isEmpty()) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(detailActivity)
                    .setTitle("Thông báo")
                    .setMessage("Các trường nhập liệu không được trống")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            loadingDialog.dismissDialog();
        }
        else{
            String recipeTitle          = edt_updateRecipe_recipeTitle.getText().toString();
            String recipeAuthor         = recipe.getRecipesAuthor();
            String recipeContent        = edt_updateRecipe_recipeContent.getText().toString();
            String recipeNutritional    = edt_updateRecipe_recipeNutritional.getText().toString();
            String recipeIngredient     = edt_updateRecipe_recipeIngredients.getText().toString();
            String recipeStep           = edt_updateRecipe_recipeSteps.getText().toString();
            String recipeTime           = edt_updateRecipe_recipeTime.getText().toString();
            String recipeStatus         = "waittingforapproval";
            updateRecipeCtv(recipe.get_id(), recipeTitle, recipeAuthor, recipeContent, recipeNutritional, recipeIngredient, recipeStep, recipeTime, recipeStatus);
        }
    }

    private void updateRecipeCtv(String id, String title, String author, String content, String nuTri, String ingredient, String step, String time, String status) {
        File file;
        String FileName = "";
        RequestBody body;
        if (mUri != null){
            String pathImage = RealPathUtil.getRealPath(view.getContext(), mUri);
            file = new File(pathImage);

            FileName = file.getName();
            MediaType mediaType = MediaType.parse("text/plain");
            body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("RecipesTitle",title)
                    .addFormDataPart("RecipesContent",content)
                    .addFormDataPart("NutritionalIngredients",nuTri)
                    .addFormDataPart("Ingredients",ingredient)
                    .addFormDataPart("Steps",step)
                    .addFormDataPart("IDRecipe",id)
                    .addFormDataPart("Time",time)
                    .addFormDataPart("Image",FileName ,
                            RequestBody.create(MEDIA_TYPE_PNG, file))
                    .build();
        }
        else{
            MediaType mediaType = MediaType.parse("text/plain");
             body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("RecipesTitle",title)
                    .addFormDataPart("RecipesContent",content)
                    .addFormDataPart("NutritionalIngredients",nuTri)
                    .addFormDataPart("Ingredients",ingredient)
                    .addFormDataPart("Steps",step)
                    .addFormDataPart("IDRecipe",id)
                     .addFormDataPart("Time",time)
                    .build();
        }
        users user = DataLocalManager.getUser();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/collaborator/update-recipe")
                .method("PUT", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(detailActivity)
                        .setTitle("Thông báo")
                        .setMessage("Chỉnh sửa công thức thành công")
                        .setType("success")
                        .Build();
                customAlertActivity.showDialog();
            }
            else {
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject error = jsonObject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(detailActivity)
                        .setTitle("Thông báo")
                        .setMessage("Chỉnh sửa công thức thất bại")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
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

    private void Mapping() {
        imgV_updateRecipe_uploadImage       = (ImageView)view.findViewById(R.id.imgV_updateRecipe_uploadImage);
        edt_updateRecipe_recipeTitle        = (EditText)view.findViewById(R.id.edt_updateRecipe_recipeTitle);
        edt_updateRecipe_recipeContent      = (EditText)view.findViewById(R.id.edt_updateRecipe_recipeContent);
        edt_updateRecipe_recipeNutritional  = (EditText)view.findViewById(R.id.edt_updateRecipe_recipeNutritional);
        edt_updateRecipe_recipeIngredients  = (EditText)view.findViewById(R.id.edt_updateRecipe_recipeIngredients);
        edt_updateRecipe_recipeSteps        = (EditText)view.findViewById(R.id.edt_updateRecipe_recipeSteps);
        edt_updateRecipe_recipeTime         = (EditText)view.findViewById(R.id.edt_updateRecipe_recipeTime);
        btn_updateRecipe_sendApproval       = (Button)view.findViewById(R.id.btn_updateRecipe_sendApproval);
    }

    private void setData(){
        recipe = detailActivity.getRecipes();
        if(recipe == null){
            return;
        }
        else{
            Glide.with(view).load(recipe.getImageMain()).placeholder(R.drawable.gray).into(imgV_updateRecipe_uploadImage);
            edt_updateRecipe_recipeTitle.setText(recipe.getRecipesTitle());
            edt_updateRecipe_recipeContent.setText(recipe.getRecipesContent());
            edt_updateRecipe_recipeNutritional.setText(recipe.getNutritionalIngredients());
            edt_updateRecipe_recipeIngredients.setText(recipe.getIngredients());
            edt_updateRecipe_recipeSteps.setText(recipe.getSteps());
            edt_updateRecipe_recipeTime.setText(recipe.getTime());
        }
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
                                    Uri uri = Uri.fromParts("package", detailActivity.getPackageName(), null);
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
                                Toast.makeText(detailActivity, "Có lỗi xảy ra!", Toast.LENGTH_LONG).show();
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
                if(resultCode == detailActivity.RESULT_OK){
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                            byteArray.length);
                    imgV_updateRecipe_uploadImage.setImageBitmap(bitmap);
                    mUri = getImageUri(view.getContext(), bmp);
                    break;
                }
            case 1:
                if(resultCode == detailActivity.RESULT_OK){
                    Uri pickImage = data.getData();
                    mUri = pickImage;
                    imgV_updateRecipe_uploadImage.setImageURI(pickImage);
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