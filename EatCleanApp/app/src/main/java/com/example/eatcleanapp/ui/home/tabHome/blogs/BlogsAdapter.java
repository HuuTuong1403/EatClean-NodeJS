package com.example.eatcleanapp.ui.home.tabHome.blogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.IClickListener;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.blogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder>{

    private Context context;
    private IClickListener iClickListener;
    private List<blogs> mListBlogs;

    public BlogsAdapter(Context context, IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }

    public void setData(List<blogs> list){
        this.mListBlogs = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_blogs, parent, false);
        return new BlogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogsViewHolder holder, int position) {
        blogs blogs = mListBlogs.get(position);
        if(blogs == null){
            return;
        }
        holder.blogs_txv_blogsTitle.setText(blogs.getBlogTitle());
        holder.blogs_txv_blogsAuthor.setText(blogs.getBlogAuthor());
        String date = FormatDate(blogs.getCreatedAt());
        holder.blogs_txv_blogsDateCreate.setText("Ngày đăng: " + date);
        Glide.with(context).load(blogs.getImageMain()).placeholder(R.drawable.gray).into(holder.blogs_image);
    }
    
    private String FormatDate(String date){
        String newDate = null;
        try {
            Date Date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            newDate = new SimpleDateFormat("dd/MM/yyyy").format(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    @Override
    public int getItemCount() {
        if(mListBlogs != null){
            return mListBlogs.size();
        }
        return 0;
    }

    public class BlogsViewHolder extends RecyclerView.ViewHolder{

        private ImageView blogs_image;
        private TextView blogs_txv_blogsTitle, blogs_txv_blogsAuthor, blogs_txv_blogsDateCreate;


        public BlogsViewHolder(@NonNull View itemView) {
            super(itemView);

            blogs_image = (ImageView)itemView.findViewById(R.id.blogs_image);
            blogs_txv_blogsAuthor = (TextView)itemView.findViewById(R.id.blogs_txv_blogsAuthor);
            blogs_txv_blogsTitle = (TextView)itemView.findViewById(R.id.blogs_txv_blogsTitle);
            blogs_txv_blogsDateCreate = (TextView)itemView.findViewById(R.id.blogs_txv_blogsDateCreate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickListener.clickItem(getAdapterPosition());
                }
            });
        }

    }
}
