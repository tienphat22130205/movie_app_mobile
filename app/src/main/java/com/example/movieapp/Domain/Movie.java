package com.example.movieapp.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("thumb_url")
    @Expose
    private String thumbUrl;

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getFullThumbUrl() {
        return "http://img.ophim1.com/uploads/movies/" + thumbUrl;
    }
}
