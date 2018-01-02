package com.example.yuanw.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AutoCompleteTextView auto;
    private ListView favlistview;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> favalistdata;
    private  Spinner sortby;
    private   Spinner order;
    private  Map<String, ?> alllocaldata;
    private String sortbysele;
    private String ordersele;
    private TextView clear;
    private Switch automatic;
    private Handler automaitchandler;
    private Runnable autorunnable;
    private ProgressBar favalistprogress;
    Handler clickrefrshhandler;
    Runnable clickrefresrunable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auto = (AutoCompleteTextView) findViewById(R.id.auto);
        AutoCompletArrayAdapte adapte = new AutoCompletArrayAdapte(this,android.R.layout.simple_dropdown_item_1line);
        auto.setAdapter(adapte);

        clear = findViewById(R.id.clear);
        favalistprogress = (ProgressBar) findViewById(R.id.favlistprogressBar);


        ImageView clickrefresh = findViewById(R.id.clickfresh);
        clickrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickrefrshhandler.postDelayed(clickrefresrunable,5000);
            }
        });

         clickrefrshhandler = new Handler();
         clickrefresrunable = new Runnable() {
            @Override
            public void run() {
                favalistprogress.setVisibility(View.VISIBLE);

                ArrayList<String> favalistname = new ArrayList<>();

                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("local",MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                alllocaldata = sharedPreferences.getAll();

                Gson gson = new Gson();
                String j = "";
                for (String key : alllocaldata.keySet()) {

                    String value = (String) alllocaldata.get(key);
                    FavItem cur = gson.fromJson(value,FavItem.class);
                    j = j + "symbol[]="+ cur.symbol+"&";


                }
                if (j.length() != 0) {
                    j = j.substring(0,j.length() - 1);
                }



                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this); // 'this' is the Context
                //Toast.makeText(MainActivity.this, j,Toast.LENGTH_LONG).show();
                String url = "http://wenshuay-env.us-east-2.elasticbeanstalk.com/?data=fresh&" + j;
                //String url = "http://wenshuay-env.us-east-2.elasticbeanstalk.com/?data=price&symbol=FB";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                //Toast.makeText(MainActivity.this, response.toString().length() +"",Toast.LENGTH_LONG).show();
                                favalistprogress.setVisibility(View.GONE);


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                        /* TODO Auto-generated method stub */
                                //Toast.makeText(MainActivity.this, "wring",Toast.LENGTH_LONG).show();
                                favalistprogress.setVisibility(View.GONE);
                            }
                        });
                requestQueue.add(jsObjRequest);


            }
        };

        automaitchandler = new Handler();
        autorunnable = new Runnable() {
            @Override
            public void run() {

                favalistprogress.setVisibility(View.VISIBLE);

                ArrayList<String> favalistname = new ArrayList<>();

                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("local",MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                alllocaldata = sharedPreferences.getAll();

                Gson gson = new Gson();
                String j = "";
                for (String key : alllocaldata.keySet()) {

                    String value = (String) alllocaldata.get(key);
                    FavItem cur = gson.fromJson(value,FavItem.class);
                    j = j + "symbol[]="+ cur.symbol+"&";


                }
                if (j.length() != 0) {
                    j = j.substring(0,j.length() - 1);
                }



                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this); // 'this' is the Context
                //Toast.makeText(MainActivity.this, j,Toast.LENGTH_LONG).show();
                String url = "http://wenshuay-env.us-east-2.elasticbeanstalk.com/?data=fresh&" + j;
                //String url = "http://wenshuay-env.us-east-2.elasticbeanstalk.com/?data=price&symbol=FB";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                //Toast.makeText(MainActivity.this, response.toString().length() +"",Toast.LENGTH_LONG).show();
                                favalistprogress.setVisibility(View.GONE);


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                        /* TODO Auto-generated method stub */
                                //Toast.makeText(MainActivity.this, "wring",Toast.LENGTH_LONG).show();
                                favalistprogress.setVisibility(View.GONE);
                            }
                        });
                requestQueue.add(jsObjRequest);


                /*
                
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Toast.makeText(MainActivity.this, response.toString().length(),Toast.LENGTH_LONG).show();
                                favalistprogress.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "wring",Toast.LENGTH_LONG).show();
                                favalistprogress.setVisibility(View.GONE);

                            }
                        });
          */


                automaitchandler.postDelayed(autorunnable,10000);
            }
        };


        automatic = (Switch) findViewById(R.id.automatic);
        automatic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    automaitchandler.postDelayed(autorunnable,5000);
                } else {
                    automaitchandler.removeCallbacks(autorunnable);
                }
            }
        });




        sortby = (Spinner) findViewById(R.id.sortby);

        String[] sortvalu = new String[]{"Defalut", "Symbol", "Price", "Change","Change(%)"};
        ArrayAdapter<String> sortbyadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortvalu);
        sortbyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortby.setPrompt("Sort by");
        sortby.setAdapter(sortbyadapter);
        sortby.setOnItemSelectedListener(this);

        order = (Spinner) findViewById(R.id.order);
        order.setPrompt("Order");
        String[] ordervalue = new String[]{"Ascending", "Descending"};
        ArrayAdapter<String> orderadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ordervalue);
        order.setAdapter(orderadapter);
        order.setOnItemSelectedListener(this);

        favlistview = (ListView) findViewById(R.id.favalistview);
        favalistdata = new ArrayList<>();


        SharedPreferences sharedPreferences = this.getSharedPreferences("local",MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        alllocaldata = sharedPreferences.getAll();

        Gson gson = new Gson();

      for (String key : alllocaldata.keySet()) {

            String value = (String) alllocaldata.get(key);
            FavItem cur = gson.fromJson(value,FavItem.class);
            Map<String,Object> map = new HashMap<>();
            map.put("symbol", cur.symbol);
            map.put("lastprice", cur.lastprice);
            map.put("change", cur.change + "(" + cur.per + ")");
            favalistdata.add(map);
        }




        simpleAdapter = new SimpleAdapter(this, favalistdata,R.layout.favlistitem, new String[]{"symbol", "lastprice", "change"}, new int[]{R.id.symbol, R.id.lastprice, R.id.change});
        favlistview.setAdapter(simpleAdapter);

        registerForContextMenu(favlistview);



        favlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String clicksymbol = ((TextView)view.findViewById(R.id.symbol)).getText().toString();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                intent.putExtra("extra_data", clicksymbol);
                startActivity(intent);

            }
        });



    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        String neirong = auto.getText().toString();
        if (neirong == null || neirong.length() == 0 || neirong.trim().length() == 0) {
            Toast.makeText(this, "Please enter a stock name or symbol",Toast.LENGTH_LONG).show();
            return;
        }

        int index = neirong.indexOf("-");
        String data = neirong;
        if (index != -1){
            data = neirong.substring(0, index);
        }

        intent.putExtra("extra_data", data);
        startActivity(intent);
    }

    public void clear(View view) {
        auto.setText("");
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

       sortbysele= sortby.getSelectedItem().toString();
       ordersele =  order.getSelectedItem().toString();
       if (sortbysele.equals("Defalut")) {
           return;
       }


       ArrayList<FavItem> sortarry = new ArrayList<>();

        Gson gson = new Gson();
        for (String key : alllocaldata.keySet()) {

            String value = (String) alllocaldata.get(key);
            FavItem cur = gson.fromJson(value,FavItem.class);
            sortarry.add(cur);

        }


        if (ordersele.equals("Ascending")) {

            Collections.sort(sortarry, new Comparator<FavItem>() {
                @Override
                public int compare(FavItem f1, FavItem f2) {
                    if (sortbysele.equals("Symbol")) {
                        return  f1.symbol.compareTo(f2.symbol);
                    } else if (sortbysele.equals("Price")) {
                        double res = f1.lastprice - f2.lastprice;
                        if (res == 0) {
                            return 0;
                        } else if (res > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                    double res = f1.change - f2.change;
                    if (res == 0) {
                        return 0;
                    } else if (res > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });



        } else {

            Collections.sort(sortarry, new Comparator<FavItem>() {
                @Override
                public int compare(FavItem f1, FavItem f2) {
                    if (sortbysele.equals("Symbol")) {
                        return f2.symbol.compareTo(f1.symbol);
                    } else if (sortbysele.equals("Price")) {

                        double res = f2.lastprice - f1.lastprice;
                        if (res == 0) {
                            return 0;
                        } else if (res > 0) {
                            return 1;
                        } else {
                            return -1;
                        }

                    }
                    double res = f2.change - f1.change;
                    if (res == 0) {
                        return 0;
                    } else if (res > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }

            favalistdata = new ArrayList<>();
            for (FavItem cur : sortarry) {


                Map<String,Object> map = new HashMap<>();
                map.put("symbol", cur.symbol);
                map.put("lastprice", cur.lastprice);
                map.put("change", cur.change + "(" + cur.per + ")");
                favalistdata.add(map);
            }




            simpleAdapter = new SimpleAdapter(this, favalistdata,R.layout.favlistitem, new String[]{"symbol", "lastprice", "change"}, new int[]{R.id.symbol, R.id.lastprice, R.id.change});
            favlistview.setAdapter(null);
            favlistview.setAdapter(simpleAdapter);
            favlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String clicksymbol = ((TextView)view.findViewById(R.id.symbol)).getText().toString();
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                    intent.putExtra("extra_data", clicksymbol);
                    startActivity(intent);

                }
            });





      /*  Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.sortby){
            //do this
           Toast.makeText(this, sortby.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
            //sortby.getSelectedItem().toString();
            //Toast.makeText(this, order.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
        }
        else if(spinner.getId() == R.id.order){
            Toast.makeText(this, sortby.getSelectedItem().toString() + "2",Toast.LENGTH_LONG).show();
            //sortby.getSelectedItem().toString();
            //Toast.makeText(this, order.getSelectedItem().toString() + "2",Toast.LENGTH_LONG).show();
             //order.getSelectedItem().toString();
        }
        */
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Remove from Favorites");
        menu.add(0, v.getId(), 1, "No");//groupId, itemId, order, title
        menu.add(0, v.getId(), 2, "Yes");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Yes"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //String key = ((TextView) info.targetView).getText().toString();
            View view = info.targetView.findViewById(R.id.symbol);
            TextView view1 = (TextView) view;
            String key1 = view1.getText().toString();


            //Toast.makeText(this,key1,Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences = this.getSharedPreferences("local",MODE_PRIVATE );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key1);
            editor.commit();

            alllocaldata = sharedPreferences.getAll();
            sortbysele= sortby.getSelectedItem().toString();
            ordersele =  order.getSelectedItem().toString();



            ArrayList<FavItem> sortarry = new ArrayList<>();

            Gson gson = new Gson();
            for (String key : alllocaldata.keySet()) {

                String value = (String) alllocaldata.get(key);
                FavItem cur = gson.fromJson(value,FavItem.class);
                sortarry.add(cur);

            }


            if (ordersele.equals("Ascending")) {

                Collections.sort(sortarry, new Comparator<FavItem>() {
                    @Override
                    public int compare(FavItem f1, FavItem f2) {
                        if (sortbysele.equals("Symbol")) {
                            return  f1.symbol.compareTo(f2.symbol);
                        } else if (sortbysele.equals("Price")) {
                            double res = f1.lastprice - f2.lastprice;
                            if (res == 0) {
                                return 0;
                            } else if (res > 0) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                        double res = f1.change - f2.change;
                        if (res == 0) {
                            return 0;
                        } else if (res > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });



            } else {

                Collections.sort(sortarry, new Comparator<FavItem>() {
                    @Override
                    public int compare(FavItem f1, FavItem f2) {
                        if (sortbysele.equals("Symbol")) {
                            return f2.symbol.compareTo(f1.symbol);
                        } else if (sortbysele.equals("Price")) {

                            double res = f2.lastprice - f1.lastprice;
                            if (res == 0) {
                                return 0;
                            } else if (res > 0) {
                                return 1;
                            } else {
                                return -1;
                            }

                        }
                        double res = f2.change - f1.change;
                        if (res == 0) {
                            return 0;
                        } else if (res > 0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
            }

            favalistdata = new ArrayList<>();
            for (FavItem cur : sortarry) {


                Map<String,Object> map = new HashMap<>();
                map.put("symbol", cur.symbol);
                map.put("lastprice", cur.lastprice);
                map.put("change", cur.change + "(" + cur.per + ")");
                favalistdata.add(map);
            }




            simpleAdapter = new SimpleAdapter(this, favalistdata,R.layout.favlistitem, new String[]{"symbol", "lastprice", "change"}, new int[]{R.id.symbol, R.id.lastprice, R.id.change});
            favlistview.setAdapter(null);
            favlistview.setAdapter(simpleAdapter);
            favlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String clicksymbol = ((TextView)view.findViewById(R.id.symbol)).getText().toString();
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                    intent.putExtra("extra_data", clicksymbol);
                    startActivity(intent);

                }
            });

            Toast.makeText(this,"Selete Yes",Toast.LENGTH_LONG).show();


        }
        else if(item.getTitle()=="No"){
            Toast.makeText(this,"Selete NO",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }

}
