package com.example.eatcleanapp.ui.home.detail.blog;

import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.blogs;
import com.example.eatcleanapp.ui.home.detail.DetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailBlogFragment extends Fragment {

    private View view;
    private ImageView detail_blogs_imageBlog;
    private TextView detail_blog_titleBlog, detail_blog_timeBlog, detail_blog_authorBlog, detail_blog_contentBlog;
    private DetailActivity mDetailActivity;
    private blogs blog_detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_blog, container, false);
        mDetailActivity = (DetailActivity) getActivity();
        Mapping();

        return view;
    }

    private void Mapping(){
        detail_blogs_imageBlog = (ImageView)view.findViewById(R.id.detail_blogs_imageBlog);
        detail_blog_titleBlog = (TextView)view.findViewById(R.id.detail_blogs_titleBlog);
        detail_blog_timeBlog = (TextView)view.findViewById(R.id.detail_blogs_timeBlog);
        detail_blog_authorBlog = (TextView)view.findViewById(R.id.detail_blogs_authorBlog);
        detail_blog_contentBlog = (TextView)view.findViewById(R.id.detail_blogs_contentBlog);
        blog_detail = mDetailActivity.getBlogs();
        if(blog_detail != null){
            String date = FormatDate(blog_detail.getCreatedAt());
            Glide.with(view).load(blog_detail.getImageMain()).placeholder(R.drawable.gray).into(detail_blogs_imageBlog);
            detail_blog_titleBlog.setText(blog_detail.getBlogTitle());
            detail_blog_timeBlog.setText("Ngày đăng: " + date);
            detail_blog_authorBlog.setText("Tác giả: " + blog_detail.getBlogAuthor());
            detail_blog_contentBlog.setText(blog_detail.getBlogContent());
        }
        else{
            return;
        }

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
}