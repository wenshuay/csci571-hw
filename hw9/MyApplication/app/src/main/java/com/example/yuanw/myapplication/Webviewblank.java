package com.example.yuanw.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

/**
 * Created by yuanw on 2017/11/30.
 */

public class Webviewblank {

 Activity activity;
 String URL;

    public Webviewblank(Activity activity) {
        this.activity = activity;
    }


    @JavascriptInterface
    public void getUrl (String url) {
        URL =url;
        //TextView textView = (TextView) activity.findViewById(R.id.url);
        //textView.setText(url);
        //TextView textView = (TextView) view.findViewById(R.id.url);
        //textView.setText(url);
    }
}
