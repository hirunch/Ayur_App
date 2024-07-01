package com.s22010120.ayur;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.facebook.shimmer.ShimmerFrameLayout;


public class VideosFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_videos, container, false);


        WebView ytVideo1 = rootView.findViewById(R.id.ytVideo1);
        String video1 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" +
                "za9M_RyzoUw?si=aHkRm-P9aPenmz9u\" title=\"YouTube video player\" frameborder=\"0\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
                "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" " +
                "allowfullscreen></iframe>";

        ytVideo1.loadData(video1,"text/html","utf-8");
        ytVideo1.getSettings().setJavaScriptEnabled(true);
        ytVideo1.setWebChromeClient((new WebChromeClient()));

        WebView ytVideo2 = rootView.findViewById(R.id.ytVideo2);
        String video2 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" +
                "j87XV7VytY8?si=ZJNLkuAQs5RlE8et\" title=\"YouTube video player\" frameborder=\"0\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
                "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" " +
                "allowfullscreen></iframe>";

        ytVideo2.loadData(video2,"text/html","utf-8");
        ytVideo2.getSettings().setJavaScriptEnabled(true);
        ytVideo2.setWebChromeClient((new WebChromeClient()));

        WebView ytVideo3 = rootView.findViewById(R.id.ytVideo3);
        String video3 = "<iframe src=\"https://drive.google.com/file/d/1Xdkk0R6KiROaQ5v93PwGVGrTY52kKqLh/preview\" " +
                "width=\"100%\" height=\"100%\" allow=\"autoplay\"></iframe>";

        ytVideo3.loadData(video3,"text/html","utf-8");
        ytVideo3.getSettings().setJavaScriptEnabled(true);
        ytVideo3.setWebChromeClient((new WebChromeClient()));


        WebView ytVideo4 = rootView.findViewById(R.id.ytVideo4);
        String video4 = "<iframe src=\"https://drive.google.com/file/d/1Hd2iQAx3bVbOl55NffD65iAQMy3yb3fi/preview\" " +
                "width=\"100%\" height=\"100%\" allow=\"autoplay\"></iframe>";

        ytVideo4.loadData(video4,"text/html","utf-8");
        ytVideo4.getSettings().setJavaScriptEnabled(true);
        ytVideo4.setWebChromeClient((new WebChromeClient()));


        WebView ytVideo5 = rootView.findViewById(R.id.ytVideo5);
        String video5 = "<iframe src=\"https://drive.google.com/file/d/1YIq7l4WjAtjeaSBWlEKRKUu44JEXsSGJ/preview\" " +
                "width=\"100%\" height=\"100%\" allow=\"autoplay\"></iframe>";

        ytVideo5.loadData(video5,"text/html","utf-8");
        ytVideo5.getSettings().setJavaScriptEnabled(true);
        ytVideo5.setWebChromeClient((new WebChromeClient()));


        return rootView;


    }
}