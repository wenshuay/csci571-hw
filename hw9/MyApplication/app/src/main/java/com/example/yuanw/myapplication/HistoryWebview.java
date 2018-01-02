package com.example.yuanw.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by yuanw on 2017/11/29.
 */

public class HistoryWebview extends AsyncTask {
    @Override
    protected String doInBackground(Object[] objects) {


        WebView webView = (WebView) objects[0];
        String symbol = (String) objects[1];


        webView.loadUrl("file:///android_asset/tennis.html?sy="+ symbol);

        return "HI";
    }
}
