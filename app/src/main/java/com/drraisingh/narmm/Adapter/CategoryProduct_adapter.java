package com.drraisingh.narmm.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drraisingh.narmm.Model.CategoryProductModel.CategoryProductModelData;
import com.drraisingh.narmm.R;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 26-Dec-19.
 */
public class CategoryProduct_adapter extends RecyclerView.Adapter<CategoryProduct_adapter.MyViewHolder> {

    private List<CategoryProductModelData> modelList;
   Context context;
    Product_adapter product_adapter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cat_name;
        RecyclerView rv_product;
        public Context context;

        public MyViewHolder(View view) {
            super(view);
            tv_cat_name = (TextView) view.findViewById(R.id.tv_cat_name);
            rv_product =  view.findViewById(R.id.rv_product);
        }
    }

    public CategoryProduct_adapter(List<CategoryProductModelData> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_product_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryProductModelData mList = modelList.get(position);

        holder.tv_cat_name.setText(mList.getHeading());

        product_adapter = new Product_adapter(mList.getProduct(), context);
        LinearLayoutManager manager= new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.rv_product.setLayoutManager(manager);
        holder.rv_product.setAdapter(product_adapter);
        product_adapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
