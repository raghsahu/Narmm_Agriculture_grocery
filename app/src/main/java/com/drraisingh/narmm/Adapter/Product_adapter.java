package com.drraisingh.narmm.Adapter;


import android.app.FragmentManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Fragment.ItemDetailsFragment.ProductDetailsFragment;
import com.drraisingh.narmm.Model.CategoryProductModel.CategoryModelDataProduct;


import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.DatabaseHandler;
import com.drraisingh.narmm.util.Session_management;

import org.json.JSONException;
import org.json.JSONObject;

import static com.drraisingh.narmm.Config.BaseURL.GET_addtocart;
import static com.drraisingh.narmm.activity.MainActivity.totalBudgetCount;

/**
 * Created by Rajesh Dabhi on 26/6/2017.
 */

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.MyViewHolder>
        implements Filterable {

    private List<CategoryModelDataProduct> modelList;
    private List<CategoryModelDataProduct> mFilteredList;
    private Context context;
    private DatabaseHandler dbcart;
    MediaPlayer mp;
    private Session_management sessionManagement;
    private String user_id;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_subcat_name, tv_total,tv_subcat_add;
        public ImageView iv_logo;
        CardView  cardView;
        ConstraintLayout canst_item;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_subcat_title);
            tv_subcat_name = (TextView) view.findViewById(R.id.tv_subcat_name);
            tv_total = (TextView) view.findViewById(R.id.tv_subcat_total);
            iv_logo = (ImageView) view.findViewById(R.id.iv_subcat_img);
           cardView = (CardView) view.findViewById(R.id.card_view);
            canst_item = view.findViewById(R.id.canst_item);
            tv_subcat_add = view.findViewById(R.id.tv_subcat_add);


        }

    }

    public Product_adapter(List<CategoryModelDataProduct> modelList, Context context) {
        this.modelList = modelList;
        this.mFilteredList = modelList;

        dbcart = new DatabaseHandler(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CategoryModelDataProduct mList = modelList.get(position);

        sessionManagement = new Session_management(context);
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + mList.getProductImage())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.iv_logo);

        holder.tv_title.setText(mList.getProductName());
        holder.tv_subcat_name.setText(mList.getTechnicalName());
      //  holder.tv_price.setText(context.getResources().getString(R.string.tv_pro_price) + mList.get() + " " +
                //mList.getUnit() + " / "  + mList.getPrice()+" "+ context.getResources().getString(R.string.currency));

        holder.canst_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                Fragment fm = new ProductDetailsFragment();
                args.putString("product_id", mList.getProductId());
                args.putString("product_name", mList.getProductName());
                args.putString("product_tech_name", mList.getTechnicalName());
                args.putString("product_img", BaseURL.IMG_PRODUCT_URL + mList.getProductImage());
                fm.setArguments(args);
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack("Home_fragment")
                        .commit();
            }
        });

        holder.tv_subcat_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (ConnectivityReceiver.isConnected()){
                    AddCartApi(modelList.get(position).getProductId());
                }else {
                    Toast.makeText(context, "Please check internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void AddCartApi(String productId) {
        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id", user_id);
        params.put("product_id", productId);
        params.put("qty", "1");

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_addtocart, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("local_retail", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");

                    if (status) {
                        String count = response.getString("count");
                        totalBudgetCount.setText(""+count);
                        Toast.makeText(context, "Add cart successful", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                VolleyLog.d("Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    if (context != null) {
                        Toast.makeText(context, context.getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);



    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = modelList;
                } else {

                    ArrayList<CategoryModelDataProduct> filteredList = new ArrayList<>();

                    for (CategoryModelDataProduct androidVersion : modelList) {

                        if (androidVersion.getProductName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<CategoryModelDataProduct>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }





}