
package com.oss.jahezandroidchallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oss.jahezandroidchallenge.databinding.ItemRestaurantBinding;
import com.oss.jahezandroidchallenge.model.RestaurantModel;

import java.util.List;
import java.util.Locale;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private Context mContext;
    private List<RestaurantModel> mData;

    public RestaurantAdapter(List<RestaurantModel> mData) {
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        ItemRestaurantBinding binding = ItemRestaurantBinding.inflate(LayoutInflater.from(mContext), viewGroup, false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        RestaurantModel model = mData.get(position);

        Glide.with(mContext).load(model.getImage()).into(holder.mBinding.restaurantImgV);

        holder.mBinding.titleTxtV.setText(model.getName());
        holder.mBinding.distanceTxtV.setText(String.format(Locale.ENGLISH, "%.1f", model.getDistance()));
        holder.mBinding.descriptionTxtV.setText(model.getDescription());
        if (model.getHasOffer())
            holder.mBinding.offerTxtV.setVisibility(View.VISIBLE);
        else
            holder.mBinding.offerTxtV.setVisibility(View.GONE);

        holder.mBinding.hoursTxtV.setText(model.getHours());

        holder.mBinding.ratingBar.setEnabled(false);
        holder.mBinding.ratingBar.setMax(5);
        holder.mBinding.ratingBar.setStepSize(0.01f);
        double ratingOutOf5 = model.getRating() / 2;
        holder.mBinding.ratingBar.setRating(Float.parseFloat(String.valueOf(ratingOutOf5)));
        holder.mBinding.ratingBar.invalidate();
    }


    @Override
    public int getItemCount() {
        try {
            return mData.size();
        } catch (Exception exp) {
            return 0;
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRestaurantBinding mBinding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = ItemRestaurantBinding.bind(itemView);
        }
    }

}

