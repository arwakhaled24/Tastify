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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;





public class FavFragmentAdapter extends RecyclerView.Adapter<FavFragmentAdapter.ViewHolder> {

    Context con;
    List<Recipe> recipeList;
  AdapterFavFragmentCommunicator listener;


    public FavFragmentAdapter(Context con, List<Recipe> items, AdapterFavFragmentCommunicator listener) {
        this.con = con;
        this.recipeList = items;
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView favIcon;
        public TextView mealTitle;
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
    public FavFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.fav_item_list_view, recyclerView, false);
        FavFragmentAdapter.ViewHolder holder = new FavFragmentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavFragmentAdapter.ViewHolder holder, int position) {
        Recipe item = recipeList.get(position);
        holder.mealTitle.setText(item.strMeal);
        Glide.with(con).load(item.getStrMealThumb())
                .apply(new RequestOptions().override(227, 132))
                .into(holder.imageView);
        holder.cardView.setOnClickListener(
                (view) -> {
                    Toast.makeText(con, "Card clicked: " + item.strMeal, Toast.LENGTH_SHORT).show();
                 listener.navigateToDetails(item);
                }

        );
        holder.favIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onRemoveFromFav(item);
                    }
                }
        );
    }


    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    public void updateUi(List<Recipe> recipeList) {
        if(recipeList.size()==0)
            listener.onEmptyList(true);
        else listener.onEmptyList(false);
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public interface AdapterFavFragmentCommunicator {
        void onRemoveFromFav(Recipe recipe);

        void navigateToDetails(Recipe recipe);
        void onEmptyList(boolean isEmpty);
    }
}



