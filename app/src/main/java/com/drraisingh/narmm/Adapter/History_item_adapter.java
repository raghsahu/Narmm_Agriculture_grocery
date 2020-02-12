package com.drraisingh.narmm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drraisingh.narmm.Model.OrderHistoryModel.OrderHistory_modelData_Item;
import com.drraisingh.narmm.R;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 03-Jan-20.
 */
public class History_item_adapter extends RecyclerView.Adapter<History_item_adapter.MyViewHolder> {

    private List<OrderHistory_modelData_Item> modelList;

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

    public History_item_adapter(List<OrderHistory_modelData_Item> modelList) {
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
        OrderHistory_modelData_Item mList = modelList.get(position);

        holder.tv_item.setText(mList.getProductName());
        holder.tv_price.setText(mList.getUnit_price());
        holder.tv_total.setText(mList.getPrice());
        holder.tv_quantity_bill.setText(mList.getQty());



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
