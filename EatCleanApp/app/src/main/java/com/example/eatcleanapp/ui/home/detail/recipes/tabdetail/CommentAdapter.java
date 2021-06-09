package com.example.eatcleanapp.ui.home.detail.recipes.tabdetail;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.comments;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;
import com.example.eatcleanapp.ui.nguoidung.data_local.DataLocalManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    private List<comments> listComments;
    private IClickListener iClickListener;

    public CommentAdapter(Context context, IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }

    public void setData(List<comments> list){
        this.listComments = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        comments comments = listComments.get(position);
        if(comments == null){
            return;
        }
        Glide.with(context).load(comments.getImage()).placeholder(R.drawable.gray).into(holder.imgV_listComment_imageUser);
        holder.txv_listComment_userName.setText(comments.getUsername());
        holder.txv_listComment_content.setText(comments.getComment());
    }

    @Override
    public int getItemCount() {
        if(listComments != null){
            return listComments.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgV_listComment_imageUser;
        private TextView txv_listComment_userName, txv_listComment_content;

        public CommentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgV_listComment_imageUser  = (ImageView)itemView.findViewById(R.id.imgV_listComment_imageUser);
            txv_listComment_userName    = (TextView)itemView.findViewById(R.id.txv_listComment_userName);
            txv_listComment_content     = (TextView)itemView.findViewById(R.id.txv_listComment_content);


            Animation animButton = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.anim_scale);
            Handler handler = new Handler();

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    v.startAnimation(animButton);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iClickListener.clickItem(getAdapterPosition());
                        }
                    }, 500);

                    return false;
                }
            });
        }
    }
}
