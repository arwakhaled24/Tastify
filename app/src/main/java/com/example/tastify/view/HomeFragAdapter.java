package com.example.tastify.view;

import android.content.Context;
import android.util.Log;
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
import com.example.tastify.view.fragments.HomeFragmentDirections;

import java.util.List;


public class HomeFragAdapter extends RecyclerView.Adapter<HomeFragAdapter.ViewHolder> {

    Context con;
    List<Recipe> recipeList;
    AdapterFragmentCommunicator listener;
    boolean isFav=false;
    public HomeFragAdapter(Context con, List<Recipe> items, AdapterFragmentCommunicator listener) {
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

       /* public CardView cardView;*/

        public View layout;

        View constrainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealTitle = itemView.findViewById(R.id.mealTitleRV);
            imageView = itemView.findViewById(R.id.mealPhoto);
            favIcon = itemView.findViewById(R.id.favIcon);
            constrainLayout=itemView.findViewById(R.id.homeItemLayout);
/*
            cardView = itemView.findViewById(R.id.cardView);
*/
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
        holder.constrainLayout.setOnClickListener(
                (view) -> {
                    HomeFragmentDirections.ActionHomeFragmentToRecipeDetails action = HomeFragmentDirections.actionHomeFragmentToRecipeDetails(item);
                    Navigation.findNavController(view)
                            .navigate(action);
                }

        );
        holder.favIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            listener.onAddToFav(item);
                            //add animation

                    }
                }
        );


    }


    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    public interface AdapterFragmentCommunicator {
        void removeFromFav(Recipe recipe);

        void onAddToFav(Recipe recipe);
    }

    public interface onAddToFavInterface {

    }

    public void updateUi(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

}



