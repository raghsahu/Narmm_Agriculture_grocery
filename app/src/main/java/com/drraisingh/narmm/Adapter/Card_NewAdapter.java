package com.drraisingh.narmm.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Fragment.ItemDetailsFragment.ProductDetailsFragment;
import com.drraisingh.narmm.Fragment.UseFragmnet.Cart_fragment;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_Main_model;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_model;
import com.drraisingh.narmm.Model.UseableModel.ComplainList_model;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.Session_management;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drraisingh.narmm.Config.BaseURL.GET_remove_to_cart;
import static com.drraisingh.narmm.Config.BaseURL.GET_send_complaint;
import static com.drraisingh.narmm.Config.BaseURL.GET_update_cart;
import static com.drraisingh.narmm.activity.MainActivity.totalBudgetCount;

/**
 * Created by Raghvendra Sahu on 07-Jan-20.
 */
public class Card_NewAdapter extends RecyclerView.Adapter<Card_NewAdapter.MyViewHolder> {

    private List<Cartlist_model> modelList;

    private Context context;
    private Session_management sessionManagement;
    private String user_id;
    //public static JSONArray passArray=null;
   // public Integer total_amt=0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_subcat_title, tv_sub_price,tv_subcat_out,tv_total,tv_subcat_update,tv_cart_id;
        LinearLayout tv_sen;
        ImageView iv_subcat_img;
        Spinner spin_qty;

        public MyViewHolder(View view) {
            super(view);
            tv_subcat_title = (TextView) view.findViewById(R.id.tv_subcat_title);
            tv_sub_price = (TextView) view.findViewById(R.id.tv_sub_price);
            iv_subcat_img = view.findViewById(R.id.iv_subcat_img);
            tv_subcat_out = view.findViewById(R.id.tv_subcat_out);
            tv_total = view.findViewById(R.id.tv_total);
            spin_qty = view.findViewById(R.id.tv_subcat_contetiy);
            tv_subcat_update = view.findViewById(R.id.tv_subcat_update);
            tv_cart_id = view.findViewById(R.id.tv_cart_id);

        }
    }

    public Card_NewAdapter(List<Cartlist_model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_cart_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Cartlist_model mList = modelList.get(position);

        sessionManagement = new Session_management(context);
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
        holder.tv_cart_id.setText(""+modelList.get(position).getAddtocart_id());

        holder.tv_subcat_title.setText(""+modelList.get(position).getProduct_name());
        holder.tv_sub_price.setText("Rs. "+modelList.get(position).getUnit_price());
        holder.tv_total.setText("Total Rs. "+modelList.get(position).getPrice());

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + modelList.get(position).getProduct_image())
                .fitCenter()
                .placeholder(R.drawable.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.iv_subcat_img);

        String qty=modelList.get(position).getQty();
        holder.spin_qty.setSelection(Integer.parseInt(qty)-1);

        holder.tv_subcat_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove_card_item(modelList.get(position).getAddtocart_id(),position);

            }
        });

        holder.tv_subcat_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add_cart_id=modelList.get(position).getAddtocart_id();
                String spin_qty=holder.spin_qty.getSelectedItem().toString();
                Update_cart(spin_qty,add_cart_id);
            }
        });


        //********************

    }

    private void Update_cart(final String qty_update, String add_cart_id) {


        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("addtocart_id", add_cart_id);
        params.put("qty", qty_update);
        params.put("farmer_id", user_id);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_update_cart, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("remove_retail", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show();
                       // cartlist_main_model.setQty(qty_update);
                      // notifyDataSetChanged();

                        Fragment fm = new Cart_fragment();
                        FragmentManager fragmentManager = ((FragmentActivity) context).getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                .addToBackStack(null)
                                .commit();

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

    private void Remove_card_item(String product_id, final int position) {

        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("addtocart_id", product_id);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_remove_to_cart, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("remove_retail", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {
                        String count = response.getString("count");
                        totalBudgetCount.setText(""+count);

                        Toast.makeText(context, "Remove successful", Toast.LENGTH_SHORT).show();
                        modelList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, modelList.size());

                        notifyDataSetChanged();
                        Log.d("remove_itm",""+ modelList.size());

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
        return modelList.size();
    }

}
