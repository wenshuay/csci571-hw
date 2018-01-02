package com.example.yuanw.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;


public class NewsFragment extends Fragment {

    private TextView fail;
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> newsdata;
    private String symbol;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
       fail = (TextView) view.findViewById(R.id.newsfail);
       symbol = getArguments().getString("symbol");
       // symbol= "FB";
        listView = (ListView) view.findViewById(R.id.newslistview);

        newsdata = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext()); // 'this' is the Context

        String url = "http://wenshuay-env.us-east-2.elasticbeanstalk.com/?data=NEWS&symbol=" + getArguments().getString("symbol");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <= response.length() - 1; i++) {
                            try {
                                JSONArray cur = response.getJSONArray(i);

                                String title = cur.getJSONObject(0).getString("0");
                                String link = cur.getJSONObject(1).getString("0");
                                String day = cur.getJSONObject(2).getString("0");
                                String author = cur.getJSONObject(3).getString("0");




                                Map<String, Object> map = new HashMap<>();
                                map.put("author",author);
                                map.put("title",title);
                                map.put("day",day);
                                map.put("link",link);
                                newsdata.add(map);

                            } catch (JSONException e) {

                                fail.setVisibility(View.VISIBLE);
                                e.printStackTrace();
                            }

                        }


                        simpleAdapter = new SimpleAdapter(getActivity(), newsdata,R.layout.newitem, new String[]{"author", "title","day","link"}, new int[]{R.id.newsauthor, R.id.newstitle, R.id.newsday,R.id.link});

                        listView.setAdapter(simpleAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String clicklink = ((TextView)view.findViewById(R.id.link)).getText().toString();

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(clicklink));
                                startActivity(intent);

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        fail.setVisibility(View.VISIBLE);
                    }
                });

        requestQueue.add(jsonArrayRequest);

        return view;
    }


}
