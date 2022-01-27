package com.oss.jahezandroidchallenge.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantModel {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("hours")
    private String hours;
    @SerializedName("image")
    private String image;
    @SerializedName("rating")
    private Double rating;
    @SerializedName("distance")
    private Double distance;
    @SerializedName("hasOffer")
    private Boolean hasOffer;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHours() {
        return hours;
    }

    public String getImage() {
        return image;
    }

    public Double getRating() {
        return rating;
    }

    public Double getDistance() {
        return distance;
    }

    public Boolean getHasOffer() {
        return hasOffer;
    }
}
