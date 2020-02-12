package com.drraisingh.narmm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.drraisingh.narmm.Model.CropModel.CropModel;
import com.drraisingh.narmm.Model.CropModel.CropModelData;
import com.drraisingh.narmm.Model.RegistraionModel.FarmerRegistrationModel;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.databinding.ActivityUpdateAcarBinding;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.Session_management;
import com.rxandroidnetworking.RxAndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_farmer_acres_area_update;
import static com.drraisingh.narmm.Config.BaseURL.REGISTER_URL;
import static com.drraisingh.narmm.Config.BaseURL.get_crop;

public class UpdateAcarActivity extends AppCompatActivity {
    ActivityUpdateAcarBinding binding;
    Session_management sessionManagement;
    List<CropModelData> cropLists=new ArrayList<>();
    List<String> cropLists_name=new ArrayList<>();
    int acar1 = 0,acar2=0,acar3=0,acar4=0;
    String formar_id,CropName_Id1,CropName_Id2,CropName_Id3,CropName_Id4;
    private String crop_first,crop_other,crop_second,crop_third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_update_acar);
      binding= DataBindingUtil.setContentView(this, R.layout.activity_update_acar);//using data binding

        sessionManagement = new Session_management(UpdateAcarActivity.this);
        AndroidNetworking.initialize(getApplicationContext());//initialize must be important api call networking

        if (ConnectivityReceiver.isConnected()){
            getCropApi();
        }else {
            Toast.makeText(this, "Please check internet", Toast.LENGTH_SHORT).show();
        }

        try {
            Intent intent=getIntent();
            formar_id=intent.getStringExtra("farmar_id");
            String total_acres=intent.getStringExtra("total_acres");
            crop_first=intent.getStringExtra("crop_first");
            crop_second=intent.getStringExtra("crop_second");
            crop_third=intent.getStringExtra("crop_third");
            crop_other=intent.getStringExtra("crop_other");
            String crop_first_qty=intent.getStringExtra("crop_first_qty");
            String crop_second_qty=intent.getStringExtra("crop_second_qty");
            String crop_third_qty=intent.getStringExtra("crop_third_qty");
            String crop_other_qty=intent.getStringExtra("crop_other_qty");

            binding.spinAcre.setSelection(Integer.parseInt(total_acres));
            binding.corp1Acre.setSelection(Integer.parseInt(crop_first_qty));
            binding.spinCorp2Qty.setSelection(Integer.parseInt(crop_second_qty));
            binding.corp3Acre.setSelection(Integer.parseInt(crop_third_qty));
            binding.corpotherQty.setSelection(Integer.parseInt(crop_other_qty));

           // binding.spinCorp1.setSelection(Integer.parseInt(crop_first));
           // binding.spinCorp2.setSelection(Integer.parseInt(crop_second));
           // binding.corp3.setSelection(Integer.parseInt(crop_third));
           // binding.corpOther.setSelection(Integer.parseInt(crop_other));



        }catch (Exception e){
        }




        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gonext();
            }
        });


        binding.spinCorp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id1=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinCorp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id2=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.corp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id3=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.corpOther.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CropName_Id4=cropLists.get(position).getMId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void gonext() {

        String spinAcre=binding.spinAcre.getSelectedItem().toString();
       // String spinCorp1=binding.spinCorp1.getSelectedItem().toString();
        String corp1Acre=binding.corp1Acre.getSelectedItem().toString();
       // String spinCorp2=binding.spinCorp2.getSelectedItem().toString();
        String spinCorp2Qty=binding.spinCorp2Qty.getSelectedItem().toString();
       // String corp3=binding.corp3.getSelectedItem().toString();
        String corp3Acre=binding.corp3Acre.getSelectedItem().toString();
       // String corpOther=binding.corpOther.getSelectedItem().toString();
        String corpotherQty=binding.corpotherQty.getSelectedItem().toString();



        if (!spinAcre.equalsIgnoreCase("Acar")){

            int total_acar= Integer.parseInt(spinAcre);

            if (!corp1Acre.equalsIgnoreCase("Acar")){
                acar1= Integer.parseInt(corp1Acre);
            }if (!spinCorp2Qty.equalsIgnoreCase("Acar")){
                acar2= Integer.parseInt(spinCorp2Qty);
            }if (!corp3Acre.equalsIgnoreCase("Acar")){
                acar3= Integer.parseInt(corp3Acre);
            } if (!corpotherQty.equalsIgnoreCase("Acar")){
                acar4= Integer.parseInt(corpotherQty);
            }

            int selected_total=acar1+acar2+acar3+acar4;
            if (selected_total!=0){

                Log.e("Selected_Total",""+selected_total);
                Log.e("total_acar",""+total_acar);

                if (total_acar<selected_total){
                    Toast.makeText(this, "total acar not small crop acar calculation", Toast.LENGTH_LONG).show();
                }else {

                    //****using rxjava with retrofit fast networking
                    UpdateAcar(spinAcre,CropName_Id1,corp1Acre,CropName_Id2,spinCorp2Qty,CropName_Id3,corp3Acre,
                            CropName_Id4,corpotherQty);

                }
            }else {

                Toast.makeText(this, "Please select any one crop acar", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "Please select Total Acar", Toast.LENGTH_SHORT).show();

        }


    }

    private void UpdateAcar(String spinAcre, String spinCorp1, String corp1Acre, String spinCorp2, String spinCorp2Qty, String corp3, String corp3Acre, String corpOther, String corpotherQty) {

        final ProgressDialog progressDialog = new ProgressDialog(UpdateAcarActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.upload(GET_farmer_acres_area_update)
                .addMultipartParameter("farmer_id", formar_id)
                .addMultipartParameter("total_acres", spinAcre)
                .addMultipartParameter("crop_first", spinCorp1)
                .addMultipartParameter("crop_first_qty", corp1Acre)
                .addMultipartParameter("crop_second", spinCorp2)
                .addMultipartParameter("crop_second_qty", spinCorp2Qty)
                .addMultipartParameter("crop_third", corp3)
                .addMultipartParameter("crop_third_qty", corp3Acre)
                .addMultipartParameter("crop_other", corpOther)
                .addMultipartParameter("crop_other_qty", corpotherQty)


                .build()
                .getObjectObservable(FarmerRegistrationModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FarmerRegistrationModel>() {
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
                    public void onNext(FarmerRegistrationModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {

                            // Log.e("reg_responce",user.getData().getFullName());

                            if (user.getResponce()==true){
                                Toast.makeText(UpdateAcarActivity.this, ""+user.getResponce(), Toast.LENGTH_SHORT).show();
                            //onBackPressed();

                                Intent i = new Intent(UpdateAcarActivity.this, FirstTimePayment.class);
                                i.putExtra("Amount", user.getAmount());
                                i.putExtra("GstAmt", user.getGst_amount());
                                i.putExtra("NetAmt", user.getNet_amount());
                                i.putExtra("mobile", user.getUser().getMobileNo());
                                i.putExtra("mail", user.getUser().getEmail());
                                i.putExtra("name", user.getUser().getFullName());
                                i.putExtra("farmar_id", user.getUser().getFarmerId());
                                startActivity(i);

                            }else {
                                Toast.makeText(UpdateAcarActivity.this, ""+user.getError(), Toast.LENGTH_SHORT).show();
                                Log.e("reg_respo",user.getError());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });

    }

    private void getCropApi() {
        final ProgressDialog progressDialog = new ProgressDialog(UpdateAcarActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.get(get_crop)
                //.addBodyParameter("product_id", product_id)
                .build()
                .getObjectObservable(CropModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CropModel>() {
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
                    public void onNext(CropModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            Log.e("res_pro_det",""+user.getResponce());
                            if (user.getResponce()==true){
                                //  text_price.setText("Rs. " +user.getData().getPrice());
                                cropLists=user.getData();
                                for (int i=0; i<user.getData().size();i++){
                                    cropLists_name.add(user.getData().get(i).getMValue());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                        (UpdateAcarActivity.this, android.R.layout.simple_spinner_item, cropLists_name);
                                //selected item will look like a spinner set from XML
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                binding.spinCorp1.setAdapter(spinnerArrayAdapter);
                                binding.spinCorp2.setAdapter(spinnerArrayAdapter);
                                binding.corp3.setAdapter(spinnerArrayAdapter);
                                binding.corpOther.setAdapter(spinnerArrayAdapter);


                                for (int i=0; i<cropLists.size();i++){
                                    if (cropLists.get(i).getMId().equalsIgnoreCase(crop_first)){
                                        binding.spinCorp1.setSelection(i);
                                        Log.e("crop_first",""+i);
                                    }
                                    if (cropLists.get(i).getMId().equalsIgnoreCase(crop_second)){
                                        binding.spinCorp2.setSelection(i);
                                        Log.e("crop_second",""+i);
                                    }

                                    if (cropLists.get(i).getMId().equalsIgnoreCase(crop_third)){
                                        binding.corp3.setSelection(i);
                                        Log.e("crop_third",""+i);
                                    }
                                    if (cropLists.get(i).getMId().equalsIgnoreCase(crop_other)){
                                        binding.corpOther.setSelection(i);
                                        Log.e("crop_other",""+i);
                                    }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
