package com.drraisingh.narmm.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.drraisingh.narmm.Adapter.CategoryProduct_adapter;
import com.drraisingh.narmm.Adapter.Product_adapter;
import com.drraisingh.narmm.Model.CategoryProductModel.CategoryProductModel;
import com.drraisingh.narmm.Model.Category_model;
import com.drraisingh.narmm.Model.Product_model;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.rxandroidnetworking.RxAndroidNetworking;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_PRODUCT_URL;

/**
 * Created by Rajesh Dabhi on 26/6/2017.
 */

public class Product_fragment extends Fragment {
    private static final String KEY_POSITION="position";
    private static String TAG = Product_fragment.class.getSimpleName();

    private RecyclerView rv_cat;
    private TabLayout tab_cat;

    private List<Category_model> category_modelList = new ArrayList<>();
    private List<String> cat_menu_id = new ArrayList<>();
    GridLayoutManager manager;
    private List<Product_model> product_modelList = new ArrayList<>();
    private Product_adapter adapter_product;
    CategoryProduct_adapter categoryProduct_adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        //setHasOptionsMenu(true);

        rv_cat = (RecyclerView) view.findViewById(R.id.rv_subcategory);
         manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        rv_cat.setLayoutManager(manager);

        String getcat_id = getArguments().getString("cat_id");
        String getcat_title = getArguments().getString("cat_title");


        ((MainActivity) getActivity()).setTitle(getcat_title);

        // check internet connection
        if (ConnectivityReceiver.isConnected()) {

            GetCategoryProduct(getcat_id);//using rxjava with retofit
        }


        return view;
    }

    private void GetCategoryProduct(String getcat_id) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_PRODUCT_URL)
                .addBodyParameter("category_id", getcat_id)
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

                            // Log.e("reg_responce",user.getData().getFullName());
                            if (user.getResponce()==true){

                                categoryProduct_adapter = new CategoryProduct_adapter(user.getData(), getActivity());

                                rv_cat.setAdapter(categoryProduct_adapter);
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


    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        MenuItem check = menu.findItem(R.id.action_change_password);
        check.setVisible(false);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        *//*searchView.setBackgroundColor(getResources().getColor(R.color.white));
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.GRAY);*//*

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter_product.getFilter().filter(newText);
                return false;
            }
        });
    }*/









}
