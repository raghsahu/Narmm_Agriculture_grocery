package com.drraisingh.narmm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drraisingh.narmm.NotificationDetails;
import com.drraisingh.narmm.R;

import java.util.List;

import com.drraisingh.narmm.Model.My_notify;

public class NotificationAdaper extends RecyclerView.Adapter<NotificationAdaper.MyViewHolder> {

    private List<My_notify> modelList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_orderno, tv_status, tv_date, tv_time, tv_price, tv_item;
    LinearLayout tv_sen;
        public MyViewHolder(View view) {
            super(view);
            tv_orderno = (TextView) view.findViewById(R.id.tv_order_no);
            tv_status = (TextView) view.findViewById(R.id.tv_order_status);
            tv_date = (TextView) view.findViewById(R.id.tv_order_date);
            tv_time = (TextView) view.findViewById(R.id.tv_order_time);
            tv_sen = (LinearLayout) view.findViewById(R.id.tv_sen);

        }
    }

    public NotificationAdaper(List<My_notify> modelList) {
        this.modelList = modelList;
    }

    @Override
    public NotificationAdaper.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_notify_item, parent, false);

        context = parent.getContext();

        return new NotificationAdaper.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationAdaper.MyViewHolder holder, int position) {
        final My_notify mList = modelList.get(position);

        holder.tv_orderno.setText(Html.fromHtml(mList.getTitle()));



        holder.tv_time.setText(context.getResources().getString(R.string.datee) + mList.getCdate() );
    holder.tv_sen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,NotificationDetails.class);
            intent.putExtra("title",mList.getTitle());
            intent.putExtra("des",mList.getDescription());
            context.startActivity(intent);
        }
    });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}