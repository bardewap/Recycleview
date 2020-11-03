package com.example.task.models;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("Title")
    private  String Title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private int Poster;



    public Movie(String movieName, String year, int poster) {
        this.Title = movieName;
        this.year = year;
        this.Poster = poster;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getYear() {
        return year;
    }

    public int getPoster() {
        return Poster;
    }

    public void setPoster(int poster) {
        Poster = poster;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
