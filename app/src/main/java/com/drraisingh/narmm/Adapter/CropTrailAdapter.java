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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Fragment.ItemDetailsFragment.ProductDetailsFragment;
import com.drraisingh.narmm.Model.OrderHistoryModel.OrderHistory_modelData;
import com.drraisingh.narmm.Model.UseableModel.CropTrailModel;
import com.drraisingh.narmm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class CropTrailAdapter extends RecyclerView.Adapter<CropTrailAdapter.MyViewHolder> {

    private List<CropTrailModel> modelList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView crop_name;
                ImageView tv_view_pdf;


        public MyViewHolder(View view) {
            super(view);
            crop_name = (TextView) view.findViewById(R.id.crop_name);
            tv_view_pdf = (ImageView) view.findViewById(R.id.tv_view_pdf);
          //  tv_order_date = (TextView) view.findViewById(R.id.tv_order_date);


        }
    }

    public CropTrailAdapter(ArrayList<CropTrailModel> modelList, Context context) {
        this.modelList = modelList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.croptrail_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CropTrailModel mList = modelList.get(position);

        holder.crop_name.setText(mList.getCrop_name());
      // holder.order_status.setText(mList.getOrderStatus());


        holder.tv_view_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle args = new Bundle();
//                Fragment fm = new ViewPdfFragment();
//                args.putString("Viewpdf", modelList.get(position).getPdf());
//                fm.setArguments(args);
//                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                        .addToBackStack("Home_fragment")
//                        .commit();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(modelList.get(position).getPdf()));
                context.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
