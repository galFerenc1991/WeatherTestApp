package com.example.weathertestapp.presentation.screens.saved_locations.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertestapp.R;


public class LocationListViewHolder extends RecyclerView.ViewHolder {

    private CardView cvRootContainer;

    public TextView tvCityName;

    public LocationListViewHolder(View _view) {
        super(_view);
        cvRootContainer = _view.findViewById(R.id.cvContainer_MLI);
        tvCityName = _view.findViewById(R.id.tvCityName);
    }

    public void setListeners(OnCardClickListener listener) {
        cvRootContainer.setOnClickListener(view -> listener.onClick(itemView, getAdapterPosition(), getItemViewType()));
    }
}
