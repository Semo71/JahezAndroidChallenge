package com.oss.jahezandroidchallenge.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oss.jahezandroidchallenge.R;
import com.oss.jahezandroidchallenge.adapter.RestaurantAdapter;
import com.oss.jahezandroidchallenge.databinding.ActivityMainBinding;
import com.oss.jahezandroidchallenge.managers.BaseActivity;
import com.oss.jahezandroidchallenge.managers.CustomerLocalizationManager;
import com.oss.jahezandroidchallenge.managers.LocalizationManager;
import com.oss.jahezandroidchallenge.model.RestaurantModel;
import com.oss.jahezandroidchallenge.viewmodel.MainViewModel;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends BaseActivity {

    private Context mContext;
    private ActivityMainBinding mBinding;
    private MainViewModel mViewModel;
    private ProgressDialog mProgressDialog;
    private List<RestaurantModel> mRestaurantModels;
    private boolean isFilterApplied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mContext = MainActivity.this;
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mProgressDialog = new ProgressDialog(mContext);

        mBinding.toolbarInclude.titleTxtV.setText(R.string.home);
        mBinding.toolbarInclude.languageImgV.setVisibility(View.VISIBLE);
        mBinding.toolbarInclude.languageImgV.setOnClickListener(v -> {
            if (LocalizationManager.getInstance().isLanguageEnglish())
                CustomerLocalizationManager.getInstance().setLanguage(LocalizationManager.ARABIC);
            else
                CustomerLocalizationManager.getInstance().setLanguage(LocalizationManager.ENGLISH);
        });

        mBinding.toolbarInclude.filterImgV.setVisibility(View.VISIBLE);
        mBinding.toolbarInclude.filterImgV.setOnClickListener(v -> {
            isFilterApplied = !isFilterApplied;
            if (isFilterApplied) {
                mBinding.toolbarInclude.filterImgV.setImageResource(R.drawable.ic_filter_accent_24dp);
                List<RestaurantModel> sortedByDistance = mRestaurantModels.stream().sorted(Comparator.comparing(RestaurantModel::getDistance)).collect(Collectors.toList());
                updateAdapter(sortedByDistance);
            } else {
                mBinding.toolbarInclude.filterImgV.setImageResource(R.drawable.ic_filter_gray_24dp);
                updateAdapter(mRestaurantModels);
            }
        });

        getRestaurantList();
    }

    public void getRestaurantList() {
        mProgressDialog.show();
        mViewModel.getRestaurantList().observe(this, restaurantModels -> {
            mProgressDialog.cancel();
            mRestaurantModels = restaurantModels;
            if (mRestaurantModels == null) {
                Toast.makeText(mContext, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                return;
            }

            if (mRestaurantModels.isEmpty())
                Toast.makeText(mContext, R.string.no_data_were_found, Toast.LENGTH_SHORT).show();

            updateAdapter(mRestaurantModels);
        });

    }

    private void updateAdapter(List<RestaurantModel> restaurantModels) {
        RestaurantAdapter adapter = new RestaurantAdapter(restaurantModels);
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
    }

}