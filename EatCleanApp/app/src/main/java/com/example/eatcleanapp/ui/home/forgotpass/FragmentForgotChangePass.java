package com.example.eatcleanapp.ui.home.forgotpass;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.SubActivity;
import com.example.eatcleanapp.ui.home.LoadingDialog;
import com.example.eatcleanapp.ui.home.profile.ProfileEditFragment;

public class FragmentForgotChangePass extends Fragment {

    private View view;
    private SubActivity mSubActivity;
    private EditText forgotChangePass_edtNewPass, forgotChangePass_edtNewPassAgain;
    private Button forgotChangePass_btnChangePass;
    private LoadingDialog loadingDialog;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubActivity = (SubActivity) getActivity();
        loadingDialog = new LoadingDialog(mSubActivity);
        view = inflater.inflate(R.layout.fragment_forgot_change_pass, container, false);
        Mapping();

        Animation animButton = mSubActivity.getAnimButton(view);
        Handler handler = new Handler();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FragmentForgotChangePass.this).navigate(R.id.action_forgotChange_pass_fragment_to_forgot_pass_fragment);
            }
        });

        return view;
    }

    private void Mapping(){
        toolbar = (Toolbar)mSubActivity.findViewById(R.id.toolbar);
        mSubActivity.setText("Đổi mật khẩu");
        mSubActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back24);
        forgotChangePass_edtNewPass         = (EditText)view.findViewById(R.id.forgotChangePass_edtNewPass);
        forgotChangePass_edtNewPassAgain    = (EditText)view.findViewById(R.id.forgotChangePass_edtNewPassAgain);
        forgotChangePass_btnChangePass      = (Button)view.findViewById(R.id.forgotChangePass_btnChangePass);
    }
}