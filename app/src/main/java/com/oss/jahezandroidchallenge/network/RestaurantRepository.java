package com.oss.jahezandroidchallenge.network;

import androidx.lifecycle.MutableLiveData;

import com.oss.jahezandroidchallenge.model.RestaurantModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {

    private static RestaurantRepository mRepository;

    public static RestaurantRepository getInstance() {
        if (mRepository == null) {
            mRepository = new RestaurantRepository();
        }
        return mRepository;
    }


    private RestaurantRepository() {
    }

    public MutableLiveData<List<RestaurantModel>> getRestaurantList() {

        final MutableLiveData<List<RestaurantModel>> mutableLiveData = new MutableLiveData<>();

        JsonPlaceHolderApi mJsonPlaceHolderApi = CustomerNetworkAPIManager.getInstance().retrofit.create(JsonPlaceHolderApi.class);
        mJsonPlaceHolderApi.getRestaurantList().enqueue(new Callback<List<RestaurantModel>>() {
            @Override
            public void onResponse(Call<List<RestaurantModel>> call, Response<List<RestaurantModel>> response) {
                if (response.isSuccessful())
                    mutableLiveData.setValue(response.body());
                else
                    mutableLiveData.setValue(null);
            }

            @Override
            public void onFailure(Call<List<RestaurantModel>> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }


}
