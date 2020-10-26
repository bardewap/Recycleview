package com.example.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RequestQueue queue;
    StoreAdapter storeAdapter;
    List<StoreDataModel> storeDataModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= (findViewById(R.id.recycle_view));
        queue = Volley.newRequestQueue(MainActivity.this);


        loadData();
        
    }


    private void loadData() {
        final String url = "http://plobalapps.s3-ap-southeast-1.amazonaws.com/dummy-app-data.json";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Log.d("OOSDOBODBSD", "onResponse: "+response.toString());

                        try {
                            JSONArray jsonArray= response.getJSONArray("apps");
                            Log.d("OOSDOBODBSD", "onResponse: "+jsonArray);

                            for(int i= 0;i<jsonArray.length();i++){

                                JSONObject jsonObject= jsonArray.getJSONObject(i);

                                String name= jsonObject.getString("name");
                                String currency= jsonObject.getString("currency");
                                String money_format= jsonObject.getString("money_format");

                                Log.d("JSBASABAS", "onResponse: "+name);

                                storeAdapter = new StoreAdapter(storeDataModelList, getApplicationContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(storeAdapter);


                                storeDataModelList.add(new StoreDataModel(name,currency,money_format));


                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("SBDPSPDBD", "onErrorResponse: " + error);
                    }

                }

        );



// add it to the RequestQueue
        queue.add(getRequest);


    }

}