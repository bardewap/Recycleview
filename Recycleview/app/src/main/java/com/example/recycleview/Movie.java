package com.example.recycleview;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("Title")
    private  String Title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private String Poster;



    public Movie(String movieName, String year, String poster) {
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

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
