package com.drraisingh.narmm.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Model.UseableModel.CropTrailModel;
import com.drraisingh.narmm.Model.UseableModel.NarmmProductModel;
import com.drraisingh.narmm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class NarmmProductAdapter extends RecyclerView.Adapter<NarmmProductAdapter.MyViewHolder> {

    private List<NarmmProductModel> modelList;

    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_subcat_title;
        ImageView iv_subcat_img;

        public MyViewHolder(View view) {
            super(view);
            tv_subcat_title = (TextView) view.findViewById(R.id.tv_subcat_title);
            iv_subcat_img =  view.findViewById(R.id.iv_subcat_img);

        }
    }

    public NarmmProductAdapter(ArrayList<NarmmProductModel> modelList, Context context) {
        this.modelList = modelList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product_rv, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NarmmProductModel mList = modelList.get(position);

        holder.tv_subcat_title.setText(mList.getProduct_name());
        // holder.order_status.setText(mList.getOrderStatus());

        Glide.with(context)
                .load(mList.getImage())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.iv_subcat_img);

        holder.iv_subcat_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_image(mList.getImage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private void show_image(String imgurl) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_imageview);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ImageView imageView = dialog.findViewById(R.id.img_product);
        ImageView cancel = dialog.findViewById(R.id.cancel_card);

        Glide.with(context)
                .load(imgurl)
                .centerCrop()
                .placeholder(R.drawable.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }       });


        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }


}
