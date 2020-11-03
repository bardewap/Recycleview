package com.example.task.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.task.Common.Adapters.MovieAdapter;
import com.example.task.Common.interfaces.ApiInterface;
import com.example.task.Common.ApiClient;
import com.example.task.models.Movie;
import com.example.task.R;
import com.example.task.ViewModel.MainViewModel;
import com.example.task.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    MovieAdapter movieAdapter;
    List<Movie> movieList = new ArrayList<>();
    MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.setTitle("Movies & Shows");
        mainViewModel = new MainViewModel(getApplicationContext());
        loadApiData();
        movieList = new ArrayList<>();
        movieList.add(new Movie("3 Idiots", "2010", R.drawable.ic_launcher_background));
        movieList.add(new Movie("Titanic  ", "1997", R.drawable.image));
        movieList.add(new Movie("Rockstar","2008", R.drawable.ic_launcher_foreground));
        movieList.add(new Movie("Mirzapur ", "2020", R.drawable.no_image));
        mainViewModel.setMovie(movieList);

        movieAdapter = new MovieAdapter(mainViewModel.getMovie(), getApplicationContext());
        activityMainBinding.linearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                activityMainBinding.recycleView.setLayoutManager(mLayoutManager);
                activityMainBinding.recycleView.setItemAnimator(new DefaultItemAnimator());
                activityMainBinding.recycleView.setAdapter(movieAdapter);

            }
        });

        activityMainBinding.gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 3);
                activityMainBinding.recycleView.setLayoutManager(mLayoutManager);
                activityMainBinding.recycleView.setItemAnimator(new DefaultItemAnimator());
                activityMainBinding.recycleView.setAdapter(movieAdapter);

            }
        });

    }

    private void loadApiData() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Movie>> call = apiService.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieList = response.body();
                Log.d("TAG","Response = "+movieList);

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
}