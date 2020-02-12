package com.drraisingh.narmm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drraisingh.narmm.Model.OrderHistoryModel.OrderHistory_modelData_Item;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_model;
import com.drraisingh.narmm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 12-Jan-20.
 */
public class Show_item_adapter extends RecyclerView.Adapter<Show_item_adapter.MyViewHolder> {

    private List<Cartlist_model> modelList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_item, tv_quantity_bill, tv_total,tv_price;

        public MyViewHolder(View view) {
            super(view);
            tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_quantity_bill = (TextView) view.findViewById(R.id.tv_quantity_bill);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_total = (TextView) view.findViewById(R.id.tv_total);

        }
    }

    public Show_item_adapter(ArrayList<Cartlist_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_item_food, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cartlist_model mList = modelList.get(position);

        holder.tv_item.setText(mList.getProduct_name());
        holder.tv_price.setText(mList.getUnit_price());
        holder.tv_total.setText(mList.getPrice());
        holder.tv_quantity_bill.setText(mList.getQty());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
