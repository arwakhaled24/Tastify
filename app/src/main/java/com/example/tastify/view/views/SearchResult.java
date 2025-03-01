package com.example.tastify.view.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.dataClasses.SearchResponse;

public class SearchResult extends Fragment implements SearchResultAdapter.Communicator {

    SearchResultAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;

    SearchResponse searchResponse;
    public SearchResult() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchResponse = SearchResultArgs.fromBundle(getArguments()).getResult();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.search_result_recycler_view);
        adapter = new SearchResultAdapter(getActivity(),searchResponse,this);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void opedDetails(String recipeName) {
        Recipe recipe = new Recipe(recipeName);
        SearchResultDirections.ActionSearchResultToRecipeDetails action
                = SearchResultDirections.actionSearchResultToRecipeDetails(recipe);
        Navigation.findNavController(requireView())
                .navigate(action);

    }
}