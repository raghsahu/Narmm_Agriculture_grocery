package com.drraisingh.narmm.activity;

import android.app.ProgressDialog;
import android.app.Activity;

import android.content.IntentFilter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Model.OrderModel.OrderPaymentModel;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.Session_management;
import com.rxandroidnetworking.RxAndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.payment;

public class PaymentActivity extends AppCompatActivity {

    TextView confirmorder,tv_order_id,tv_order_total,tv_order_details;
    private String Order_id;
    RadioButton radio_online;
    private String Order_amount;
    private Session_management sessionManagement;
    String user_id,getphone,getname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        // Call the function callInstamojo to start payment here
        sessionManagement = new Session_management(PaymentActivity.this);
        getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
        getphone = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
        user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        confirmorder=findViewById(R.id.confirmorder);
        tv_order_id=findViewById(R.id.tv_order_id);
        tv_order_total=findViewById(R.id.tv_order_total);
        tv_order_details=findViewById(R.id.tv_order_details);
        radio_online=findViewById(R.id.radio_online);


        try {
           Order_id=getIntent().getStringExtra("Order_id");
           String Order_details=getIntent().getStringExtra("Order_details");
           Order_amount=getIntent().getStringExtra("Order_amount");

            tv_order_id.setText("Order id: "+Order_id);
            tv_order_total.setText("Pay Total Rs: "+Order_amount);
          //  tv_order_details.setText("Pay Total Rs: "+Order_details);
            try {
                // tv_details.setText(pro_dosage);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_order_details.setText(Html.fromHtml(Order_details, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tv_order_details.setText(Html.fromHtml(Order_details));
                }
            }catch (Exception e){

            }

        }catch (Exception e){

        }


        confirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio_online.isChecked()){

                    Toast.makeText(PaymentActivity.this, "Instamojo Payment Gateway...", Toast.LENGTH_SHORT).show();
                    callInstamojoPay(getname+"@gmail.com",getphone,Order_amount,"Buy_Product",getname);
                    // startPayment();//razor pay payment gateway

                }else {
                    Toast.makeText(PaymentActivity.this, "Please select payment mode", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {

        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("insta_error",e.toString());
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;

    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                Payment_online(response);//after success payment send data to server

            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

    private void Payment_online(String response) {
        final ProgressDialog progressDialog = new ProgressDialog(PaymentActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(payment)
                .addBodyParameter("order_id",Order_id )
                .addBodyParameter("transaction_id", response)
                .addBodyParameter("status", "Success")
                .build()
                .getObjectObservable(OrderPaymentModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderPaymentModel>() {
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
                    public void onNext(OrderPaymentModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            // Log.e("reg_responce",user.getData().getFullName());
                            Toast.makeText(PaymentActivity.this, ""+user.getResponce(), Toast.LENGTH_SHORT).show();

                            Intent wallet=new Intent(PaymentActivity.this, Thankyou.class);
                            wallet.putExtra("Happy_code",user.getData().getHappyCode().toString());
                            startActivity(wallet);
                            finish();


                        }catch (Exception e){
                            Log.e("categ_catch",e.getMessage());
                        }

                    }
                });

    }

    /*  private void startPayment() {

        *//**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         *//*
       // final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Narmm");
            options.put("description", "Product Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            String payment = Order_amount;

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
           preFill.put("email", getname+"@gmail.com");
            preFill.put("contact", getphone);

            options.put("prefill", preFill);

            co.open(PaymentActivity.this, options);
        } catch (Exception e) {
            Toast.makeText(PaymentActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/



    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        Intent wallet=new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(wallet);
        finish();
    }

}
