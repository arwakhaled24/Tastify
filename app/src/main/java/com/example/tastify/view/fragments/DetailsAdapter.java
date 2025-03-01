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

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {


    Context con;
    List<String> ingrediants;
    List<String>measurements;


    public DetailsAdapter(Context con, List<String> ingrediants,List<String>measurements) {
        this.con = con;
        this.ingrediants=ingrediants;
        this.measurements=measurements;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView ingrediant;
        public TextView measurement;

        public View layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingrediant = itemView.findViewById(R.id.ingrediant);
            imageView = itemView.findViewById(R.id.ingreImage);
            measurement = itemView.findViewById(R.id.measurement);
            layout = itemView;
        }
    }


    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.ingerdiant_item_in_recycler_view_in_details,recyclerView, false);
        return new DetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.ViewHolder holder, int position) {
        String ingrediand = ingrediants.get(position);
        String measure= measurements.get(position);

        holder.measurement.setText(measure);
        holder.ingrediant.setText(ingrediand);
        String url= "https://www.themealdb.com/images/ingredients/"+ingrediand.replace(" ", "_")+"-Small.png";
        Glide.with(con).load(url)
                .apply(new RequestOptions().override(227, 132))
                .into(holder.imageView);
    }


    public int getItemCount() {
        return ingrediants == null ? 0 : ingrediants.size();
    }

    public void updateUi(List<String> recipeList) {
        this.ingrediants = recipeList;
        notifyDataSetChanged();
    }


}
