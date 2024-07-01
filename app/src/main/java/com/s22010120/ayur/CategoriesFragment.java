package com.s22010120.ayur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;


public class CategoriesFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_categories, container, false);

        ImageView Ads1 = rootView.findViewById(R.id.imageAds1);
        ImageView Ads2 = rootView.findViewById(R.id.imageAds2);
        GridLayout osuBtn = rootView.findViewById(R.id.osu);
        GridLayout athBehethBtn = rootView.findViewById(R.id.athBeheth);
        GridLayout otherHelpsBtn = rootView.findViewById(R.id.otherHelps);


        Ads1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format("https://ayurveda.gov.lk/press-release/");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        Ads2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format("https://ayurveda.gov.lk/international-research-expo-educational-and-trade-exhibition-ayur-ex-2024");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        osuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), OsuInfoActivity.class);
                startActivity(intent);
            }
        });

        athBehethBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AthBethethActivity.class);
                startActivity(intent);
            }
        });

        otherHelpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), OthersHelpActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

}