package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drraisingh.narmm.Adapter.Card_NewAdapter;
import com.drraisingh.narmm.Adapter.Cart_adapter;
import com.drraisingh.narmm.Config.BaseURL;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_Main_model;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_model;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.DatabaseHandler;
import com.drraisingh.narmm.util.Session_management;
import com.drraisingh.narmm.util.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import static com.drraisingh.narmm.Config.BaseURL.GET_get_cart;

/**
 * Created by Rajesh Dabhi on 26/6/2017.
 */

public class Cart_fragment extends Fragment implements View.OnClickListener {

    private static String TAG = Cart_fragment.class.getSimpleName();

    private RecyclerView rv_cart;
    private TextView tv_clear, tv_total;
    public TextView tv_item;
    private Button btn_checkout;

    private DatabaseHandler db;
   // private List<Cartlist_model> delivery_address_modelList = new ArrayList<>();
    private List<Cartlist_model> cartlist_main_models = new ArrayList<>();

    private Session_management sessionManagement;
    private String user_id;
    private String item_count;

    private JSONArray passArray;

    public Cart_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.tv_cart_title));

        sessionManagement = new Session_management(getActivity());
        sessionManagement.cleardatetime();
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        /*tv_clear = (TextView) view.findViewById(R.id.tv_cart_clear);*/
        tv_total = (TextView) view.findViewById(R.id.tv_cart_total);
        tv_item = (TextView) view.findViewById(R.id.tv_cart_item);
        btn_checkout = (Button) view.findViewById(R.id.btn_cart_checkout);
        rv_cart = (RecyclerView) view.findViewById(R.id.rv_cart);
        rv_cart.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = new DatabaseHandler(getActivity());

        if (ConnectivityReceiver.isConnected()) {
            GetCartItemRequest();
        } else {
            ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
        }


        //updateData();

     /*   tv_clear.setOnClickListener(this);*/
        btn_checkout.setOnClickListener(this);

        return view;
    }

    private void GetCartItemRequest() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
       params.put("farmer_id",user_id );

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_get_cart, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("cart_retail", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status==true) {

                        cartlist_main_models.clear();
                      // JSONArray jsonObject=response.getJSONArray("data");

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Cartlist_model>>() {
                        }.getType();

                        cartlist_main_models = gson.fromJson(response.getString("data"), listType);

                        // Log.e("responce_addres",delivery_address_modelList.get(0).getShop_name());
                        Card_NewAdapter adapter = new Card_NewAdapter(cartlist_main_models,getActivity());
                        rv_cart.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_cart.setAdapter(adapter);
                        //adapter.notifyDataSetChanged();

                        //tv_total.setText("" + );
                        //item_count= String.valueOf(cartlist_main_models.size());
                        //tv_item.setText("" + cartlist_main_models.size());

//                        if (delivery_address_modelList.isEmpty()) {
//                            if (getActivity() != null) {
//                                //Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
//                            }
//                        }

                    }else {
                        Toast.makeText(getActivity(), "No item found", Toast.LENGTH_SHORT).show();
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
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    private void open_dialog_product_thumbnail(String imgname) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_product_image);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView txtcancel=(TextView)dialog.findViewById(R.id.txtcancelproductthumbnail);
        txtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView imgpthumbnail=(ImageView)dialog.findViewById(R.id.img_product_thumbnail);
        Glide.with(getActivity())
                .load(imgname)
                .centerCrop()
                .placeholder(R.mipmap.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imgpthumbnail);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

            if (id == R.id.btn_cart_checkout) {


                if (ConnectivityReceiver.isConnected()) {
                    //  makeGetLimiteRequest();
                    getCartinfo();
                } else {
                    ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
                }

            }

            //**************************************


    }

    // update UI
    private void updateData() {
        tv_total.setText("" + db.getTotalAmount());
        tv_item.setText("" + db.getCartCount());
        ((MainActivity) getActivity()).setCartCounter("" + db.getCartCount());
    }

    private void showClearDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Are you sure!\nyou want to delete all cart product");
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // clear cart data
                db.clearCart();
                ArrayList<HashMap<String, String>> map = db.getCartAll();

                Cart_adapter adapter = new Cart_adapter(getActivity(), map, new Cart_adapter.cartproductlistener() {
                    @Override
                    public void imgcartclicklistener(String imgname) {
                        open_dialog_product_thumbnail(imgname);
                    }
                });
                rv_cart.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                updateData();

                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    /**
     * Method to make json array request where json response starts wtih
     */
    private void makeGetLimiteRequest() {

        JsonArrayRequest req = new JsonArrayRequest(BaseURL.GET_LIMITE_SETTING_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        Double total_amount = Double.parseDouble(db.getTotalAmount());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            boolean issmall = false;
                            boolean isbig = false;

                            // arraylist list variable for store data;
                            ArrayList<HashMap<String, String>> listarray = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = (JSONObject) response
                                        .get(i);
                                int value;

                                if (jsonObject.getString("id").equals("1")) {
                                    value = Integer.parseInt(jsonObject.getString("value"));

                                    if (total_amount < value) {
                                        issmall = true;
                                        Toast.makeText(getActivity(), "" + jsonObject.getString("title") + " : " + value, Toast.LENGTH_SHORT).show();
                                    }
                                } else if (jsonObject.getString("id").equals("2")) {
                                    value = Integer.parseInt(jsonObject.getString("value"));

                                    if (total_amount > value) {
                                        isbig = true;
                                        Toast.makeText(getActivity(), "" + jsonObject.getString("title") + " : " + value, Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            if (!issmall && !isbig) {
                                if(sessionManagement.isLoggedIn()) {
                                    Bundle args = new Bundle();
                                    Fragment fm = new Delivery_fragment();
                                    fm.setArguments(args);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                            .addToBackStack(null).commit();
                                }else {
                                    //Toast.makeText(getActivity(), "Please login or regiter.\ncontinue", Toast.LENGTH_SHORT).show();
                                    Bundle args = new Bundle();
                                    Fragment fm = new Delivery_fragment();
                                    fm.setArguments(args);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                            .addToBackStack(null).commit();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Connection Time out", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    @Override
    public void onPause() {
        super.onPause();
        // unregister reciver
        getActivity().unregisterReceiver(mCart);
    }

    @Override
    public void onResume() {
        super.onResume();
        // register reciver
        getActivity().registerReceiver(mCart, new IntentFilter("Grocery_cart"));
    }

    // broadcast reciver for receive data
    private BroadcastReceiver mCart = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("type");

            if (type.contentEquals("update")) {
                updateData();
            }
        }
    };


    public void getCartinfo() {

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id",user_id );

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_get_cart, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("cart_retail", response.toString());
                try {
                    cartlist_main_models.clear();
                    Boolean status = response.getBoolean("responce");
                    if (status==true) {

//                        List<Cartlist_model> cartlist_main_models = new ArrayList<>();
//                        cartlist_main_models.clear();
                        // JSONArray jsonObject=response.getJSONArray("data");

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Cartlist_model>>() {
                        }.getType();

                        cartlist_main_models = gson.fromJson(response.getString("data"), listType);

                        item_count= String.valueOf(cartlist_main_models.size());

                        if (cartlist_main_models!=null && !cartlist_main_models.isEmpty()){

                           Integer total_value=0;

                            passArray=new JSONArray();
                            for (int u = 0; u<cartlist_main_models.size() ; u++){
                                total_value = total_value + Integer.parseInt(cartlist_main_models.get(u).getPrice());
                                JSONObject jObjP = new JSONObject();
                                try {
                                    jObjP.put("product_id",cartlist_main_models.get(u).getProduct_id());
                                    jObjP.put("quantity",cartlist_main_models.get(u).getQty());

                                    passArray.put(jObjP);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            Log.e("UPDATED "," ---- "+total_value+" item_count "+item_count);
                            Log.e("UPDATED_pass_arry "," ----- "+passArray);

                            Bundle args = new Bundle();
                            Fragment fm = new Delivery_fragment();

                            args.putString("total_value", ""+total_value);
                            args.putString("passArray", passArray.toString());
                            args.putString("item_count", item_count);
                            args.putParcelableArrayList("cart_model", (ArrayList<? extends Parcelable>) cartlist_main_models);

                            fm.setArguments(args);
                            android.app.FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                    .addToBackStack(null).commit();

                        }else {
                            Toast.makeText(getActivity(), "Please add item", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                         Toast.makeText(getActivity(), "No item found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                VolleyLog.d("Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }


}
