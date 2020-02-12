package com.drraisingh.narmm.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.drraisingh.narmm.Model.CategoryProductModel.CategoryProductModel;
import com.drraisingh.narmm.Model.CategoryProductModel.CategoryProductModelData;
import com.drraisingh.narmm.Model.CategoryWithCompanyModel.CategoryCompanyModelData;
import com.drraisingh.narmm.Model.CategoryWithCompanyModel.CompanyData;
import com.drraisingh.narmm.R;
import com.rxandroidnetworking.RxAndroidNetworking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_PRODUCT_URL;

/**
 * Created by Raghvendra Sahu on 02-Jan-20.
 */
public class HomeCategoryProductAdapter extends RecyclerView.Adapter<HomeCategoryProductAdapter.MyViewHolder> {

    private List<CategoryCompanyModelData> modelList;
    private Context context;
    List<CategoryProductModelData> categoryCompanyModelDataList=new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        Spinner spin_cont;
        RecyclerView recycler_item;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_cate);
            spin_cont =  view.findViewById(R.id.spin_cont);
            recycler_item =  view.findViewById(R.id.recycler_item);
            //image = (ImageView) view.findViewById(R.id.iv_home_img);
        }
    }

    public HomeCategoryProductAdapter(List<CategoryCompanyModelData> modelList, Activity activity) {
        this.modelList = modelList;
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_category_product_item, parent, false);

        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CategoryCompanyModelData mList = modelList.get(position);

//        Glide.with(context)
//                .load(BaseURL.IMG_CATEGORY_URL+mList.getImage())
//                .placeholder(R.drawable.logo)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .dontAnimate()
//                .into(holder.image);

        holder.title.setText(modelList.get(position).getCategory().getName());

        List<String> company_name=new ArrayList<>();
        final HashMap<Integer, CompanyData> ClassHashMap = new HashMap<Integer, CompanyData>();
        for (int i=0; i<modelList.get(position).getCompany().size();i++){
            company_name.add(modelList.get(position).getCompany()
                    .get(i).getCompanyName());

            ClassHashMap.put(i, new CompanyData(modelList.get(position).getCompany()
                    .get(i).getCompanyId(),modelList.get(position).getCompany()
                    .get(i).getCompanyName(),modelList.get(position).getCategory().getId()));

            //CallProductList(modelList.get(position).getCompany().get(0).getCompanyId(),ClassHashMap.get(i).getCate_id(),holder.recycler_item);

        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (context, android.R.layout.simple_spinner_item, company_name);
        //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spin_cont.setAdapter(spinnerArrayAdapter);

        holder.spin_cont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("SelectedItem SPINNER", "  " + parent.getItemAtPosition(position).toString());

                for (int i = 0; i < ClassHashMap.size(); i++) {
                    if (ClassHashMap.get(i).getCompanyName().equals(parent.getItemAtPosition(position).toString())) {
                        String comp_id = ClassHashMap.get(i).getCompanyId();
                        Log.e("comp_id111", comp_id);

                        CallProductList(comp_id, ClassHashMap.get(i).getCate_id(), holder.recycler_item);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void CallProductList(String comp_id, String cate_id, final RecyclerView recycler_item) {

        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_PRODUCT_URL)
                .addBodyParameter("category_id", cate_id)
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
                            categoryCompanyModelDataList.clear();
                            Log.e("reg_responce123",""+user.getResponce());
                            if (user.getResponce()==true){

                                categoryCompanyModelDataList=user.getData();
                                CategoryProduct_adapter categoryProduct_adapter = new CategoryProduct_adapter(categoryCompanyModelDataList, context);
                                recycler_item.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                recycler_item.setAdapter(categoryProduct_adapter);
                                // categoryProduct_adapter.notifyDataSetChanged();
                            }else {
                                // Toast.makeText(context, "No product available", Toast.LENGTH_SHORT).show();
                                recycler_item.setAdapter(null);
                                Log.e("reg_respo",""+user.getResponce());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
