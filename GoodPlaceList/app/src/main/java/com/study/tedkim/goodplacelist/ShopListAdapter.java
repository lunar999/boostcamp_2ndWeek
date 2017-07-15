package com.study.tedkim.goodplacelist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by tedkim on 2017. 7. 11..
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder> {

    Context mContext;
    ArrayList<ShopInfo> mDataset = new ArrayList<>();

    LayoutInflater mInflater;

    public ShopListAdapter(Context context, ArrayList<ShopInfo> dataset) {

        mContext = context;
        mDataset = dataset;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public class ShopListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivShopImage;
        TextView tvShopName, tvShopContents;
        CheckBox cbChoose;
        CardView cvItem;

        // view holder constructor
        public ShopListViewHolder(View itemView) {
            super(itemView);

            ivShopImage = (ImageView) itemView.findViewById(R.id.imageView_shopImage);  // 매장 전경

            tvShopName = (TextView) itemView.findViewById(R.id.textView_shopName);  // 매장 이름
            tvShopContents = (TextView) itemView.findViewById(R.id.textView_shopContents);  // 매장 평가

            cbChoose = (CheckBox) itemView.findViewById(R.id.checkBox_choose);  // 체크 박스

            cvItem = (CardView) itemView.findViewById(R.id.cardView_item);  // 카드뷰 item
        }

    }

    // create viewHolder
    @Override
    public ShopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.item_shop, parent, false);
        return new ShopListViewHolder(itemView);
    }

    // dataset binding to viewHolder
    @Override
    public void onBindViewHolder(final ShopListViewHolder holder, final int position) {

        // Glide 로 매장 전경 load
        Glide.with(mContext).load(mDataset.get(position).getShopImage()).into(holder.ivShopImage);

        // text contents init
        holder.tvShopName.setText(mDataset.get(position).getName());
        holder.tvShopContents.setText(mDataset.get(position).getContents());

        // init check button
        // 이전 사용자의 check 여부에 따라 서로 다른 image set 사용
        if (!mDataset.get(position).isChecked()) {
            holder.cbChoose.setButtonDrawable(R.drawable.img_check_off);
        } else {
            holder.cbChoose.setButtonDrawable(R.drawable.img_check_on);
        }

        // define check button click action
        // 체크 여부를 dataset 에 전달
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mDataset.get(position).isChecked()) {
                    holder.cbChoose.setButtonDrawable(R.drawable.img_check_on);
                    mDataset.get(position).setChecked(true);

                } else {
                    holder.cbChoose.setButtonDrawable(R.drawable.img_check_off);
                    mDataset.get(position).setChecked(false);
                }
            }
        });

        // CardView 생성 animation 정의
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        holder.cvItem.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
