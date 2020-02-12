package com.drraisingh.narmm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.drraisingh.narmm.Model.Profile_model.CompanyProfileModel;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.rxandroidnetworking.RxAndroidNetworking;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.company_profile;

public class CompanyProfile extends AppCompatActivity {

    ImageView img_crop;
    TextView tv_dr_name;
    ImageView dr_profile_img;
    private String profile;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        dr_profile_img=findViewById(R.id.dr_profile_img);
        img_crop=findViewById(R.id.img_crop);
        tv_dr_name=findViewById(R.id.tv_dr_name);
        submit=findViewById(R.id.submit);

        try{
            if (getIntent()!=null){
                profile=getIntent().getStringExtra("profile");

                if (profile.equalsIgnoreCase("profile")){
                    submit.setVisibility(View.GONE);
                }

            }
        }catch (Exception e){

        }

        if (ConnectivityReceiver.isConnected()){

            getCompanyProfile();
        }


    }

    private void getCompanyProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(CompanyProfile.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.get(company_profile)
                //.addBodyParameter("product_id", product_id)
                .build()
                .getObjectObservable(CompanyProfileModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompanyProfileModel>() {
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
                    public void onNext(CompanyProfileModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            Log.e("res_pro_det",""+user.getResponce());
                            if (user.getResponce()==true){
                              //  text_price.setText("Rs. " +user.getData().getPrice());

                                tv_dr_name.setText(user.getData().getName());
                                setCompanyProfile(user);

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

    private void setCompanyProfile(CompanyProfileModel user) {

        Glide.with(CompanyProfile.this)
                .load(user.getData().getProfileImage())
                .placeholder(R.drawable.per)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(dr_profile_img);


        Glide.with(CompanyProfile.this)
                .load(user.getData().getImage())
                .centerCrop()
                .placeholder(R.drawable.crop)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(img_crop);

    }


    public void go_registration(View view) {
        /*if(sessionManagement.isLoggedIn()) {*/
        Intent startmain = new Intent(CompanyProfile.this, LoginActivity.class);
        startActivity(startmain);
        /*}else{
            Intent startmain = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(startmain);
        }*/
        finish();
    }
}
