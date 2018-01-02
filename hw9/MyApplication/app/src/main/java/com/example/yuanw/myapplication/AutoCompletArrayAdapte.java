package com.example.yuanw.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import android.widget.Filterable;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by yuanw on 2017/11/28.
 */

public class AutoCompletArrayAdapte extends ArrayAdapter<String> implements Filterable {
    ArrayList<String> data;
    private Context mycontext;

    public AutoCompletArrayAdapte(@NonNull Context context, int resource) {
        super(context, resource);
        data = new ArrayList<>();
        mycontext = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    public void  getJson (String input) {
        RequestQueue requestQueue = Volley.newRequestQueue(mycontext); // 'this' is the Context

        String url = "http://dev.markitondemand.com/MODApis/Api/v2/Lookup/json?input=" + input;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        data.clear();
                        for (int i = 0; i <= response.length() - 1 && i <= 4; i++) {
                            try {
                                JSONObject each = response.getJSONObject(i);
                                String symbol = each.getString("Symbol");
                                String name = each.getString("Name");
                                String exchange = each.getString("Exchange");
                                String res = symbol + "-" + name + "(" + exchange + ")";

                                data.add(res);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        Toast.makeText(mycontext, data.size() + "diyi", Toast.LENGTH_LONG).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mycontext, "cuowu", Toast.LENGTH_LONG).show();
                    }
                });
              requestQueue.add(jsonArrayRequest);
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter myfilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults filterResults = new FilterResults();
                //filterResults = new FilterResults();
                if (charSequence != null) {
                   // data = new ArrayList<>();
                    //Toast.makeText(mycontext, charSequence.toString(), Toast.LENGTH_LONG).show();
                    //getJson(charSequence.toString());
                    //Toast.makeText(mycontext, data.size() + "", Toast.LENGTH_LONG).show();
                    try {
                        data = (ArrayList<String>) new GetResult().execute(new String[]{charSequence.toString()}).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    filterResults.values = data;
                    filterResults.count = data.size();








                }
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults != null && filterResults.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myfilter;
    }

}
