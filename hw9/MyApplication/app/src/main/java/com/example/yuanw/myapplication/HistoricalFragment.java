package com.example.yuanw.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutionException;


public class HistoricalFragment extends Fragment {
    private String symbol;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historical, container, false);
        //ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.historyprogress);
        symbol = getArguments().getString("symbol");
        WebView webView = (WebView) view.findViewById(R.id.histchar);
        webView.getSettings().setJavaScriptEnabled(true);

        //webView.addJavascriptInterface(new Webviejsinterface(progressBar),"Android");

         webView.loadUrl("file:///android_asset/tennis.html?sy="+ symbol);

/*
        Object[] objects = new Object[2];
        objects[0] = webView;
        objects[1] = symbol;
        try {
            new HistoryWebview().execute(objects).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
*/

        return view;
    }




}
