package com.project2.mini.yunkyun.miniproject2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by YunKyun on 2017-07-11.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public static final int SORT_BY_DISTANCE = 0;
    public static final int SORT_BY_POPULARITY = 1;
    public static final int SORT_BY_RECENT = 2;

    private ArrayList<StoreItem> itemList;
    private Context context;

    public RecyclerAdapter(Context context) {
        this.itemList = new ArrayList<>();
        this.context = context;
    }

    public void setItemList(ArrayList<StoreItem> items) {
        itemList.clear();
        itemList.addAll(items);
    }

    public void sortItemList(int option) {
        switch(option){
            case SORT_BY_DISTANCE:
                Collections.sort(itemList, new Comparator<StoreItem>() {
                    @Override
                    public int compare(StoreItem o1, StoreItem o2) {
                        return Double.compare(o1.getDistance(), o2.getDistance());
                    }
                });
                break;
            case SORT_BY_POPULARITY:
                Collections.sort(itemList, new Comparator<StoreItem>() {
                    @Override
                    public int compare(StoreItem o1, StoreItem o2) {
                        return o2.getPopularity() - o1.getPopularity();
                    }
                });
                break;
            case SORT_BY_RECENT:
                Collections.sort(itemList, new Comparator<StoreItem>() {
                    @Override
                    public int compare(StoreItem o1, StoreItem o2) {
                        return Long.compare(o2.getDate(), o1.getDate());
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final StoreItem item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        Glide.with(context).load(item.getImageFile()).into(holder.image);
        holder.checkBox.setChecked(item.isChecked());
        holder.checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                item.setChecked(!item.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView description;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_store_image);
            name = itemView.findViewById(R.id.tv_store_item_name);
            description = itemView.findViewById(R.id.tv_store_item_desc);
            checkBox = itemView.findViewById(R.id.checkBox_store_item);
        }
    }
}
