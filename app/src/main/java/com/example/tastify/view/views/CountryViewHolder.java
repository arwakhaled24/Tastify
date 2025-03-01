package com.example.tastify.view.views;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Country;

public class CountryViewHolder extends RecyclerView.ViewHolder {

    private TextView ingrediantName;
    CardView cardView;

    public CountryViewHolder(View itemView) {
        super(itemView);
        ingrediantName = itemView.findViewById(R.id.ingrediant);
        cardView = itemView.findViewById(R.id.cardView);
    }

    public void bind(Country country, View.OnClickListener clickListener, SearchAdapter.Communicator communicator) {
        ingrediantName.setText(country.getStrArea());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.getCountrySearchKey(country.getStrArea());
            }
        }
        );

    }
}