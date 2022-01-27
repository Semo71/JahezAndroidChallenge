package com.oss.jahezandroidchallenge.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.oss.jahezandroidchallenge.model.RestaurantModel;
import com.oss.jahezandroidchallenge.network.RestaurantRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    public LiveData<List<RestaurantModel>> getRestaurantList() {
        return RestaurantRepository.getInstance().getRestaurantList();
    }

}
