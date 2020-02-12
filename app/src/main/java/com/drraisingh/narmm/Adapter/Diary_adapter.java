package com.drraisingh.narmm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drraisingh.narmm.Model.UseableModel.ComplainList_model;
import com.drraisingh.narmm.Model.UseableModel.DiaryList_model;
import com.drraisingh.narmm.R;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 09-Jan-20.
 */
public class Diary_adapter extends RecyclerView.Adapter<Diary_adapter.MyViewHolder> {

    private List<DiaryList_model> modelList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_sub, tv_desc,tv_res;
        LinearLayout tv_sen;
        public MyViewHolder(View view) {
            super(view);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_res = (TextView) view.findViewById(R.id.tv_res);

        }
    }

    public Diary_adapter(List<DiaryList_model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_diary_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DiaryList_model mList = modelList.get(position);

        holder.tv_desc.setText("Description: "+mList.getContent());
        holder.tv_res.setText("Date:- "+mList.getCreated_date());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
