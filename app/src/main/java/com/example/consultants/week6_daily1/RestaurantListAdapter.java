package com.example.consultants.week6_daily1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ContactViewHolder>{

    private List<RestaurantInfo> restaurantInfoList;
    private Context context;
    private FragmentManager fm;
    private static final String TAG = RestaurantListAdapter.class.getSimpleName() + "_TAG";

    public RestaurantListAdapter(List<RestaurantInfo> restaurantInfoList, Context context, FragmentManager fm) {
        this.restaurantInfoList = restaurantInfoList;
        this.context = context;
        this.fm = fm;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.single_restaurant_view, null);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
