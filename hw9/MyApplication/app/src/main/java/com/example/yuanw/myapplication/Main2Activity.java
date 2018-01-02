package com.example.yuanw.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity {

    currentFragment cf;
    HistoricalFragment hf;
    NewsFragment nf;
    RadioGroup radiogrop;
    private RequestQueue requestQueue;
    private TextView m;
    int  hfcount = 0;
    int nfcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbartext = findViewById(R.id.toobarsymbol);


        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        toolbartext.setText(data);

        Bundle bundle = new Bundle();
        bundle.putString("symbol", data);


        cf = new currentFragment();
        cf.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, cf).commit();


        hf = new HistoricalFragment();
        hf.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container, hf).commit();
        getSupportFragmentManager().beginTransaction().hide(hf).commit();


      /*  Object[] objects = new Object[2];
        objects[0] = bundle;
        objects[1] = this;

        try {
            hf = (HistoricalFragment) new HistoryWebview().execute(objects).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
*/

        nf = new NewsFragment();
        nf.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, nf).commit();
        getSupportFragmentManager().beginTransaction().hide(nf).commit();



        radiogrop =(RadioGroup) findViewById(R.id.rg_group);
        radiogrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.curr) {
                    getSupportFragmentManager().beginTransaction().show(cf).commit();
                    getSupportFragmentManager().beginTransaction().hide(nf).commit();
                    getSupportFragmentManager().beginTransaction().hide(hf).commit();
                   //getSupportFragmentManager().beginTransaction().replace(R.id.container, cf).commit();


                } else if(checkedId==R.id.hist){

                    getSupportFragmentManager().beginTransaction().show(hf).commit();
                    getSupportFragmentManager().beginTransaction().hide(nf).commit();
                    getSupportFragmentManager().beginTransaction().hide(cf).commit();
                   /* if (hfcount == 0) {
                        getSupportFragmentManager().beginTransaction().add(R.id.container, hf).commit();
                        hfcount++;
                    }
                  */


                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, hf).commit();
                } else {

                    getSupportFragmentManager().beginTransaction().show(nf).commit();
                    getSupportFragmentManager().beginTransaction().hide(cf).commit();
                    getSupportFragmentManager().beginTransaction().hide(hf).commit();
                   /* if (nfcount == 0) {
                        getSupportFragmentManager().beginTransaction().add(R.id.container, nf).commit();
                        nfcount++;
                    }
                  */

                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, nf).commit();
                }

            }
        });

    }

    public void back (View view) {
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);


        startActivity(intent);
    }








}

