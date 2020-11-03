package com.example.recycleview;

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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Movie> movieList = new ArrayList<>();
    UserAdapter userAdapter;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Movies & Shows");
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        queue = Volley.newRequestQueue(MainActivity.this);

        loadData();

//        movieList = new ArrayList<>();
//        movieList.add(new Movie("Pradeep Bardewa", "pb@gmail.com",R.drawable.image));
//        movieList.add(new Movie("Sunil Singh ", "ss@gmail.com", R.drawable.ic_launcher_background));
//        movieList.add(new Movie("Sandeep Bardewa ", "sb@gmail.com", R.drawable.image));
//        movieList.add(new Movie("Jitupan Nath ", "jn@gmail.com", R.drawable.ic_launcher_foreground));



    }

    private void loadData() {
        final String url = "http://www.omdbapi.com/?i=tt3896198&apikey=8160992d";

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String title= response.getString("Title");
                            String year= response.getString("Year");
                            String poster= (response.getString("Poster"));

                            userAdapter = new UserAdapter(movieList, getApplicationContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(userAdapter);


                            movieList.add(new Movie(title,year,poster));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // display response
                        Log.d("AVDOVDVOVAO", response.toString());


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