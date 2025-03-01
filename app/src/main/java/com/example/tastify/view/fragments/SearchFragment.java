/*
package com.example.tastify.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastify.R;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.dataClasses.Category;
import com.example.tastify.model.dataClasses.ListItem;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.SearchPresenter;
import com.example.tastify.view.viewInterfaces.SearchVireInterface;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchVireInterface {

    ChipGroup chipGroup;
    SearchView searchView;

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    SearchAdapter adapter;
    Chip countryChip, categoryChip, ingredientsChip;
    SearchPresenter presenter;

    List<ListItem> categoryList = new ArrayList<>();
    List<ListItem> ingredientList = new ArrayList<>();
    List<ListItem> areaList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }


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
        chipGroup = view.findViewById(R.id.chipGroupFilter);
        categoryChip = view.findViewById(R.id.categorychip);
        ingredientsChip = view.findViewById(R.id.ingredientschip);
        countryChip = view.findViewById(R.id.countrychip);
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.chipsRecicleView);


        adapter = new SearchAdapter(getActivity(), new ArrayList<>());
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        presenter = new SearchPresenter(this, RecipeRepository.
        getInstance(new RecipeLocalDataSource(getContext()), new RecipeRemoteDataSource(getContext())));


        chipGroup.setSingleSelection(true);
        setUpSingleChip();

       */
/* searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {


                    }
                }
        );*//*


    }


    private void setUpSingleChip(){

        for (int i=0;i<chipGroup.getChildCount();i++){
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        if(isChecked){
                            Log.i("TAG", "setUpSingleChip: ");                        }
                    }
            );


        }
    }


    @Override
    public void showCategories(List<Category> categories) {
        List<ListItem> genericList = new ArrayList<>();
        genericList.addAll(categories);
        adapter.updateUi(genericList);
    }
}*/
package com.example.tastify.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.tastify.R;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.dataClasses.Category;
import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.ListItem;
import com.example.tastify.model.dataClasses.Meal;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.SearchPresenter;
import com.example.tastify.view.viewInterfaces.SearchViweInterface;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment  implements SearchViweInterface {

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
    public SearchFragment() {
        // Required empty public constructor
    }



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


        adapter = new SearchAdapter(getActivity(), categoryList);
        manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        chipGroup.setSingleSelection(true);
        presenter = new SearchPresenter(this, RecipeRepository.
                getInstance(new RecipeLocalDataSource(getContext()), new RecipeRemoteDataSource(getContext())));

        setUpSingleChip();

       /* TextWatcher textWatcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (categoryChip.isChecked()) {
                    presenter.search(s, categoryList);
                } else if (ingredientsChip.isChecked()) {
                    presenter.search(s, ingredientList);
                } else if (countryChip.isChecked()) {
                    presenter.search(s, areaList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };

        editText.addTextChangedListener(textWatcher);*/

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

    }
    private void setUpSingleChip(){

        for (int i=0;i<chipGroup.getChildCount();i++){
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        if(isChecked){
                            if (chip.getId() == R.id.categorychip) {
                                recyclerView.setLayoutManager(manager);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                presenter.getCategoriesList();
                            } else if (chip.getId()==R.id.ingredientschip) {
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager
                                        (2, StaggeredGridLayoutManager.VERTICAL));
                                recyclerView.setPadding(10,10,10,10);
                                presenter.getIngrediantList();
                            } else if (chip.getId()==R.id.countrychip) {
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager
                                        (2, StaggeredGridLayoutManager.VERTICAL));
                                recyclerView.setPadding(10,10,10,10);
                                presenter.getCountries();

                            }
                        }
                    }
            );
        }
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
}