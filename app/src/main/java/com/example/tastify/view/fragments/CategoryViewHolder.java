package com.example.tastify.view.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryName;
    private ImageView categoryThumb;
CardView cardView ;
    public CategoryViewHolder(View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.ingrediant);
        categoryThumb = itemView.findViewById(R.id.ingreImage);
        cardView = itemView.findViewById(R.id.cardView);
    }

    public void bind(Category category, View.OnClickListener clickListener) {
        categoryName.setText(category.getStrCategory());
        Glide.with(itemView.getContext())
                .load(category.getStrCategoryThumb())
                .apply(new RequestOptions()
                .override(227, 132))
                .into(categoryThumb);
        cardView.setOnClickListener(clickListener);

    }
}
