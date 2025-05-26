package com.example.movieapp.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("items")
    @Expose
    private List<Movie> items;

    public List<Movie> getItems() {
        return items;
    }
}
