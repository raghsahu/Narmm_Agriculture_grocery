package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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


import com.drraisingh.narmm.Adapter.Show_item_adapter;
import com.drraisingh.narmm.Config.BaseURL;

import com.drraisingh.narmm.Fragment.Thanks_fragment;
import com.drraisingh.narmm.Model.OrderModel.PlaceOrderModel;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_model;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.R;

import cn.refactor.lib.colordialog.PromptDialog;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.drraisingh.narmm.activity.PaymentActivity;
import com.drraisingh.narmm.util.ConnectivityReceiver;

import com.drraisingh.narmm.util.DatabaseHandler;
import com.drraisingh.narmm.util.Session_management;
import com.drraisingh.narmm.util.Utilities;
import com.rxandroidnetworking.RxAndroidNetworking;

import static com.drraisingh.narmm.Config.BaseURL.KEY_ID;
import static com.drraisingh.narmm.Config.BaseURL.KEY_MOBILE;
import static com.drraisingh.narmm.Config.BaseURL.KEY_NAME;
import static com.drraisingh.narmm.Config.BaseURL.send_order;


public class Delivery_payment_detail_fragment extends Fragment {

    private static String TAG = Delivery_payment_detail_fragment.class.getSimpleName();

    private TextView tv_timeslot, tv_address, tv_item, tv_total,text_buyer;
    private Button btn_order,pay_now;

    private String getlocation_id = "";
    private String gettime = "";
    private String getdate = "";
    private String item_count;
    private String data_json;
    private String getuser_id = "",result;
    private int deli_charges;
    double d1;
   // JSONArray passArray;
    private DatabaseHandler db_cart;
    private Session_management sessionManagement;
    String generatedNumber;
    JSONArray passArray=null;
     ProgressDialog pDialog;
    String tag_json_obj = "json_add_order_req";
    String formattedDate;
    String total_value;
    ArrayList<Cartlist_model> cart_model;
    RecyclerView rv_myorder;

    public Delivery_payment_detail_fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_order, container, false);

        ( getActivity()).setTitle(getResources().getString(R.string.payment_detail));

        db_cart = new DatabaseHandler(getActivity());
        sessionManagement = new Session_management(getActivity());

        tv_timeslot = (TextView) view.findViewById(R.id.textTimeSlot);
        tv_address = (TextView) view.findViewById(R.id.txtAddress);
        //tv_item = (TextView) view.findViewById(R.id.textItems);
        //tv_total = (TextView) view.findViewById(R.id.textPrice);
        tv_total = (TextView) view.findViewById(R.id.txtTotal);
        text_buyer = (TextView) view.findViewById(R.id.text_buyer);

        btn_order = (Button) view.findViewById(R.id.buttonContinue);
        pay_now = (Button) view.findViewById(R.id.buttonContinue1);
        rv_myorder =  view.findViewById(R.id.rv_myorder);

        try {
            getdate = getArguments().getString("getdate");
            gettime = getArguments().getString("time");
            getlocation_id = getArguments().getString("shop_id");
            data_json = getArguments().getString("data");
            String address = getArguments().getString("address");
            total_value = getArguments().getString("total_value");
            item_count = getArguments().getString("item_count");
            cart_model  = getArguments().getParcelableArrayList("cart_model");

            tv_address.setText(address);


            Log.e("cat_model_ii",""+cart_model.get(0).getProduct_name());
            Show_item_adapter history_item_adapter = new Show_item_adapter(cart_model);
            LinearLayoutManager manager= new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            rv_myorder.setLayoutManager(manager);
            rv_myorder.setAdapter(history_item_adapter);
            history_item_adapter.notifyDataSetChanged();

        }catch (Exception e){

        }

        text_buyer.setText(sessionManagement.getUserDetails().get(KEY_NAME)+ "\n" +
                sessionManagement.getUserDetails().get(KEY_MOBILE));


        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
       /// String inputDateStr="2013-06-24";
        Date date = null;
        try {
            date = inputFormat.parse(getdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        tv_timeslot.setText(outputDateStr + "                         " + gettime);

//        tv_total.setText(getResources().getString(R.string.tv_cart_item) + item_count.toString() + "\n" +
//                getResources().getString(R.string.total_amount) + " Rs. " +total_value);

        tv_total.setText("Total Amount: "+total_value);
                
        Random random = new Random();
        generatedNumber= String.format("%04d", random.nextInt(10000));

        // Tag used to cancel the request
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
         formattedDate = df.format(c);

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



            Log.e(TAG, "from:" + gettime + "\ndate:" + getdate +
                    "\n" + "\nuser_id:" + getuser_id + "\n" + getlocation_id + "\ndata:" + passArray.toString());



        }



        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check internet connection
                if (ConnectivityReceiver.isConnected()) {
                    makeAddOrderRequest();

                    //Log.e("swapnilt",""+generatedPassword);

                } else {
                    ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
                }
            }
        });


        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check internet connection
                if (ConnectivityReceiver.isConnected()) {

                    makeAddOrderRequestOnline();//place order API
                } else {
                    ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
                }
            }
        });

        return view;
    }

    /**
     * Method to make json object request where json response starts wtih
     */
    private void makeAddOrderRequest() {
        new FetchSamplePaper().execute();
    }
    private void makeAddOrderRequestOnline() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(send_order)
                 .addBodyParameter("farmer_id",sessionManagement.getUserDetails().get(KEY_ID) )
                 .addBodyParameter("delivery_date", getdate)
                 .addBodyParameter("delivery_time", gettime)
                 .addBodyParameter("shop_id", getlocation_id)
                 .addBodyParameter("data", data_json)
                .build()
                .getObjectObservable(PlaceOrderModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlaceOrderModel>() {
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
                    public void onNext(PlaceOrderModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            // Log.e("reg_responce",user.getData().getFullName());
                     Toast.makeText(getActivity(), ""+user.getResponce(), Toast.LENGTH_SHORT).show();

                      Intent wallet=new Intent(getActivity(), PaymentActivity.class);
                     wallet.putExtra("Order_id",user.getOrderId().toString());
                     wallet.putExtra("Order_details",user.getData());
                     wallet.putExtra("Order_amount",user.getTotalAmount().toString());
                      startActivity(wallet);
                      getActivity().finish();

                        }catch (Exception e){
                            Log.e("categ_catch",e.getMessage());
                        }

                    }
                });



    }


    public class FetchSamplePaper extends AsyncTask<String, String, String> {

        String result = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
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
                                "&user_id=" + URLEncoder.encode("4", "UTF-8")+
                                "&location=" + URLEncoder.encode(getlocation_id, "UTF-8")+
                                "&mobile=" + URLEncoder.encode(sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE), "UTF-8")+
                                "&name=" + URLEncoder.encode(sessionManagement.getUserDetails().get(BaseURL.KEY_NAME), "UTF-8")+
                                "&unique_id=" + URLEncoder.encode(generatedNumber, "UTF-8")+
                                "&total_amount=" + URLEncoder.encode(String.valueOf(Integer.parseInt(db_cart.getTotalAmount())+deli_charges), "UTF-8")+
                                "&delivery_date=" + URLEncoder.encode(formattedDate, "UTF-8")+
                                "&order_type=" + URLEncoder.encode("COD", "UTF-8")+
                                "&data=" + URLEncoder.encode(passArray.toString(), "UTF-8");
                result = Utilities.postParamsAndfindJSON("http://mysomeproject.top/hfcmall/index.php/Api/send_order/", urlParameters);
                Log.i("dataff",result.toString());

            }catch (Exception e){

            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                dialog.dismiss();
                Utilities.Alert(getActivity(), "Server problem occurred");
            } else {
                try {
                    dialog.dismiss();

                    JSONObject jsonObject=new JSONObject(result);
                    Log.d("data",result.toString());
                    Boolean status = jsonObject.getBoolean("responce");
                    if (status) {
                        String msg = jsonObject.getString("data");
                        db_cart.clearCart();
                        ((MainActivity) getActivity()).setCartCounter("" + db_cart.getCartCount());

                        Bundle args = new Bundle();
                        Fragment fm = new Thanks_fragment();
                        args.putString("msg", msg);
                        fm.setArguments(args);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                .addToBackStack(null).commit();


                    }


                } catch (Exception e) {

                }

                super.onPostExecute(result);
            }
        }


    }

    public class FetchSamplePaperOnline extends AsyncTask<String, String, String> {

        String result = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
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
                                "&user_id=" + URLEncoder.encode("4", "UTF-8")+
                                "&location=" + URLEncoder.encode(getlocation_id, "UTF-8")+
                                "&unique_id=" + URLEncoder.encode(generatedNumber, "UTF-8")+
                                "&total_amount=" + URLEncoder.encode(String.valueOf(Integer.parseInt(db_cart.getTotalAmount())+deli_charges), "UTF-8")+
                                "&delivery_date=" + URLEncoder.encode(formattedDate, "UTF-8")+
                                "&order_type=" + URLEncoder.encode("online", "UTF-8")+
                                "&data=" + URLEncoder.encode(passArray.toString(), "UTF-8");
                result = Utilities.postParamsAndfindJSON("http://mysomeproject.top/hfcmall/index.php/Api/send_order?", urlParameters);
                Log.i("nahinahi",result.toString());

            }catch (Exception e){

            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                dialog.dismiss();
                Utilities.Alert(getActivity(), "Server problem occurred");
            } else {
                try {
                    dialog.dismiss();

                    JSONObject jsonObject=new JSONObject(result);
                    Log.d("data",result.toString());
                    Boolean status = jsonObject.getBoolean("responce");
                    if (status) {
                        String msg = jsonObject.getString("data");
                        db_cart.clearCart();
                        ((MainActivity) getActivity()).setCartCounter("" + db_cart.getCartCount());

                        Bundle args = new Bundle();
                        Fragment fm = new Thanks_fragment();
                        args.putString("msg", msg);
                        fm.setArguments(args);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                .addToBackStack(null).commit();


                    }


                } catch (Exception e) {

                }

                super.onPostExecute(result);
            }
        }


    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        result=data.getStringExtra("result");


        if(result.equals("payment_successfull")){

            String response = data.getStringExtra("payment_response");
            try {
                JSONObject jsonObject=new JSONObject(response);
                String bname = jsonObject.getString("name_on_card");
                String bank_ref_num = jsonObject.getString("bank_ref_num");
                String udf3 = jsonObject.getString("udf3");
                String hash = jsonObject.getString("hash");
                String firstname = jsonObject.getString("firstname");
                String net_amount_debit = jsonObject.getString("net_amount_debit");
                String payment_source = jsonObject.getString("payment_source");
                String surl = jsonObject.getString("surl");
                String error_Message = jsonObject.getString("error_Message");
                String issuing_bank = jsonObject.getString("issuing_bank");
                String cardCategory = jsonObject.getString("cardCategory");
                String phone = jsonObject.getString("phone");
                String easepayid = jsonObject.getString("easepayid");

                String cardnum = jsonObject.getString("cardnum");
                String key = jsonObject.getString("key");
                String udf8 = jsonObject.getString("udf8");
                String unmappedstatus = jsonObject.getString("unmappedstatus");
                String PG_TYPE = jsonObject.getString("PG_TYPE");
                String addedon = jsonObject.getString("addedon");


                String card_type = jsonObject.getString("card_type");
                String amount = jsonObject.getString("amount");



                String deduction_percentage = jsonObject.getString("deduction_percentage");
                String orderid = jsonObject.getString("txnid");
                String txnid = jsonObject.getString("txnid");



               new FetchSamplePaperOnline().execute();


                //loadBulkshopdata(error_Message,net_amount_debit,orderid,amount,addedon,txnid,"online",PG_TYPE,unmappedstatus,card_type);


            }catch (Exception e){

            }





        }else if(result.equals("user_cancelled")) {

            PromptDialog promptDialog=  new PromptDialog(getActivity());
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Cancelled");
            promptDialog.setContentText("user_cancelled");
            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("payment_failed")) {

            PromptDialog promptDialog=  new PromptDialog(getActivity());
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Failure");
            promptDialog.setContentText("payment_failed");
            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("txn_session_timeout")) {

            PromptDialog promptDialog=  new PromptDialog(getActivity());
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction session timeout");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("back_pressed")) {

            PromptDialog promptDialog=  new PromptDialog(getActivity());
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Failure");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("invalid_input_data")) {

            PromptDialog promptDialog=  new PromptDialog(getActivity());
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Invalid Input Data");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("trxn_not_allowed")) {

            PromptDialog promptDialog=  new PromptDialog(getActivity());
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Not allowed");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("bank_back_pressed")) {

            PromptDialog promptDialog=  new PromptDialog(getActivity());
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Not allowed");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }





    }

}
