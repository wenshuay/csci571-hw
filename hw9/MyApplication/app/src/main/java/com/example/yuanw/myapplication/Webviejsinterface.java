package com.example.yuanw.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by yuanw on 2017/11/29.
 */

public class Webviejsinterface {
  /*Context context;

    public Webviejsinterface(Context context) {
        this.context = context;
    }
*/

  ProgressBar progressBar;

    public Webviejsinterface(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @JavascriptInterface
    public void hide(){

       // View view = View.inflate(context, R.layout.fragment_historical, null);
       // ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.historyprogress);
        progressBar.setVisibility(View.GONE);


    }
}
