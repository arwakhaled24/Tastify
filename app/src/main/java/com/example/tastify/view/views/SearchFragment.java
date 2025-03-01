
package com.example.tastify.view.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.tastify.R;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.dataClasses.Category;
import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.ListItem;
import com.example.tastify.model.dataClasses.Meal;
import com.example.tastify.model.dataClasses.SearchResponse;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.SearchPresenter;
import com.example.tastify.utils.BaseConnections;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.SearchViweInterface;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseConnections implements SearchViweInterface ,SearchAdapter.Communicator  {

    ChipGroup chipGroup;
    Chip countryChip,categoryChip,ingredientsChip;
    SearchPresenter presenter;
    List<ListItem> categoryList = new ArrayList<>();
    List<ListItem> ingredientList = new ArrayList<>();
    List<ListItem> areaList = new ArrayList<>();
    LinearLayoutManager manager;
    SearchAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
    LinearLayout chipsLayout;

    ConstraintLayout connectionLayout;
    public SearchFragment() {
        // Required empty public constructor
    }

    SearchResponse searchResponse;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chipGroup=view.findViewById(R.id.chipGroupFilter);
        categoryChip=view.findViewById(R.id.categorychip);
        ingredientsChip=view.findViewById(R.id.ingredientschip);
        countryChip=view.findViewById(R.id.countrychip);
        recyclerView=view.findViewById(R.id.chipsRecicleView);
        searchView=view.findViewById(R.id.searchView);
        connectionLayout=view.findViewById(R.id.connectionLayout);
        chipsLayout =view.findViewById(R.id.linearLayout);


        adapter = new SearchAdapter(getActivity(), categoryList,this);
        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        chipGroup.setSingleSelection(true);
        presenter = new SearchPresenter(this, RecipeRepository.
                getInstance(new RecipeLocalDataSource(getContext()), new RecipeRemoteDataSource(getContext()), SharedPreferencesHelper.getInstance(getContext())));

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (categoryChip.isChecked()) {
                            presenter.search(newText, categoryList);
                        } else if (ingredientsChip.isChecked()) {
                            presenter.search(newText, ingredientList);
                        } else if (countryChip.isChecked()) {
                            presenter.search(newText, areaList);
                        }
                        return false;
                    }
                }
        );
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == View.NO_ID) {
                    adapter.updateUi(new ArrayList<>());
                } else {
                    if (checkedId == R.id.categorychip) {
                        recyclerView.setLayoutManager(manager);
                        manager.setOrientation(LinearLayoutManager.VERTICAL);
                        presenter.getCategoriesList();
                    } else if (checkedId == R.id.ingredientschip) {
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setPadding(10, 10, 10, 10);
                        presenter.getIngrediantList();
                    } else if (checkedId == R.id.countrychip) {
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setPadding(10, 10, 10, 10);
                        presenter.getCountries();
                    }
                }
            }
        });


    }

    @Override
    public void showCategories(List<Category> categories) {
        categoryList.clear();
        categoryList.addAll(categories);
        adapter.updateUi(categoryList);
    }


    @Override
    public void showIngrediants(List<Meal> ingredients) {
        ingredientList.clear();
        ingredientList.addAll(ingredients);
        adapter.updateUi(ingredientList);
    }
    @Override
    public void showCountries(CountryResponse countries) {
        areaList.clear();
        areaList.addAll(countries.countries);
        adapter.updateUi(areaList);
    }

    @Override
    public void getSearchByIngrediant(SearchResponse searchResponse) {
        SearchFragmentDirections.ActionSearchFragmentToSearchResult action
                = SearchFragmentDirections.actionSearchFragmentToSearchResult(searchResponse);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void getSearchByCategory(SearchResponse searchResponse) {
        SearchFragmentDirections.ActionSearchFragmentToSearchResult action
                = SearchFragmentDirections.actionSearchFragmentToSearchResult(searchResponse);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void getSearchByCountru(SearchResponse searchResponse) {
        SearchFragmentDirections.ActionSearchFragmentToSearchResult action
                = SearchFragmentDirections.actionSearchFragmentToSearchResult(searchResponse);
        Navigation.findNavController(requireView()).navigate(action);
    }
    @Override
    public void getIngrediantSearchKey(String wordKey) {
        presenter.searchByIngredient(wordKey);
    }

    @Override
    public void getCategorySearchKey(String category) {
        presenter.searchByCategory(category);
    }

    @Override
    public void getCountrySearchKey(String country) {
        presenter.searchByCountry(country);
    }


   void  onInternetConnection(Boolean isConnected){
        if(isConnected){
            connectionLayout.setVisibility(View.INVISIBLE);
            chipsLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
        }else{
            connectionLayout.setVisibility(View.VISIBLE);
            chipsLayout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            searchView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onNetworkChanged(boolean isConnected) {
        onInternetConnection(isConnected);
    }
}