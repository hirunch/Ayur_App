package com.s22010120.ayur;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class LocationsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_locations, container, false);

       FrameLayout departmentBtn =rootView.findViewById(R.id.departmentBtn);
       FrameLayout bangalowsBtn = rootView.findViewById(R.id.bangalowsBtn);


        departmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), DepartmentsMapActivity.class);
                startActivity(intent);

            }
        });


        bangalowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), BangalowsActivity.class));

            }
        });

        return rootView;

    }
}