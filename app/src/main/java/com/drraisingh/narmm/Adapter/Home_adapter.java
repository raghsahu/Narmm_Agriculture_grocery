package com.drraisingh.narmm.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Fragment.ItemDetailsFragment.ProductDetailsFragment;
import com.drraisingh.narmm.Model.CategoryProductModel.CategoryModelDataProduct;
import com.drraisingh.narmm.Model.Category_model;
import com.drraisingh.narmm.R;

/**
 * Created by Rajesh Dabhi on 22/6/2017.
 */

public class Home_adapter extends RecyclerView.Adapter<Home_adapter.MyViewHolder> {

    private List<CategoryModelDataProduct> modelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        LinearLayout layout_item;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_home_title);
            image = (ImageView) view.findViewById(R.id.iv_home_img);
            layout_item =  view.findViewById(R.id.layout_item);
        }
    }

    public Home_adapter(ArrayList<CategoryModelDataProduct> modelList, Context context) {
        this.modelList = modelList;
    }

    @Override
    public Home_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_home_rv, parent, false);

        context = parent.getContext();

        return new Home_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Home_adapter.MyViewHolder holder, int position) {
        final CategoryModelDataProduct mList = modelList.get(position);

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + mList.getProductImage())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.image);

        holder.title.setText(mList.getProductName());



        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                Fragment fm = new ProductDetailsFragment();
                args.putString("product_id", mList.getProductId());
                args.putString("product_name", mList.getProductName());
                args.putString("product_tech_name", mList.getTechnicalName());
                args.putString("product_img", BaseURL.IMG_PRODUCT_URL + mList.getProductImage());
                fm.setArguments(args);
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack("Home_fragment")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}

