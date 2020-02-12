package com.drraisingh.narmm.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.drraisingh.narmm.Fragment.UseFragmnet.ViewPdfFragment;
import com.drraisingh.narmm.Model.OrderHistoryModel.OrderHistory_modelData;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.PaymentActivity;

import java.util.List;

import static com.drraisingh.narmm.Config.BaseURL.GET_order_bill;

/**
 * Created by Raghvendra Sahu on 03-Jan-20.
 */
public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private List<OrderHistory_modelData> modelList;

    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_order_id, tv_order_date, order_status,payment_status,
                tv_shop_address,tv_happy_code,tv_total_rs,tv_total_item,btn_bill;
        RecyclerView recycler_view_bill;
        Button btn_confirm;

        public MyViewHolder(View view) {
            super(view);
            tv_order_id = (TextView) view.findViewById(R.id.tv_order_id);
            tv_order_date = (TextView) view.findViewById(R.id.tv_order_date);
            order_status = (TextView) view.findViewById(R.id.order_status);
            payment_status = (TextView) view.findViewById(R.id.payment_status);
            recycler_view_bill = view.findViewById(R.id.recycler_view_bill);
            btn_confirm = view.findViewById(R.id.btn_pay_now);
            tv_happy_code = view.findViewById(R.id.tv_happy_code);
            tv_total_rs = view.findViewById(R.id.tv_total_rs);
            tv_shop_address = view.findViewById(R.id.tv_shop_address);
            tv_total_item = view.findViewById(R.id.tv_total_item);
            btn_bill = view.findViewById(R.id.btn_bill);

        }
    }

    public OrderHistoryAdapter(List<OrderHistory_modelData> modelList) {
        this.modelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        OrderHistory_modelData mList = modelList.get(position);

        holder.tv_order_id.setText(mList.getOrderId());
        holder.order_status.setText(mList.getOrderStatus());
        holder.tv_happy_code.setText(mList.getHappyCode());
        holder.tv_order_date.setText(mList.getOrder_date());

        if (mList.getShopDetail()!=null){

            String address = "Shop address: "
                    + mList.getShopDetail().getPrapriterName()+
                    // "\n" + context.getResources().getString(R.string.reciver_mobile) + getphone +
                    " , " + "Shop name: "
                    + mList.getShopDetail().getShopName()+
                    " , " + "Distt. : "+ mList.getShopDetail().getDistrict() +
                    " , " + "Block : " + mList.getShopDetail().getBlock()+
                    " , " + "Place market : " + mList.getShopDetail().getPlaceMarket() ;

            holder.tv_shop_address.setText(address);

        }


        if(mList.getPaymentStatus().equals("0")) {
            holder.btn_confirm.setVisibility(View.VISIBLE);
            holder.payment_status.setText(context.getResources().getString(R.string.pending));
        }else {
            holder.payment_status.setText("Complete");
            holder.btn_confirm.setVisibility(View.GONE);
        }

        Integer total_value=0;
        if (mList.getItem()!=null && !mList.getItem().isEmpty()){
            holder.tv_total_item.setText("No. of item: "+mList.getItem().size());


            for (int i=0; i<modelList.get(position).getItem().size(); i++){

                total_value=total_value +Integer.parseInt(modelList.get(position).getItem().get(i).getPrice());

            }
            Log.e("Total_rs",total_value.toString());
            holder.tv_total_rs.setText("Total Rs: "+total_value.toString());

            History_item_adapter history_item_adapter = new History_item_adapter(mList.getItem());
            LinearLayoutManager manager= new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            holder.recycler_view_bill.setLayoutManager(manager);
            holder.recycler_view_bill.setAdapter(history_item_adapter);
            history_item_adapter.notifyDataSetChanged();
        }


        final Integer finalTotal_value = total_value;

        holder.btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent wallet=new Intent(context, PaymentActivity.class);
                wallet.putExtra("Order_id",modelList.get(position).getOrderId().toString());
                wallet.putExtra("Order_details","");
                wallet.putExtra("Order_amount", finalTotal_value.toString());
                context.startActivity(wallet);

            }
        });

     holder.btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle args = new Bundle();
//                Fragment fm = new ViewPdfFragment();
//                args.putString("Order_id",modelList.get(position).getOrderId().toString());
//                fm.setArguments(args);
//                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                        .addToBackStack("Home_fragment")
//                        .commit();

                Intent intent=new Intent(context,ViewPdfFragment.class);
                intent.putExtra("Order_id",modelList.get(position).getOrderId().toString());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
