package com.example.yuanw.myapplication;

import android.os.AsyncTask;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yuanw on 2017/11/28.
 */

public class GetResult extends AsyncTask {
    @Override
    protected ArrayList<String> doInBackground(Object[] objects) {
        ArrayList<String> data = new ArrayList<>();
        String input = (String) objects[0];
        try {
            URL url = new URL("http://dev.markitondemand.com/MODApis/Api/v2/Lookup/json?input=" + input);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder s = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                s.append(line);
            }

            String json = new String(s);
            JSONArray response = new JSONArray(json);

            for (int i = 0; i <= response.length() - 1 && i <= 4; i++) {

                    JSONObject each = response.getJSONObject(i);
                    String symbol = each.getString("Symbol");
                    String name = each.getString("Name");
                    String exchange = each.getString("Exchange");
                    String res = symbol + "-" + name + "(" + exchange + ")";

                    data.add(res);


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return data;
    }
}
