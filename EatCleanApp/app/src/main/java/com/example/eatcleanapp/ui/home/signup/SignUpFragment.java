package com.example.eatcleanapp.ui.home.signup;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eatcleanapp.CustomAlert.CustomAlertActivity;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpFragment extends Fragment {

    private View view;
    private TextInputEditText edtUsername, edtEmail, edtPassword, edtPasswordAgain, edtFullName, edtPhone;
    private Button btnRegister;
    private CheckBox cb_chooseCtv;
    private SubActivity mSubActivity;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saved0InstanceState) {
        mSubActivity = (SubActivity) getActivity();
        loadingDialog = new LoadingDialog(mSubActivity);
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        View background = view.findViewById(R.id.backgroundSignUp);
        Drawable backgroundImage = background.getBackground();
        backgroundImage.setAlpha(80);
        Mapping();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));

        Animation animTranslate = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                v.startAnimation(animTranslate);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registerUser();
                    }
                }, 400);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void Mapping(){
        edtUsername         = (TextInputEditText) view.findViewById(R.id.signup_edtUsername);
        edtEmail            = (TextInputEditText) view.findViewById(R.id.signup_edtEmail);
        edtPassword         = (TextInputEditText) view.findViewById(R.id.signup_edtPassword);
        edtPasswordAgain    = (TextInputEditText) view.findViewById(R.id.signup_edtPasswordAgain);
        edtFullName         = (TextInputEditText) view.findViewById(R.id.signup_edtFullname);
        btnRegister         = (Button) view.findViewById(R.id.signup_btnRegister);
        edtPhone            = (TextInputEditText) view.findViewById(R.id.signup_edtSDT);
        cb_chooseCtv        = (CheckBox)view.findViewById(R.id.cb_chooseCtv);
    }

    private void registerUser(){
        if(edtEmail.getText().toString().isEmpty() || edtFullName.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtPasswordAgain.getText().toString().isEmpty() || edtPhone.getText().toString().isEmpty() || edtUsername.getText().toString().isEmpty()){
            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                    .setActivity(getActivity())
                    .setTitle("Thông báo")
                    .setMessage("Các trường nhập liệu không được trống")
                    .setType("error")
                    .Build();
            customAlertActivity.showDialog();
        }
        else{
            Request request = null;
            String password =  edtPassword.getText().toString().trim();
            String passwordAgain = edtPasswordAgain.getText().toString().trim();
            if (password.equals(passwordAgain)){
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("Email", edtEmail.getText().toString().trim())
                        .addFormDataPart("Password", password)
                        .addFormDataPart("FullName", edtFullName.getText().toString().trim())
                        .addFormDataPart("Username", edtUsername.getText().toString().trim())
                        .addFormDataPart("SoDienThoai", edtPhone.getText().toString().trim())
                        .build();
                if(cb_chooseCtv.isChecked()){
                    request = new Request.Builder()
                            .url("https://eat-clean-nhom04.herokuapp.com/collaborator/register-collaborator")
                            .method("POST", body)
                            .build();
                }
                else{
                    request = new Request.Builder()
                            .url("https://eat-clean-nhom04.herokuapp.com/user/register-user")
                            .method("POST", body)
                            .build();
                }
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        if(cb_chooseCtv.isChecked()){
                            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                    .setActivity(getActivity())
                                    .setTitle("Thông báo")
                                    .setMessage("Đăng ký tài khoản cộng tác viên thành công")
                                    .setType("success")
                                    .Build();
                            customAlertActivity.showDialog();
                        }
                        else{
                            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                    .setActivity(getActivity())
                                    .setTitle("Thông báo")
                                    .setMessage("Đăng ký tài khoản người dùng thành công")
                                    .setType("success")
                                    .Build();
                            customAlertActivity.showDialog();
                        }
                        edtUsername.setText("");
                        edtEmail.setText("");
                        edtPassword.setText("");
                        edtPasswordAgain.setText("");
                        edtFullName.setText("");
                        edtPhone.setText("");
                        cb_chooseCtv.setChecked(false);
                    }
                    else {
                        String jsonData = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonData);
                        String error = jsonObject.getString("error");
                        if(error.trim().equals("Tài khoản hoặc Email đã tồn tại")){
                            CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                    .setActivity(getActivity())
                                    .setTitle("Thông báo")
                                    .setMessage(error.trim())
                                    .setType("error")
                                    .Build();
                            customAlertActivity.showDialog();
                        }
                        else{
                            if(cb_chooseCtv.isChecked()){
                                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                        .setActivity(getActivity())
                                        .setTitle("Thông báo")
                                        .setMessage("Đăng ký tài khoản cộng tác viên thất bại")
                                        .setType("error")
                                        .Build();
                                customAlertActivity.showDialog();
                            }
                            else{
                                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                                        .setActivity(getActivity())
                                        .setTitle("Thông báo")
                                        .setMessage("Đăng ký tài khoản người dùng thất bại")
                                        .setType("error")
                                        .Build();
                                customAlertActivity.showDialog();
                            }
                        }
                    }
                } catch (IOException | JSONException e) {
                    CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                            .setActivity(getActivity())
                            .setTitle("Thông báo")
                            .setMessage("Đã có lỗi xảy ra")
                            .setType("error")
                            .Build();
                    customAlertActivity.showDialog();
                    e.printStackTrace();
                }
            }
            else {
                CustomAlertActivity customAlertActivity = new CustomAlertActivity.Builder()
                        .setActivity(getActivity())
                        .setTitle("Thông báo")
                        .setMessage("Mật khẩu nhập lại không chính xác")
                        .setType("error")
                        .Build();
                customAlertActivity.showDialog();
            }
        }
        loadingDialog.dismissDialog();
    }
}