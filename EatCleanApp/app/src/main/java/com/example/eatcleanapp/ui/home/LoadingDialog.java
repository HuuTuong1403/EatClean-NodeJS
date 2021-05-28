package com.example.eatcleanapp.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.eatcleanapp.R;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity myActivity){
        activity = myActivity;
    };

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_dialog_loading, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}
