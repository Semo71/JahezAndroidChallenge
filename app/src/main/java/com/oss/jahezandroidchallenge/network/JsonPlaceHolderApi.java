package com.oss.jahezandroidchallenge.network;

import com.oss.jahezandroidchallenge.model.RestaurantModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface JsonPlaceHolderApi {

    @GET("restaurants.json")
    Call<List<RestaurantModel>> getRestaurantList();

}


