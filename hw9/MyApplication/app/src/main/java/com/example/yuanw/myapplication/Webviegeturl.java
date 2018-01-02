package com.example.yuanw.myapplication;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

/**
 * Created by yuanw on 2017/11/30.
 */

public class Webviegeturl {
    TextView textView;

    public Webviegeturl(TextView textView) {
        this.textView = textView;
    }


    @JavascriptInterface
    public void getUrl (String url) {
        textView.setText(url);
    }
}
