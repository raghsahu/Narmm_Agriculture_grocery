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

import com.drraisingh.narmm.Model.My_notify;
import com.drraisingh.narmm.Model.UseableModel.ComplainList_model;
import com.drraisingh.narmm.NotificationDetails;
import com.drraisingh.narmm.R;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 06-Jan-20.
 */
public class Complain_adapter extends RecyclerView.Adapter<Complain_adapter.MyViewHolder> {

    private List<ComplainList_model> modelList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sub, tv_desc,tv_res,tv_date;
        LinearLayout tv_sen;
        public MyViewHolder(View view) {
            super(view);
            tv_sub = (TextView) view.findViewById(R.id.tv_sub);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_res = (TextView) view.findViewById(R.id.tv_res);
            tv_date = (TextView) view.findViewById(R.id.tv_date);

        }
    }

    public Complain_adapter(List<ComplainList_model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_notify_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ComplainList_model mList = modelList.get(position);

        holder.tv_sub.setText("Subject: "+mList.getSubject());
        holder.tv_desc.setText("Message: "+mList.getMessage());
        holder.tv_res.setText("Responce:- "+mList.getResponce_msg());
        holder.tv_date.setText(""+mList.getCreated_date());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
