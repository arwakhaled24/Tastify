package com.example.tastify.view.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Meal;
public class IngrediantsViewHolder  extends RecyclerView.ViewHolder{
    private TextView ingrediantName;
    CardView cardView ;
    public IngrediantsViewHolder(View itemView) {
        super(itemView);
        ingrediantName = itemView.findViewById(R.id.mealTitleRV);
        cardView = itemView.findViewById(R.id.cardView);
    }

    public void bind(Meal meal, View.OnClickListener clickListener) {
        ingrediantName.setText(meal.getStrIngredient());
        cardView.setOnClickListener(clickListener);

    }
}
