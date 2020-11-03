package com.example.task.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.task.models.Movie;

import java.util.List;

public class MainViewModel extends ViewModel {


    private Context context;
    private List<Movie> movie;

    public  MainViewModel(Context context){
        this.context= context;
    }

    public List<Movie> getMovie() {
        return movie;
    }

    public void setMovie(List<Movie> movie) {
        this.movie = movie;
    }



}
