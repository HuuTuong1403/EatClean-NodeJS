package com.example.eatcleanapp.ui.home.profile;

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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.MainActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.RealPathUtil;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.model.users;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;
import com.google.gson.Gson;
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

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ProfileFragment extends Fragment {

    private View view;
    private SubActivity mSubActivity;
    private Button btn_profile_edit, btn_profile_changePass, btn_add_avatar, btn_save_avatar;
    private Toolbar toolbar;
    private CircleImageView imageView_avatar_user;
    private users user;
    private TextView txv_profile_userName, txv_profile_email, txv_profile_fullName, txv_profile_title_fullName, txv_profile_soDienThoai;
    private Uri mUri;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
    private LoadingDialog loadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubActivity = (SubActivity) getActivity();
        mSubActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);
        loadingDialog = new LoadingDialog(mSubActivity);
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Mapping();
        user = DataLocalManager.getUser();

        txv_profile_userName.setText("T??n ????ng nh???p: " + user.getUsername());
        txv_profile_email.setText("Email: " + user.getEmail());
        txv_profile_fullName.setText("H??? v?? t??n: " + user.getFullName());
        txv_profile_soDienThoai.setText("S??? ??i???n tho???i: " + user.getSoDienThoai());
        txv_profile_title_fullName.setText(user.getFullName());
        Glide.with(view).load(user.getImage()).placeholder(R.drawable.gray).into(imageView_avatar_user);

        Animation animButton = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        btn_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profile_fragment_to_profile_edit_fragment);
                    }
                }, 400);
            }
        });

        btn_profile_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profile_fragment_to_profile_changePass_fragment);
                    }
                }, 400);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mSubActivity, MainActivity.class);
                startActivity(intent);
                mSubActivity.finish();
            }
        });

        btn_add_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSubActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && mSubActivity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                            openDialogChooseImage();
                        }
                        else
                        {
                            openRequest();
                        }
                    }
                }, 400);
            }
        });

        btn_save_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                v.startAnimation(animButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mUri != null){
                            UploadAvatar();
                        }
                        else{
                            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                    .setActivity(mSubActivity)
                                    .setTitle("Th??ng b??o")
                                    .setMessage("B???n ch??a ch???n h??nh ???nh!")
                                    .setType("error")
                                    .Build();
                            customAlertActivity.showDialog();
                            loadingDialog.dismissDialog();
                        }
                    }
                },400);
            }
        });
        return view;
    }

    private void GetInformation(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/information")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            if (response.isSuccessful()){
                JSONObject data = Jobject.getJSONObject("data");
                Gson g = new Gson();
                user = g.fromJson(String.valueOf(data), users.class);
                DataLocalManager.setUser(user);
            }
            else{
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mSubActivity)
                        .setTitle("Th??ng b??o")
                        .setMessage("Ch???nh s???a t??i kho???n th???t b???i")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mSubActivity)
                    .setTitle("Th??ng b??o")
                    .setMessage("???? x???y ra l???i!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            e.printStackTrace();
        }
    }


    private void UploadAvatar(){
        String strRealPath = RealPathUtil.getRealPath(view.getContext(), mUri);
        File file = new File(strRealPath);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("Image", file.getName(),
                        RequestBody.create(MEDIA_TYPE_PNG, file))
                .build();
        Request request = new Request.Builder()
                .url("https://eat-clean-nhom04.herokuapp.com/me/edit-avatar")
                .method("PUT", body)
                .addHeader("Authorization", "Bearer " + user.getToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);
            if(response.isSuccessful()){
                JSONObject data = jsonObject.getJSONObject("data");
                Gson g = new Gson();
                users usertemp = g.fromJson(String.valueOf(data), users.class);
                user.setImage(usertemp.getImage());
                DataLocalManager.setUser(user);
                //GetInformation();
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mSubActivity)
                        .setTitle("Th??ng b??o")
                        .setMessage("Th??m h??nh ???nh avatar th??nh c??ng")
                        .setType("success")
                        .Build();
                customAlertActivity.showDialog();
            }
            else {
                JSONObject error = jsonObject.getJSONObject("error");
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(mSubActivity)
                        .setTitle("Th??ng b??o")
                        .setMessage("Th??m h??nh ???nh avatar th???t b???i")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
            loadingDialog.dismissDialog();
        } catch (IOException | JSONException e) {
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(mSubActivity)
                    .setTitle("Th??ng b??o")
                    .setMessage("???? x???y ra l???i!!!")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
            loadingDialog.dismissDialog();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 0:
                if(resultCode == mSubActivity.RESULT_OK){
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                            byteArray.length);
                    imageView_avatar_user.setImageBitmap(bitmap);
                    mUri = getImageUri(view.getContext(), bmp);
                    break;
                }
            case 1:
                if(resultCode == mSubActivity.RESULT_OK){
                    Uri pickImage = data.getData();
                    mUri = pickImage;
                    imageView_avatar_user.setImageURI(pickImage);
                    break;
                }
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
                                    Uri uri = Uri.fromParts("package", mSubActivity.getPackageName(), null);
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
                                Toast.makeText(mSubActivity, "C?? l???i x???y ra!", Toast.LENGTH_LONG).show();
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, String.valueOf(System.currentTimeMillis()), null);
        return Uri.parse(path);
    }

    private void Mapping() {
        mSubActivity.setText("Th??ng tin t??i kho???n");
        btn_profile_edit            = (Button)view.findViewById(R.id.btn_profile_edit);
        btn_profile_changePass      = (Button)view.findViewById(R.id.btn_profile_changePass);
        btn_add_avatar              = (Button)view.findViewById(R.id.btn_add_avatar);
        btn_save_avatar             = (Button)view.findViewById(R.id.btn_save_avatar);
        toolbar                     = (Toolbar)mSubActivity.findViewById(R.id.toolbar);
        imageView_avatar_user       = (CircleImageView)view.findViewById(R.id.imageView_avatar_user);
        txv_profile_userName        = (TextView)view.findViewById(R.id.txv_profile_userName);
        txv_profile_email           = (TextView)view.findViewById(R.id.txv_profile_email);
        txv_profile_fullName        = (TextView)view.findViewById(R.id.txv_profile_fullName);
        txv_profile_title_fullName  = (TextView)view.findViewById(R.id.txv_profile_title_fullName);
        txv_profile_soDienThoai = (TextView) view.findViewById(R.id.txv_profile_SDT);
    }


}