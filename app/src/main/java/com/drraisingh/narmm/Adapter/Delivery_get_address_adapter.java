package com.drraisingh.narmm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import com.drraisingh.narmm.Model.UseableModel.Shop_address_model;
import com.drraisingh.narmm.R;

/**
 * Created by Rajesh on 2017-08-01.
 */

public class Delivery_get_address_adapter extends RecyclerView.Adapter<Delivery_get_address_adapter.MyViewHolder> {

    private static String TAG = Delivery_get_address_adapter.class.getSimpleName();
    private final String Local;

    private List<Shop_address_model> modelList;

    private Context context;

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private boolean ischecked = false;
    private String location_id = "";
    private String getsocity, gethouse, getphone, getpin, getname, getcharge;
    //public static String shop_id;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  tv_shopname, tv_phone, tv_market,tv_pro_name,address,del_address;
        public RadioButton rb_select;


        public MyViewHolder(View view) {
            super(view);

            tv_shopname = (TextView) view.findViewById(R.id.tv_shopname);
            tv_phone = (TextView) view.findViewById(R.id.tv_adres_phone);
            tv_market = (TextView) view.findViewById(R.id.tv_market);
            tv_pro_name = (TextView) view.findViewById(R.id.tv_pro_name);
            del_address = (TextView) view.findViewById(R.id.del_address);
            rb_select =  view.findViewById(R.id.rb_adres);

            rb_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RadioButton cb = (RadioButton) view;
                    int clickedPos = getAdapterPosition();

                    location_id=modelList.get(clickedPos).getShop_id();
                    Log.e("shop_id_",location_id);

                    gethouse = modelList.get(clickedPos).getBlock();
                    getname = modelList.get(clickedPos).getPrapriter_name();
                    getphone = modelList.get(clickedPos).getMobile_no();
                    getsocity = modelList.get(clickedPos).getShop_name();
                    getpin = modelList.get(clickedPos).getDistrict();
                    //getcharge = modelList.get(clickedPos).get();

                    if (modelList.size() > 1) {
                        if (cb.isChecked()) {
                            if (lastChecked != null) {
                                lastChecked.setChecked(false);
                                modelList.get(lastCheckedPos).setIscheckd(false);
                            }

                            lastChecked = cb;
                            lastCheckedPos = clickedPos;
                        } else {
                            lastChecked = null;
                        }
                    }
                    modelList.get(clickedPos).setIscheckd(cb.isChecked());

                    if (cb.isChecked()) {
                        ischecked = true;

//                        Intent updates = new Intent("Grocery_delivery_charge");
//                        updates.putExtra("type", "update");
//                        updates.putExtra("charge", getcharge);
//                        context.sendBroadcast(updates);
                    } else {
                        ischecked = false;

//                        Intent updates = new Intent("Grocery_delivery_charge");
//                        updates.putExtra("type", "update");
//                        updates.putExtra("charge", "00");
//                        context.sendBroadcast(updates);
                    }

                }
            });
        }
    }

    public Delivery_get_address_adapter(List<Shop_address_model> modelList, String local) {
        this.modelList = modelList;
        this.Local = local;
    }

    @Override
    public Delivery_get_address_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_delivery_time_rv_test, parent, false);

        context = parent.getContext();

        return new Delivery_get_address_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Delivery_get_address_adapter.MyViewHolder holder, final int position) {
        final Shop_address_model mList = modelList.get(position);

        holder.tv_shopname.setText(mList.getShop_name());
        holder.tv_phone.setText(mList.getMobile_no());
        holder.tv_market.setText(mList.getPlace_market());
        holder.tv_pro_name.setText(mList.getPrapriter_name());
        holder.del_address.setText(mList.getBlock()+" "+mList.getTehsil()+" "+mList.getState()+" "+mList.getDistrict());

        if (Local.equalsIgnoreCase("local")){
            holder.rb_select.setVisibility(View.GONE);
        }

        holder.rb_select.setChecked(mList.getIscheckd());
        holder.rb_select.setTag(new Integer(position));



        //for default check in first item
//        if (position == 0 /*&& mList.getIscheckd() && holder.rb_select.isChecked()*/) {
//            holder.rb_select.setChecked(true);
//            modelList.get(position).setIscheckd(true);
//
//            lastChecked = holder.rb_select;
//            lastCheckedPos = 0;
//
//            location_id = modelList.get(0).getLocation_id();
//
//            gethouse = modelList.get(0).getHouse_no();
//            getname = modelList.get(0).getReceiver_name();
//            getphone = modelList.get(0).getReceiver_mobile();
//            getsocity = modelList.get(0).getSocity_name();
//            getpin = modelList.get(0).getPincode();
//            getcharge = modelList.get(0).getDelivery_charge();
//
//            ischecked = true;
//
//            Intent updates = new Intent("Grocery_delivery_charge");
//            updates.putExtra("type", "update");
//            updates.putExtra("charge", getcharge);
//            context.sendBroadcast(updates);
//        }



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public String getlocation_id() {
        return location_id;
    }

    public String getaddress() {
        String address = "Provider name: " + getname + "\n" + "Provider mob: " + getphone +
                "\n" + "Shop name: " + getsocity+
                "\n" + "Distt. : "+ getpin +
                "\n" + "Block : "+"\n" + gethouse ;


        return address;
    }

    public boolean ischeckd() {
        return ischecked;
    }



}
