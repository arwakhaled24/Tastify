package com.example.tastify.view.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.dataClasses.SearchResponse;
import com.example.tastify.model.dataClasses.SearchResponseItem;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{

    Context con;
    SearchResponse mealsResponse;


    public SearchResultAdapter(Context con, SearchResponse mealsResponse) {
        this.con = con;
        this.mealsResponse = mealsResponse;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView mealTitle;

        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealTitle = itemView.findViewById(R.id.ingrediant);
            imageView = itemView.findViewById(R.id.ingreImage);
            layout = itemView;
        }
    }


    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.categoey_item_recycler_view, recyclerView, false);
        SearchResultAdapter.ViewHolder holder = new SearchResultAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder holder, int position) {
        SearchResponseItem item = mealsResponse.getMeals().get(position);
        holder.mealTitle.setText(item.getName());
        Glide.with(con).load(item.getImage())
                .apply(new RequestOptions().override(227, 132))
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mealsResponse.getMeals().size();
    }

}
