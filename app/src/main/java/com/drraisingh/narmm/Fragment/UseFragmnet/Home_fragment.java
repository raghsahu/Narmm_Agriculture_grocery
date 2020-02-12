package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.drraisingh.narmm.Adapter.NarmmProductAdapter;
import com.drraisingh.narmm.Adapter.Product_adapter;
import com.drraisingh.narmm.Model.CategoryProductModel.CategoryModelDataProduct;
import com.drraisingh.narmm.Model.UseableModel.NarmmProductModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drraisingh.narmm.Adapter.CategoryProduct_adapter;
import com.drraisingh.narmm.Adapter.HomeCategoryProductAdapter;
import com.drraisingh.narmm.Adapter.Home_adapter;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Fragment.Search_fragment;
import com.drraisingh.narmm.Model.CategoryProductModel.CategoryProductModel;
import com.drraisingh.narmm.Model.CategoryWithCompanyModel.CategoryCompanyModel;
import com.drraisingh.narmm.Model.CategoryWithCompanyModel.CategoryCompanyModelData;
import com.drraisingh.narmm.Model.Category_model;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.R;

import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.rxandroidnetworking.RxAndroidNetworking;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_CATEGORY_URL;
import static com.drraisingh.narmm.Config.BaseURL.GET_PRODUCT_URL;
import static com.drraisingh.narmm.Config.BaseURL.GET_offer_productt;
import static com.drraisingh.narmm.Config.BaseURL.GET_party_product;

/**
 * Created by Rajesh Dabhi on 22/6/2017.
 */

public class Home_fragment extends Fragment {

    private static String TAG = Home_fragment.class.getSimpleName();

     SliderLayout imgSlider;
    private RecyclerView rv_items,rv_product,rv_offer,recycler_party_pro;
    //private RelativeLayout rl_view_all;

    private List<Category_model> category_modelList = new ArrayList<>();
    private Home_adapter adapter;

    private boolean isSubcat = false;
    Spinner spin_category,spin_comp;
    List<CategoryCompanyModelData> categoryCompanyModelDataList=new ArrayList<>();
    private String cat_id;
    CategoryProduct_adapter categoryProduct_adapter;
    ArrayList<CategoryModelDataProduct>categoryModelDataProductArrayList=new ArrayList<>();

    public Home_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
      //  setHasOptionsMenu(true);

      ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.app_name));
        ((MainActivity) getActivity()).updateHeader();


        imgSlider = (SliderLayout) view.findViewById(R.id.home_img_slider);
        rv_items = (RecyclerView) view.findViewById(R.id.rv_home);
        rv_product = (RecyclerView) view.findViewById(R.id.rv_product);
        spin_category =  view.findViewById(R.id.spin_category);
        spin_comp =  view.findViewById(R.id.spin_comp);
        rv_offer =  view.findViewById(R.id.rv_offer);
        recycler_party_pro =  view.findViewById(R.id.recycler_party_pro);

        rv_items.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_product.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_offer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_party_pro.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // initialize a SliderLayout
        imgSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imgSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imgSlider.setCustomAnimation(new DescriptionAnimation());
        imgSlider.setDuration(4000);

        // check internet connection
        if (ConnectivityReceiver.isConnected()) {
            makeGetSliderRequest();
           // makeGetCategoryRequest("");

            GetCategory_Company();
            GetOffer_Product();
            GetParty_Product();
        }else {
            Toast.makeText(getActivity(), "Please check internet", Toast.LENGTH_SHORT).show();
        }

//        rv_items.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_items, new RecyclerTouchListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                String getid = category_modelList.get(position).getId();
//                String getcat_title = category_modelList.get(position).getName();
//
//                Bundle args = new Bundle();
//                Fragment fm = new Product_fragment();
//                args.putString("cat_id", getid);
//                args.putString("cat_title", getcat_title);
//                fm.setArguments(args);
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                        .addToBackStack(null).commit();
//
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));

        //****************************

        //******************spinner on click item
        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cat_id=categoryCompanyModelDataList.get(position).getCategory().getId();

                Log.e("Cat_id",cat_id);

                List<String> company_name=new ArrayList<>();

                for (int i=0; i<categoryCompanyModelDataList.get(position).getCompany().size();i++){
                    company_name.add(categoryCompanyModelDataList.get(position).getCompany()
                            .get(i).getCompanyName());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item, company_name);
                //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spin_comp.setAdapter(spinnerArrayAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //*********spinner company onclick******

        spin_comp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String comp_id=categoryCompanyModelDataList.get(position).getCompany().get(position).getCompanyId();
               // Log.e("comp_id",comp_id);

                CallProductList(comp_id,cat_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }

    private void GetParty_Product() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String tag_json_obj = "json_login_req";

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("mobile", email);
//        params.put("pin", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.GET,
                GET_party_product, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("crop_trial", response.toString());
                progressDialog.dismiss();
                try {
                    categoryModelDataProductArrayList.clear();
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        // Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i=0; i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);
                            String id = object.getString("product_id");
                            String product_name = object.getString("product_name");
                            String technical_name = object.getString("technical_name");
                            String product_image = object.getString("product_image");

                            categoryModelDataProductArrayList.add(i,new CategoryModelDataProduct(id,product_name,technical_name,product_image));
                        }


                    } else {
                        // String error = response.getString("error");
                        Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
                    }


                    Home_adapter categoryProduct_adapter = new Home_adapter(categoryModelDataProductArrayList,getActivity());
                    recycler_party_pro.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recycler_party_pro.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    recycler_party_pro.setAdapter(categoryProduct_adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void GetOffer_Product() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String tag_json_obj = "json_login_req";

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.GET,
                GET_offer_productt, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("crop_trial", response.toString());
                progressDialog.dismiss();
                try {
                    categoryModelDataProductArrayList.clear();
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        // Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i=0; i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);
                            String id = object.getString("product_id");
                            String product_name = object.getString("product_name");
                            String technical_name = object.getString("technical_name");
                            String product_image = object.getString("product_image");

                            categoryModelDataProductArrayList.add(i,new CategoryModelDataProduct(id,product_name,technical_name,product_image));
                        }


                    } else {
                        // String error = response.getString("error");
                        Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
                    }


                    Home_adapter categoryProduct_adapter = new Home_adapter(categoryModelDataProductArrayList,getActivity());
                    rv_offer.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_offer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rv_offer.setAdapter(categoryProduct_adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }




    private void CallProductList(String comp_id, String cat_id) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_PRODUCT_URL)
                .addBodyParameter("category_id", cat_id)
                .addBodyParameter("company_id", comp_id)
                .build()
                .getObjectObservable(CategoryProductModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryProductModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("reg_rescomp","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(CategoryProductModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {

                             Log.e("reg_responce123",""+user.getData().size());
                            if (user.getResponce()==true){

                                categoryProduct_adapter = new CategoryProduct_adapter(user.getData(), getActivity());

                                rv_product.setAdapter(categoryProduct_adapter);
                                categoryProduct_adapter.notifyDataSetChanged();


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

    private void GetCategory_Company() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.get(GET_CATEGORY_URL)
               // .addBodyParameter("full_name", fullname)
                .build()
                .getObjectObservable(CategoryCompanyModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryCompanyModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("reg_rescomp","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(CategoryCompanyModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            categoryCompanyModelDataList.clear();

                            categoryCompanyModelDataList=user.getData();
                            // Log.e("reg_responce",user.getData().getFullName());
                          //  Toast.makeText(Registrationpage.this, ""+user.getResponce(), Toast.LENGTH_SHORT).show();
                            List<String> category_name=new ArrayList<>();
                            for (int i=0; i<user.getData().size();i++){
                                category_name.add(user.getData().get(i).getCategory().getName());
                            }

//                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
//                                    (getActivity(), android.R.layout.simple_spinner_item, category_name);
//                            //selected item will look like a spinner set from XML
//                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            spin_category.setAdapter(spinnerArrayAdapter);

                            //***********************************

                            HomeCategoryProductAdapter   homeCategoryProductAdapter = new HomeCategoryProductAdapter(categoryCompanyModelDataList, getActivity());

                            rv_items.setAdapter(homeCategoryProductAdapter);
                            homeCategoryProductAdapter.notifyDataSetChanged();


                        }catch (Exception e){
                            Log.e("categ_catch",e.getMessage());
                        }

                    }
                });

    }

    /**
     * Method to make json array request where json response starts wtih {
     */
    private void makeGetSliderRequest() {

        JsonArrayRequest req = new JsonArrayRequest(BaseURL.GET_SLIDER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            // arraylist list variable for store data;
                            ArrayList<HashMap<String, String>> listarray = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = (JSONObject) response
                                        .get(i);

                                HashMap<String, String> url_maps = new HashMap<String, String>();
                                url_maps.put("slider_title", jsonObject.getString("slider_title"));
                                url_maps.put("slider_image", BaseURL.IMG_SLIDER_URL + jsonObject.getString("slider_image"));

                                listarray.add(url_maps);
                            }

                            for (HashMap<String, String> name : listarray) {
                                TextSliderView textSliderView = new TextSliderView(getActivity());
                                // initialize a SliderLayout
                                textSliderView
                                        .description(name.get("slider_title"))
                                        .image(name.get("slider_image"))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name.get("slider_title"));

                                imgSlider.addSlider(textSliderView);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    /**
     * Method to make json object request where json response starts wtih {
     */
    private void makeGetCategoryRequest(String parent_id) {

        // Tag used to cancel the request
        String tag_json_obj = "json_category_req";

        isSubcat = false;

        Map<String, String> params = new HashMap<String, String>();
        if (parent_id != null && parent_id != "") {
            params.put("parent", parent_id);
            isSubcat = true;
        }

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                GET_CATEGORY_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Category_model>>() {
                        }.getType();

                        category_modelList = gson.fromJson(response.getString("data"), listType);

                       // adapter = new Home_adapter(category_modelList);
                        rv_items.setAdapter(adapter);
                       // rv_product.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(true);
        MenuItem check = menu.findItem(R.id.action_change_password);
        check.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                Fragment fm = new Search_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
                return false;
        }
        return false;
    }

}
