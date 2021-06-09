package com.example.eatcleanapp.ui.congtacvien.tabCTV;

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
import com.example.eatcleanapp.model.recipes;

import java.util.List;


public class UpdateRecipeAdapter extends RecyclerView.Adapter<UpdateRecipeAdapter.UpdateRecipeViewHolder>{

    private Context context;
    private IClickListener iClickListener;
    private List<recipes> mListRecipes;

    public UpdateRecipeAdapter(Context context, IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }

    public void setData(List<recipes> list){
        this.mListRecipes = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpdateRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recipe_ctv, parent, false);
        return new UpdateRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateRecipeViewHolder holder, int position) {
        recipes recipe = mListRecipes.get(position);
        if(recipe == null){
            return;
        }
        holder.txv_updateRecipe_Title.setText(recipe.getRecipesTitle());
        holder.txv_updateRecipe_Content.setText(recipe.getRecipesContent());
        holder.txv_updateRecipe_Author.setText(recipe.getRecipesAuthor());
        Glide.with(context).load(recipe.getImageMain()).placeholder(R.drawable.gray).into(holder.imgV_updateRecipe);
    }

    @Override
    public int getItemCount() {
        if(mListRecipes != null){
            return mListRecipes.size();
        }
        return 0;
    }

    public class UpdateRecipeViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgV_updateRecipe;
        private TextView txv_updateRecipe_Title, txv_updateRecipe_Content, txv_updateRecipe_Author;

        public UpdateRecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            imgV_updateRecipe = (ImageView)itemView.findViewById(R.id.imgV_updateRecipe);
            txv_updateRecipe_Title = (TextView)itemView.findViewById(R.id.txv_updateRecipe_Title);
            txv_updateRecipe_Content = (TextView)itemView.findViewById(R.id.txv_updateRecipe_Content);
            txv_updateRecipe_Author = (TextView)itemView.findViewById(R.id.txv_updateRecipe_Author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickListener.clickItem(getAdapterPosition());
                }
            });
        }
    }
}
