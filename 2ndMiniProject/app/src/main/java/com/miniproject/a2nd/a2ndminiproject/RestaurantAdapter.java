package com.miniproject.a2nd.a2ndminiproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miniproject.a2nd.a2ndminiproject.data.Restaurant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jh on 17. 7. 10.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private Context context;
    private ArrayList<Restaurant> restaurants;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        final Restaurant item = restaurants.get(position);

        Glide.with(context).load(item.getImageId()).into(holder.imageView);
        holder.nameView.setText(item.getName());
        holder.checkBox.setChecked(item.isChecked());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setChecked(!item.isChecked());
            }
        });
        holder.contentView.setText(item.getContent()
                +"\n"+context.getString(R.string.label_distance)+item.getDistance()
                +"\n"+context.getString(R.string.label_rank)+item.getRank()
                +"\n"+context.getString(R.string.label_time)+new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss").format(item.getTime()));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_item_image) ImageView imageView;
        @BindView(R.id.restaurant_item_name) TextView nameView;
        @BindView(R.id.restaurant_item_checkbox) CheckBox checkBox;
        @BindView(R.id.restaurant_item_content) TextView contentView;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
