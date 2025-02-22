package com.example.tastify.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tastify.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class SearchFragment extends Fragment {

ChipGroup chipGroup;
Chip countryChip,categoryChip,ingredientsChip;
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
        chipGroup.setSingleSelection(true);

        setUpSingleChip(getActivity());

    }


    private void setUpSingleChip(Context context){

        for (int i=0;i<chipGroup.getChildCount();i++){
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        if(isChecked){
                            Toast.makeText(context, "chiip", Toast.LENGTH_SHORT).show();
                        }
                    }
            );


        }
    }
}