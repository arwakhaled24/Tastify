package com.example.tastify.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.Recipe;

import java.util.List;



//public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.ViewHolder> {
public class HomeFragAdapter extends RecyclerView.Adapter<HomeFragAdapter.ViewHolder> {

    Context con;
    List<Recipe> recipeList;
    onRemoveFromFavInterface listener;

    public HomeFragAdapter(Context con, List<Recipe> items, onRemoveFromFavInterface listener) {
        this.con = con;
        this.recipeList = items;
        this.listener = listener;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView favIcon;
        public TextView mealTitle;
        public TextView mealCategory;
        public TextView mealCulture;


        public View layout;
        Button removeBtn;
        public ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealTitle = itemView.findViewById(R.id.mealTitleRV);
            imageView = itemView.findViewById(R.id.mealPhoto);
            mealCategory= itemView.findViewById(R.id.mealCategory);
            mealCulture=itemView.findViewById(R.id.cultureRV);
            layout = itemView;
        }
    }

    @NonNull
    public HomeFragAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.list_view_home_item, recyclerView, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe item = recipeList.get(position);
        holder.mealTitle.setText(item.strMeal);
        Glide.with(con).load(item.getStrMealThumb())
                .apply(new RequestOptions().override(227, 132))
                .into(holder.imageView);
    }


    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }
    public interface onRemoveFromFavInterface {
        void removeFromFav(Recipe recipe);
    }
    public void updateUi(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

}



