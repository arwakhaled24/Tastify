package com.example.tastify.view.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Country;

public class CountryViewHolder extends RecyclerView.ViewHolder {

        private TextView ingrediantName;
        CardView cardView ;
        public CountryViewHolder(View itemView) {
            super(itemView);
            ingrediantName = itemView.findViewById(R.id.ingrediant);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bind(Country meal, View.OnClickListener clickListener) {
            ingrediantName.setText(meal.getStrArea());
            cardView.setOnClickListener(clickListener);

        }
    }

