package com.example.tastify.view.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Category;
import com.example.tastify.model.dataClasses.Country;
import com.example.tastify.model.dataClasses.ListItem;
import com.example.tastify.model.dataClasses.ListItemType;
import com.example.tastify.model.dataClasses.Meal;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context con;
    private List<ListItem> itemList;

    Communicator communicator;


    public SearchAdapter(Context con, List<ListItem> items,Communicator communicator) {
        this.con = con;
        this.itemList = items;
        this.communicator=communicator;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItemType.TYPE_CATEGORY:
                View categoryView = inflater.inflate(R.layout.categoey_item_recycler_view, parent, false);
                return new CategoryViewHolder(categoryView);
            case ListItemType.TYPE_INGREDIENT:
                View ingredientView = inflater.inflate(R.layout.ingrediants_item_recycler_view, parent, false);
                return new IngrediantsViewHolder(ingredientView);
            case ListItemType.TYPE_AREA:
                View areaView = inflater.inflate(R.layout.ingrediants_item_recycler_view, parent, false);
                return new CountryViewHolder(areaView);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem item = itemList.get(position);
        switch (holder.getItemViewType()) {
            case ListItemType.TYPE_CATEGORY:
                if (item instanceof Category) {
                    ((CategoryViewHolder) holder).bind((Category) item, v -> {
                        Toast.makeText(v.getContext(), "Clicked on Category: " + ((Category) item).getStrCategory(), Toast.LENGTH_SHORT).show();
                    },communicator);
                }
                break;
            case ListItemType.TYPE_INGREDIENT:
                ((IngrediantsViewHolder) holder).bind((Meal) item, v -> {
                    Toast.makeText(v.getContext(), "Clicked on Category: " + ((Meal) item).getStrIngredient(), Toast.LENGTH_SHORT).show();
                }, communicator );
                break;
            case ListItemType.TYPE_AREA:
                ((CountryViewHolder) holder).bind((Country) item, v -> {
                    Toast.makeText(v.getContext(), "Clicked on Category: " + ((Country) item).getStrArea(), Toast.LENGTH_SHORT).show();
                },communicator);                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }


    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public void updateUi(List<ListItem> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }
    interface Communicator {
        void getIngrediantSearchKey(String wordKey);
        void getCategorySearchKey(String category);
        void getCountrySearchKey(String country);
    }

}



