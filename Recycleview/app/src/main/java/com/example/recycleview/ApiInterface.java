package com.example.recycleview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiInterface {

    @GET
    Call<MoviesResponse> getMovieDetails(@Query("i") int id, @Query("apiKey") String apiKey) {
        http://www.omdbapi.com/?i=tt3896198&apikey=8160992d
        return null;
    }

}