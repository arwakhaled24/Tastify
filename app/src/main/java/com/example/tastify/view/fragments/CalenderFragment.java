package com.example.tastify.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tastify.R;
import com.example.tastify.model.Recipe;

import java.util.ArrayList;

public class CalenderFragment extends Fragment {

    RecyclerView recyclerView;
    CalenderAdabter adapter;
    LinearLayoutManager manager;

    ArrayList<Recipe> recibesList=new ArrayList<>();


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


        Recipe re = new Recipe("arwa","arwa","arwa","arwa");
        Recipe sa = new Recipe("arwa","arwa","arwa","arwa");

        recibesList.add(re);
        recibesList.add(sa);
        recyclerView = view.findViewById(R.id.calenderRecyclerView);
        adapter = new CalenderAdabter(getActivity(), recibesList);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}