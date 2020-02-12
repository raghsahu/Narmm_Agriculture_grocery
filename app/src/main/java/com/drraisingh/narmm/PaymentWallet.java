package com.drraisingh.narmm;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.drraisingh.narmm.Config.BaseURL;

import com.drraisingh.narmm.util.DatabaseHandler;
import com.drraisingh.narmm.util.Session_management;
import com.drraisingh.narmm.util.Utilities;

public class PaymentWallet extends AppCompatActivity {
    ProgressDialog progressDoalog;
    Session_management sessionManagement,sh;
    TextView payment,userpay;
    Double totalamount;
    Button paybutton;
    String  Frate,amount;


    private String getlocation_id = "";
    private String gettime = "";
    private String getdate = "";
    private String getuser_id = "";
    private int deli_charges;
    double d1;
    // JSONArray passArray;
    private DatabaseHandler db_cart;

    String generatedNumber;
    JSONArray passArray=null;
    ProgressDialog pDialog;
    String tag_json_obj = "json_add_order_req";
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_wallet);
        Intent intent=getIntent();
        payment=(TextView)findViewById(R.id.payment);
        userpay=(TextView)findViewById(R.id.userpay);
        paybutton=(Button)findViewById(R.id.paybutton);
        db_cart = new DatabaseHandler(this);
        sessionManagement = new Session_management(this);
        new Regisster().execute();
        int a= Integer.parseInt(intent.getStringExtra("total_amount"));
        int b= Integer.parseInt("50");
        int c=a+b;

         Frate= String.valueOf(c);
        payment.setText("Total : "+Frate);
        getdate = getIntent().getStringExtra("date");
        gettime = getIntent().getStringExtra("time");
        getlocation_id = getIntent().getStringExtra("location");

        String getaddress = getIntent().getStringExtra("address");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        String inputDateStr="2013-06-24";
        Date date = null;
        try {
            date = inputFormat.parse(getdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);


        final Double total = Double.parseDouble(db_cart.getTotalAmount()) + deli_charges;

        //tv_total.setText("" + db_cart.getTotalAmount());
        //tv_item.setText("" + db_cart.getCartCount());


        Random random = new Random();
        generatedNumber= String.format("%04d", random.nextInt(10000));

        // Tag used to cancel the request
        Date ccc = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(ccc);

        ArrayList<HashMap<String, String>> items = db_cart.getCartAll();
        if (items.size() > 0) {
            passArray = new JSONArray();
            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> map = items.get(i);

                JSONObject jObjP = new JSONObject();

                try {
                    jObjP.put("product_id", map.get("product_id"));
                    jObjP.put("qty", map.get("qty"));
                    jObjP.put("unit_value", map.get("unit_value"));
                    jObjP.put("unit", map.get("unit").trim());
                    jObjP.put("price", map.get("price"));

                    passArray.put(jObjP);

                    Log.e("send",passArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);



            Log.e("", "from:" + gettime + "\ndate:" + getdate +
                    "\n" + "\nuser_id:" + getuser_id + "\n" + getlocation_id + "\ndata:" + passArray.toString());



        }



        paybutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

                 amount= String.valueOf(Integer.parseInt(Frate) - Integer.parseInt(userpay.getText().toString()));


       if(userpay.getText().toString().equals("0") ){
            Intent wallet=new Intent(PaymentWallet.this,Wallet.class);
            wallet.putExtra("data",amount);
            startActivity(wallet);
            finish();
        } else if(Integer.valueOf(Frate)> Integer.valueOf(userpay.getText().toString())){

            Intent wallet=new Intent(PaymentWallet.this,Wallet.class);
            wallet.putExtra("data",amount);
            startActivity(wallet);
            finish();


        }else {
            makeAddOrderRequest();

        }







    }
});


    }

    private void attemptOrder() {

        // retrive data from cart database

        final Map<String, String> params = new HashMap<String, String>();
        params.put("date", getdate);
        params.put("time", gettime);
        params.put("user_id",  sessionManagement.getUserDetails().get(BaseURL.KEY_ID));
        params.put("location", getlocation_id);
        params.put("unique_id", generatedNumber);
        params.put("data", passArray.toString());

    }

    /**
     * Method to make json object request where json response starts wtih
     */
    private void makeAddOrderRequest() {
        new FetchSamplePaper().execute();
    }


    public class FetchSamplePaper extends AsyncTask<String, String, String> {

        String result = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(PaymentWallet.this);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            try{
                String urlParameters =
                        "date=" + URLEncoder.encode(getdate, "UTF-8")+
                                "&time=" + URLEncoder.encode(gettime, "UTF-8")+
                                "&user_id=" + URLEncoder.encode(getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID), "UTF-8")+
                                "&location=" + URLEncoder.encode(getlocation_id, "UTF-8")+
                                "&unique_id=" + URLEncoder.encode(generatedNumber, "UTF-8")+
                                "&total_amount=" + URLEncoder.encode(db_cart.getTotalAmount(), "UTF-8")+
                                "&delivery_date=" + URLEncoder.encode(formattedDate, "UTF-8")+
                                "&order_type=" + URLEncoder.encode("online", "UTF-8")+
                                "&data=" + URLEncoder.encode(passArray.toString(), "UTF-8");
                result = Utilities.postParamsAndfindJSON("https://harshadbhaifruitcompany.com/hfcmall/index.php/Api/send_order?", urlParameters);
                Log.i("dataff",result.toString());

            }catch (Exception e){

            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                dialog.dismiss();
                Utilities.Alert(PaymentWallet.this, "Server problem occurred");
            } else {
                try {
                    dialog.dismiss();

                    JSONObject jsonObject=new JSONObject(result);
                    Log.d("data",result.toString());
                    Boolean status = jsonObject.getBoolean("responce");
                    if (status) {

                        String msg = jsonObject.getString("data");

                        db_cart.clearCart();

                        Intent intent=new Intent(PaymentWallet.this, OrderSuccess.class);
                        intent.putExtra("msg",msg);
                        startActivity(intent);


                    }


                } catch (Exception e) {

                }

                super.onPostExecute(result);
            }
        }


    }

    class Regisster extends AsyncTask<String,String,String> {
        String result = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDoalog = new ProgressDialog(PaymentWallet.this);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Its loading....");
            progressDoalog.setTitle("Please Wait...");

            // show it
            progressDoalog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String urlParameters =
                        "user_id=" + URLEncoder.encode(sessionManagement.getUserDetails().get(BaseURL.KEY_ID), "UTF-8");
                result = Utilities.postParamsAndfindJSON(" https://harshadbhaifruitcompany.com/hfcmall/index.php/Api/wallet?", urlParameters);
            } catch (Exception e) {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (result == null) {
                progressDoalog.dismiss();

            } else {
                try {
                    progressDoalog.dismiss();
                    JSONArray json = new JSONArray(result);
                    for (int i=0;i<=json.length();i++){
                        JSONObject jsonObject=json.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        String use_id=jsonObject.getString("user_id");
                        String amt=jsonObject.getString("amount");

                        userpay.setText(amt);



                    }



                } catch (Exception e) {

                }


            }


        }
    }


}
