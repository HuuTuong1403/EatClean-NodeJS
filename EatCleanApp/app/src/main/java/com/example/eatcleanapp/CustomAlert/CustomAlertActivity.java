package com.example.eatcleanapp.CustomAlert;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.CaseMap;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.eatcleanapp.R;

public class CustomAlertActivity {

    private Activity activity;
    private String Title;
    private String Message;
    private String Type;

    public CustomAlertActivity(Builder builder) {
        this.activity = builder.activity;
        this.Title = builder.Title;
        this.Message = builder.Message;
        this.Type = builder.Type;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialog);
        View view;
        if(Type.equals("success")){
            view = LayoutInflater.from(activity).inflate(R.layout.layout_success_dialog, (LinearLayout) activity.findViewById (R.id.layoutDialogContainer));
            builder.setView(view);
            ((TextView) view.findViewById(R.id.textTitle)).setText(Title);
            ((TextView) view.findViewById(R.id.textMessage)).setText(Message);
            ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.check_success);
        }
        else{
            view = LayoutInflater.from(activity).inflate(R.layout.layout_error_dialog, (LinearLayout) activity.findViewById (R.id.layoutDialogContainer));
            builder.setView(view);
            ((TextView) view.findViewById(R.id.textTitle)).setText(Title);
            ((TextView) view.findViewById(R.id.textMessage)).setText(Message);
            ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.cancel_error);
        }

        final AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.TOP;
        if(window != null){
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingAnim;
        }
        window.setAttributes(windowAtributes);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, 3000);
        alertDialog.show();
    }

    public static class Builder {
        private Activity activity;
        private String Title;
        private String Message;
        private String Type;

        public Builder(){ };

        public Builder(Activity activity, String title, String message, String type) {
            this.activity = activity;
            Title = title;
            Message = message;
            Type = type;
        }

        public Builder setType(String type) {
            this.Type = type;
            return this;
        }

        public Builder setActivity (Activity activity){
            this.activity = activity;
            return this;
        }

        public Builder setTitle (String title){
            this.Title = title;
            return this;
        }
        public Builder setMessage (String message){
            this.Message = message;
            return this;
        }

        public CustomAlertActivity Build (){
            return new CustomAlertActivity(this);
        }
    }
}
