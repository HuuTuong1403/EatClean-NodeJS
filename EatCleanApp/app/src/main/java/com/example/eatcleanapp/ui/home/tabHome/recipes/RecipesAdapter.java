package com.example.eatcleanapp.ui.home.tabHome.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatcleanapp.R;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>{

    private Context context;
    private List<Recipes> mListRecipes;

    public RecipesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Recipes> list){
        this.mListRecipes = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recipes, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        Recipes recipes = mListRecipes.get(position);
        if(recipes == null){
            return;
        }
        holder.recipes_image.setImageResource(recipes.getRecipesImage());
        holder.recipes_name.setText(recipes.getRecipesName());
    }

    @Override
    public int getItemCount() {
        if(mListRecipes != null){
            return mListRecipes.size();
        }
        return 0;
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder{

        private ImageView recipes_image;
        private TextView recipes_name;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);

            recipes_image = (ImageView)itemView.findViewById(R.id.recipes_image);
            recipes_name = (TextView)itemView.findViewById(R.id.recipes_name);
        }
    }
}
