package com.example.yuanw.myapplication;

import android.content.Context;
import com.facebook.FacebookSdk;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import static android.content.Context.MODE_PRIVATE;


public class currentFragment extends Fragment implements View.OnClickListener{


    public currentFragment() {
        // Required empty public constructor
    }
    private TextView detailfail;
    private ProgressBar detailprogressBar;
    private LinearLayout detailprolayout;
    private String symbol;
    private ListView currdetail;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> detaildata;
    private boolean isFill = false;
    private double lastprice;
    private  double change;
    private String per;
    private Spinner seletespinner;
    private WebView webView;
    private View view;
    private Button button;
    private Webviewblank webviewblank;

    private ProgressBar indicatorprogress;
    private ArrayAdapter<String> orderadapter;
    private ImageView facebook;
   // private TextView urltextview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        view = inflater.inflate(R.layout.fragment_current, container, false);


        //indicatorprogress = (ProgressBar) view.findViewById(R.id.indecatorprogress);

        detailprogressBar = (ProgressBar)view.findViewById(R.id.detailprogressBar);
        detailprolayout = (LinearLayout) view.findViewById(R.id.detailprogreslayout);
        detailfail = (TextView) view.findViewById(R.id.detailfail);
        ImageView imageView = (ImageView) view.findViewById(R.id.xingxing);
        imageView.setOnClickListener(this);

       // urltextview = (TextView) view.findViewById(R.id.url);

        facebook = (ImageView) view.findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),webviewblank.URL,Toast.LENGTH_LONG).show();
                ShareLinkContent content = new ShareLinkContent.Builder().setContentUrl(Uri.parse(webviewblank.URL)).build();
                ShareDialog.show(getActivity(),content);
            }
        });

         webviewblank = new Webviewblank(getActivity());
        button = (Button) view.findViewById(R.id.change);
        webView = (WebView) view.findViewById(R.id.indtcatorchar);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.scrollTo(View.FOCUS_UP,View.FOCUS_LEFT);
                // do something
                button.setEnabled(false);
                //indicatorprogress.setVisibility(View.VISIBLE);



                webView.getSettings().setJavaScriptEnabled(true);
                String data = seletespinner.getSelectedItem().toString();

                //webView.addJavascriptInterface(new Webviejsinterface(indicatorprogress),"Android");
                //here
                webView.addJavascriptInterface(webviewblank,"Android");

               // webView.addJavascriptInterface(new Webviegeturl(urltextview),"Android");
                webView.loadUrl("file:///android_asset/indicatro.html?sy="+ symbol+ "&data=" + data);

            }
        });
        symbol = getArguments().getString("symbol");


        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("local",MODE_PRIVATE );

        if (sharedPreferences.contains(symbol)) {
            imageView.setImageResource(R.drawable.filled);
            isFill = true;
        }



        currdetail = (ListView) view.findViewById(R.id.detail);


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext()); // 'this' is the Context

        String url = "http://wenshuay-env.us-east-2.elasticbeanstalk.com/?data=price&symbol=" + symbol;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject time = response.getJSONObject("Time Series (Daily)");
                            JSONObject meta = response.getJSONObject("Meta Data");
                            Iterator keyItera = time.keys();
                            int count = 0;
                            JSONObject firstday = null;
                            JSONObject seconday = null;
                            while (keyItera.hasNext()) {
                                String key = (String) keyItera.next();
                                if (count == 0) {
                                    firstday = time.getJSONObject(key);
                                } else if (count == 1) {
                                    seconday = time.getJSONObject(key);
                                    break;
                                }
                                count++;

                            }
                            lastprice = firstday.getDouble("4. close");
                            double secondclose = seconday.getDouble("4. close");

                            change = lastprice - secondclose;

                            change = Math.floor(change * 100) / 100;

                            double changde = change / secondclose;

                            double cper = changde * 100;
                            cper = Math.floor(cper * 100) / 100;

                            per = cper + "%";
                            double close = 0;
                            String symbol = meta.getString("2. Symbol");
                            String timestamp = meta.getString("3. Last Refreshed");
                            if (timestamp.length() > 10) {
                                timestamp = timestamp + " EDT";
                                close = secondclose;
                            } else {
                                timestamp = timestamp  + " 16:00:00 EDT";
                                close = lastprice;
                            }
                            Object pic ;
                            if (change > 0){
                                pic = R.drawable.up;
                            } else {
                                pic = R.drawable.down;
                            }

                            double open = firstday.getDouble("1. open");
                            String dayrang = firstday.getString("3. low") + "-"+ firstday.getString("2. high");
                            String volume = firstday.getString("5. volume");

                            detaildata = new ArrayList<>();

                            Map<String,Object> symbolmap = new HashMap<>();
                            symbolmap.put("detailkey", "Stock Symbol");
                            symbolmap.put("detailvalue", symbol);
                            detaildata.add(symbolmap);

                            Map<String,Object> lastpricemap = new HashMap<>();
                            lastpricemap.put("detailkey", "Last Price");
                            lastpricemap.put("detailvalue", Double.toString(lastprice));
                            detaildata.add(lastpricemap);

                            Map<String,Object> changemap = new HashMap<>();
                            changemap.put("detailkey", "Change");
                            changemap.put("detailvalue", change + "(" + cper + "%)");
                            changemap.put("upordown", pic);
                            detaildata.add(changemap);

                            Map<String,Object> timestampmap = new HashMap<>();
                            timestampmap.put("detailkey", "Timestamp");
                            timestampmap.put("detailvalue", timestamp);
                            detaildata.add(timestampmap );

                            Map<String,Object> openmap = new HashMap<>();
                            openmap.put("detailkey", "Open");
                            openmap.put("detailvalue", open);
                            detaildata.add(openmap );

                            Map<String,Object> closemap = new HashMap<>();
                            closemap.put("detailkey", "Close");
                            closemap.put("detailvalue", close);
                            detaildata.add(closemap );

                            Map<String,Object> rangemap = new HashMap<>();
                            rangemap.put("detailkey", "Day's Range");
                            rangemap.put("detailvalue", dayrang);
                            detaildata.add(rangemap );

                            Map<String,Object> volumemap = new HashMap<>();
                            volumemap.put("detailkey", "Volume");
                            volumemap.put("detailvalue", volume);
                            detaildata.add(volumemap );

                            simpleAdapter = new SimpleAdapter(getActivity(), detaildata,R.layout.item, new String[]{"detailkey", "detailvalue","upordown"}, new int[]{R.id.detailkey, R.id.detailvalue,R.id.upordown});
                            detailprolayout.setVisibility(View.GONE);
                            detailprogressBar.setVisibility(View.GONE);
                            currdetail.setAdapter(simpleAdapter);







                        } catch (JSONException e) {
                            currdetail.setVisibility(View.GONE);
                            detailprolayout.setVisibility(View.GONE);
                            detailprogressBar.setVisibility(View.GONE);
                            detailfail.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /* TODO Auto-generated method stub */
                        currdetail.setVisibility(View.GONE);
                        detailprolayout.setVisibility(View.GONE);
                        detailprogressBar.setVisibility(View.GONE);
                        detailfail.setVisibility(View.VISIBLE);
                    }
                });

        requestQueue.add(jsObjRequest);

        seletespinner = (Spinner) view.findViewById(R.id.indicatorselect);
        String[] indicatorvalue = new String[]{"Price", "SMA","EMA","RSI","ADX","CCI","STOCH","BBANDS","MACD"};
        orderadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, indicatorvalue);
        seletespinner.setAdapter(orderadapter);
        seletespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(),"DD",Toast.LENGTH_LONG).show();
                button.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //webView = (WebView) view.findViewById(R.id.indtcatorchar);
        webView.getSettings().setJavaScriptEnabled(true);

        //webView.addJavascriptInterface(new Webviejsinterface(indicatorprogress),"Android");

        //here
        webView.addJavascriptInterface(webviewblank,"Android");
        //webView.addJavascriptInterface(new Webviegeturl(urltextview),"Android");
        webView.loadUrl("file:///android_asset/indicatro.html?sy="+ symbol+ "&data=Price");

        return view;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.xingxing:
                Context context = getActivity();
                SharedPreferences sharedPreferences = context.getSharedPreferences("local",MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPreferences.edit();

                ImageView imageView = (ImageView) view.findViewById(R.id.xingxing);
                if (isFill == true) {

                    imageView.setImageResource(R.drawable.empty);
                    editor.remove(symbol);
                    editor.commit();
                } else {
                    imageView.setImageResource(R.drawable.filled);

                    FavItem myFav = new FavItem(symbol, lastprice, change,per);
                    Gson gson = new Gson();

                    String json = gson.toJson(myFav);


                    editor.putString(symbol, json);
                    editor.commit();



                }
                isFill = !isFill;
                break;


        }
    }
/*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        orderadapter.notifyDataSetChanged();
        button.setEnabled(true);



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
