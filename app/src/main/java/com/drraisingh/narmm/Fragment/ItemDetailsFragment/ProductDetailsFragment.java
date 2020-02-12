package com.drraisingh.narmm.Fragment.ItemDetailsFragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.drraisingh.narmm.Fragment.UseFragmnet.Delivery_fragment;
import com.drraisingh.narmm.Model.ProductDetailsModel.ProductDetailsModel;
import com.drraisingh.narmm.R;

import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.Session_management;
import com.rxandroidnetworking.RxAndroidNetworking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_PRODUCT_DETAILS_URL;
import static com.drraisingh.narmm.Config.BaseURL.GET_addtocart;
import static com.drraisingh.narmm.Config.BaseURL.GET_send_complaint;
import static com.drraisingh.narmm.activity.MainActivity.totalBudgetCount;

/**
 * Created by Raghvendra Sahu on 27-Dec-19.
 */
public class ProductDetailsFragment extends Fragment {
    TextView text_price,tv_buynow,tv_add_cart, tv_itemname,tv_details;
    String product_id,product_img,product_name,product_tech_nm;
    ImageView product_iv;
    TabLayout tabLayout;
    Spinner spin_qty;
    FrameLayout simpleFrameLayout;
    public static String pro_details="",pro_feature,pro_dosage,pro_insect,pro_others, pro_weed, disease, pro_deficiency;
    String price;

    private Session_management sessionManagement;
    private String user_id,p_qty;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

   // private Object requireActivity() {
     //   isRemoving();
    //return true;
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.activity_item_details, container, false);

        sessionManagement = new Session_management(getActivity());
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        product_iv=root.findViewById(R.id.product_img);
        tv_add_cart=root.findViewById(R.id.tv_add_cart);
        text_price=root.findViewById(R.id.text_price);
        tv_buynow=root.findViewById(R.id.tv_buynow);
        spin_qty=root.findViewById(R.id.spin_qty);
        tv_itemname = root.findViewById(R.id.item_name);
        tv_details = root.findViewById(R.id.tv_details);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        simpleFrameLayout = (FrameLayout) root.findViewById(R.id.simpleFrameLayout);

        try {
            if (getArguments()!=null){
                product_id=getArguments().getString("product_id");
                product_img=getArguments().getString("product_img");
                product_name=getArguments().getString("product_name");
                product_tech_nm=getArguments().getString("product_tech_name");

                tv_itemname.setText(""+product_name+"\n"+product_tech_nm);
                Glide.with(getActivity())
                        .load(product_img)
                        .placeholder(R.drawable.logo)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(product_iv);
            }

        }catch (Exception e){
        }

        //*********************get product details*******
        if (ConnectivityReceiver.isConnected()){
            getProduct_details();
        }else {
            Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Features"));
        tabLayout.addTab(tabLayout.newTab().setText("Dosece"));
        tabLayout.addTab(tabLayout.newTab().setText("Insect"));
        tabLayout.addTab(tabLayout.newTab().setText("Disease"));
      //  tabLayout.addTab(tabLayout.newTab().setText("Fungus"));
        tabLayout.addTab(tabLayout.newTab().setText("Weed"));
        tabLayout.addTab(tabLayout.newTab().setText("deficiency"));
        tabLayout.addTab(tabLayout.newTab().setText("Others"));

        //*********************by default open this fragment************************

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // viewPager.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
               Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                       // fragment = new DetailsFragment();

                        try {
                            // tv_details.setText(pro_dosage);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(pro_details, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(pro_details));
                            }
                        }catch (Exception e){

                        }
                        break;
                    case 1:
                       // fragment = new FeaturesFragment();
                        try {
                            // tv_details.setText(pro_dosage);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(pro_feature, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(pro_feature));
                            }
                        }catch (Exception e){

                        }

                        break;
                    case 2:
                        //fragment = new DoseceFragment();
                        try {
                            // tv_details.setText(pro_dosage);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(pro_dosage, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(pro_dosage));
                            }
                        }catch (Exception e){

                        }


                        break;
                    case 3:
                       // fragment = new InsectFragment();

                        try {
                            // tv_details.setText(pro_dosage);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(pro_insect, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(pro_insect));
                            }
                        }catch (Exception e){

                        }
                        break;
                    case 4:
                       // fragment = new DiseaseFragment();
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(disease, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(disease));
                            }
                        }catch (Exception e){

                        }

                        break;

                    case 5:
                        // fragment = new WeedFragment();
                        try {
                            // tv_details.setText(pro_dosage);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(pro_weed, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(pro_weed));
                            }
                        }catch (Exception e){

                        }


                        break;

                    case 6:
                        // fragment = new DeficiencyFragment();
                        try {
                            // tv_details.setText(pro_dosage);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(pro_deficiency, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(pro_deficiency));
                            }
                        }catch (Exception e){

                        }
                        break;

                    case 7:
                        // fragment = new OthersFragment();
                        try {
                            // tv_details.setText(pro_dosage);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tv_details.setText(Html.fromHtml(pro_others, Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tv_details.setText(Html.fromHtml(pro_others));
                            }
                        }catch (Exception e){

                        }

                        break;

                    case 8:



                        break;
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.simpleFrameLayout, fragment)
                        .addToBackStack(null).commit();
               }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
//**************************************
        tv_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectivityReceiver.isConnected()){
                    AddCartApi();
                }else {
                    Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //**********************************************************
        tv_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectivityReceiver.isConnected()){
                    AddCartApi();
                }else {
                    Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return root;
    }

    private void AddCartApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id", user_id);
        params.put("product_id", product_id);
        params.put("qty", spin_qty.getSelectedItem().toString());

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
                        Toast.makeText(getActivity(), "Add cart successful", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
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

    private void getProduct_details() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_PRODUCT_DETAILS_URL)
                .addBodyParameter("product_id", product_id)
                .build()
                .getObjectObservable(ProductDetailsModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductDetailsModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("res_pro_del","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(ProductDetailsModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            Log.e("res_pro_det","Product Details : ------- "+user.getData());
                            if (user.getResponce()==true){
                                price=user.getData().getPrice();
                                text_price.setText("Rs. " +user.getData().getPrice());

                                pro_details=user.getData().getProductDescription();
                                pro_feature=user.getData().getFeatures();
                                pro_dosage=user.getData().getDosage();
                                pro_insect=user.getData().getInsect();
                                pro_others=user.getData().getOther();
                                pro_deficiency=user.getData().getDeficiency();
                                pro_weed=user.getData().getWeed();
                                disease=user.getData().getDisease();

                                //setDefaultfragment();
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        tv_details.setText(Html.fromHtml(pro_details, Html.FROM_HTML_MODE_COMPACT));
                                    } else {
                                        tv_details.setText(Html.fromHtml(pro_details));
                                    }
                                }catch (Exception e){

                                }

                            }else {
//                                Toast.makeText(Registrationpage.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
//                                Log.e("reg_respo",user.getError());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });


    }

    private void setDefaultfragment() {

        Fragment fm = new DetailsFragment();
        FragmentManager fragmentManager = getFragmentManager();
       // fragmentManager.beginTransaction().replace(R.id.simpleFrameLayout, fm)
         //       .addToBackStack(null).commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                getActivity().finish();
                return true;
            default:
                return false;
        }


    }

}
