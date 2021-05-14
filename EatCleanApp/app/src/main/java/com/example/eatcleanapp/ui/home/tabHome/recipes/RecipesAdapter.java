package com.example.eatcleanapp.ui.home.tabHome.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatcleanapp.R;
import com.example.eatcleanapp.model.recipes;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>{

    private final Context context;
    private List<recipes> mListRecipes;
    private final ItemClickListener mItemClickListener;

    public RecipesAdapter(Context context, ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        this.context = context;
    }

    public void setData(List<recipes> list){
        this.mListRecipes = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recipes, parent, false);
        return new RecipesViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        recipes recipes = mListRecipes.get(position);
        if(recipes == null){
            return;
        }
        //holder.recipes_image.setImageResource(recipes.get());
        holder.recipes_name.setText(recipes.getRecipesTitle());
        String s = "Công thức tạo bởi <b>" + recipes.getRecipesAuthor() + "</b>";
        holder.recipes_author.setText(HtmlCompat.fromHtml(s, HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public int getItemCount() {
        if(mListRecipes != null){
            return mListRecipes.size();
        }
        return 0;
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView recipes_image;
        private final TextView recipes_name;
        private final TextView recipes_author;
        private final ItemClickListener itemClickListener;

        public RecipesViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            recipes_image = (ImageView)itemView.findViewById(R.id.recipes_image);
            recipes_name = (TextView)itemView.findViewById(R.id.recipes_name);
            recipes_author = (TextView)itemView.findViewById(R.id.recipes_author);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }
}
