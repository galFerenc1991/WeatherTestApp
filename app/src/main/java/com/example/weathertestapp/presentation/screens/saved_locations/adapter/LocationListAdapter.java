package com.example.weathertestapp.presentation.screens.saved_locations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertestapp.R;
import com.example.weathertestapp.data.database.SavedLocation;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListViewHolder> {

    private OnCardClickListener mOnCardClickListener;

    private List<SavedLocation> mLocationList = new ArrayList<>();

    @Inject
    public LocationListAdapter() {
    }

    @NonNull
    @Override
    public LocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        LocationListViewHolder locationListViewHolder = new LocationListViewHolder(view);
        if (mOnCardClickListener != null) {
            locationListViewHolder.setListeners(mOnCardClickListener);
        }
        return locationListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListViewHolder holder, int position) {
        SavedLocation currentLocation = mLocationList.get(position);
        holder.tvCityName.setText(currentLocation.getCityName());

    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

    public void setLocations(List<SavedLocation> _list) {
        mLocationList.clear();
        mLocationList.addAll(_list);
        notifyDataSetChanged();
    }

    public void addMovieListDH(List<SavedLocation> _list) {
        int oldSize = mLocationList.size();
        mLocationList.addAll(_list);
        notifyItemRangeInserted(oldSize, mLocationList.size());
    }

    public SavedLocation getItem(int _position) {
        if (0 <= _position && _position < mLocationList.size()) {
            return mLocationList.get(_position);
        } else {
            return null;
        }
    }

    public void setOnCardClickListener(OnCardClickListener _onCardClickListener) {
        this.mOnCardClickListener = _onCardClickListener;
    }
}