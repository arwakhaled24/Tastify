package com.example.tastify.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.example.tastify.R;
import com.example.tastify.model.PlannedRecipe;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.CalenderPresenter;

import java.util.ArrayList;
import java.util.List;

public class CalenderFragment extends Fragment implements CalenderViewInterface {

    RecyclerView recyclerView;
    CalenderAdabter adapter;
    LinearLayoutManager manager;

    List<PlannedRecipe> recipes =new ArrayList<>();

    CalenderPresenter presenter;
    CalendarView calenderView ;


    public CalenderFragment() {
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
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.calenderRecyclerView);
        adapter = new CalenderAdabter(getActivity(), recipes);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        calenderView=view.findViewById(R.id.calendarView);

        presenter=new CalenderPresenter(this,
                RecipeRepository.getInstance(new RecipeLocalDataSource(getContext()),new RecipeRemoteDataSource(getContext())));


        calenderView.setOnDateChangeListener(
                (view1, year, month, dayOfMonth) -> {
                    String date=String.valueOf(year)+String.valueOf(month)+String.valueOf(dayOfMonth);
                    presenter.getPlannedByDate(date);
                }
        );
    }

    @Override
    public void getRecipesByDate(String date) {
        LiveData<List<PlannedRecipe>> liveData = presenter.getPlannedByDate(date);
        Observer<List<PlannedRecipe>> observer = new Observer<List<PlannedRecipe>>() {
            @Override
            public void onChanged(List<PlannedRecipe> products) {
                adapter.updateUi(products);
            }
        };
        liveData.observe(getActivity(), observer);
    }
}