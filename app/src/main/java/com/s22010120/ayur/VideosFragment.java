package com.s22010120.ayur;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


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
        String video3 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" +
                "l54MJOfxGLc?si=1Z7S5yslo8_ruiBE\" title=\"YouTube video player\" frameborder=\"0\"" +
                " allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
                "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\"" +
                " allowfullscreen></iframe>";

        ytVideo3.loadData(video3,"text/html","utf-8");
        ytVideo3.getSettings().setJavaScriptEnabled(true);
        ytVideo3.setWebChromeClient((new WebChromeClient()));


        WebView ytVideo4 = rootView.findViewById(R.id.ytVideo4);
        String video4 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/F5" +
                "Pwh_ZEpAI?si=nAjcdLaTsYFiTR9r\" title=\"YouTube video player\" frameborder=\"0\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
                "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\"" +
                " allowfullscreen></iframe>";

        ytVideo4.loadData(video4,"text/html","utf-8");
        ytVideo4.getSettings().setJavaScriptEnabled(true);
        ytVideo4.setWebChromeClient((new WebChromeClient()));


        WebView ytVideo5 = rootView.findViewById(R.id.ytVideo5);
        String video5 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/zb" +
                "G2xUTWGgc?si=6iYB0UR6u7bEPyEo\" title=\"YouTube video player\" frameborder=\"0\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
                "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" " +
                "allowfullscreen></iframe>";

        ytVideo5.loadData(video5,"text/html","utf-8");
        ytVideo5.getSettings().setJavaScriptEnabled(true);
        ytVideo5.setWebChromeClient((new WebChromeClient()));

     WebView ytVideo6 = rootView.findViewById(R.id.ytVideo6);
     String video6 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/SGiA-" +
             "bjtfcE?si=Kt4N2L11aGMsdFwr\" title=\"YouTube video player\" frameborder=\"0\" " +
             "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
             "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" " +
             "allowfullscreen></iframe>";

     ytVideo6.loadData(video6,"text/html","utf-8");
     ytVideo6.getSettings().setJavaScriptEnabled(true);
     ytVideo6.setWebChromeClient((new WebChromeClient()));

     WebView ytVideo7 = rootView.findViewById(R.id.ytVideo7);
     String video7 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/pdT8" +
             "cs2RlGs?si=tzwE0_hgf59dOV77\" title=\"YouTube video player\" frameborder=\"0\" " +
             "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
             "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" " +
             "allowfullscreen></iframe>";

     ytVideo7.loadData(video7,"text/html","utf-8");
     ytVideo7.getSettings().setJavaScriptEnabled(true);
     ytVideo7.setWebChromeClient((new WebChromeClient()));

     WebView ytVideo8 = rootView.findViewById(R.id.ytVideo8);
     String video8 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/aka" +
             "wx9JCjHc?si=fsd42DvVvJLLzbbj\" title=\"YouTube video player\" frameborder=\"0\" " +
             "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
             "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" " +
             "allowfullscreen></iframe>";

     ytVideo8.loadData(video8,"text/html","utf-8");
     ytVideo8.getSettings().setJavaScriptEnabled(true);
     ytVideo8.setWebChromeClient((new WebChromeClient()));


        return rootView;
    }
}
