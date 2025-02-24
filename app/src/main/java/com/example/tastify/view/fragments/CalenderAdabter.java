package com.example.tastify.view.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.Recipe;

import java.util.List;

public class CalenderAdabter extends RecyclerView.Adapter<CalenderAdabter.ViewHolder> {

    Context con;
    List<Recipe> recipeList;



    public CalenderAdabter(Context con, List<Recipe> items) {
        this.con = con;
        this.recipeList = items;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView favIcon;
        public TextView mealTitle;
        public TextView mealCategory;
        public TextView mealCulture;

        public CardView cardView;
        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealTitle = itemView.findViewById(R.id.mealTitleRV);
            imageView = itemView.findViewById(R.id.mealPhoto);
            favIcon = itemView.findViewById(R.id.deleteIcon);
            cardView = itemView.findViewById(R.id.cardView);
            layout = itemView;
        }
    }

    @NonNull
    public CalenderAdabter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.fav_item_list_view, recyclerView, false);
        CalenderAdabter.ViewHolder holder = new CalenderAdabter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderAdabter.ViewHolder holder, int position) {
        Recipe item = recipeList.get(position);
        holder.mealTitle.setText(item.strMeal);
        Glide.with(con).load(item.getStrMealThumb())
                .apply(new RequestOptions().override(227, 132))
                .into(holder.imageView);
        holder.cardView.setOnClickListener(
                (view) -> {
                    Toast.makeText(con, "Card clicked: " + item.strMeal, Toast.LENGTH_SHORT).show();
                    HomeFragmentDirections.ActionHomeFragmentToRecipeDetails action = HomeFragmentDirections.actionHomeFragmentToRecipeDetails(item);
                    Navigation.findNavController(view)
                            .navigate(action);
                }

        );
        holder.favIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*listener.onRemoveFromFav(item);*/
                    }
                }
        );
    }


    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    public void updateUi(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }






}



