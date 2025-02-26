package com.example.tastify.view.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.dataClasses.PlannedRecipe;

import java.util.List;

public class CalenderAdabter extends RecyclerView.Adapter<CalenderAdabter.ViewHolder> {

    Context con;
    List<PlannedRecipe> recipeList;
    CalenderAdapterCommunicator communicator;



    public CalenderAdabter(Context con, List<PlannedRecipe> items,CalenderAdapterCommunicator communicator) {
        this.con = con;
        this.recipeList = items;
        this.communicator=communicator;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView deleteIcon;
        public TextView mealTitle;
        public CardView cardView;
        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealTitle = itemView.findViewById(R.id.mealTitleRV);
            imageView = itemView.findViewById(R.id.mealPhoto);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
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
        PlannedRecipe item = recipeList.get(position);
        holder.mealTitle.setText(item.strMeal);
        Glide.with(con).load(item.getStrMealThumb())
                .apply(new RequestOptions().override(227, 132))
                .into(holder.imageView);
   /*     holder.cardView.setOnClickListener(
                (view) -> {
                    Toast.makeText(con, "Card clicked: " + item.strMeal, Toast.LENGTH_SHORT).show();
                    HomeFragmentDirections.ActionHomeFragmentToRecipeDetails action =
                            HomeFragmentDirections.actionHomeFragmentToRecipeDetails(item);
                    Navigation.findNavController(view)
                            .navigate(action);
                }*/
        ;
        holder.deleteIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        communicator.onDelete(item);
                    }
                }
        );
    }


    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    public void updateUi(List<PlannedRecipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

     interface CalenderAdapterCommunicator{
        public void onDelete(PlannedRecipe recipe);
    }






}



